package nl.windesheim.ictm2f.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.util.Logger;

public class MySQL implements IDatabase {

    private Connection connection;
    private String database = "";

    @Override
    public String getType() {
        return "MySQL";
    }

    @Override
    public boolean connect() {
        String ip = String.valueOf(Main.getInstance().getConfigManager().get("mysql-connection-ip"));
        String port = String.valueOf(Main.getInstance().getConfigManager().get("mysql-connection-port"));
        database = String.valueOf(Main.getInstance().getConfigManager().get("mysql-connection-database"));
        String username = String.valueOf(Main.getInstance().getConfigManager().get("mysql-connection-username"));
        String password = String.valueOf(Main.getInstance().getConfigManager().get("mysql-connection-password"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                return false;
            }
        }

        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":" + port + "?autoReconnect=true"
                            + "&useSSL=false" + "&rewriteBatchedStatements=true",
                    username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        String query = "CREATE DATABASE IF NOT EXISTS " + database;
        try {
            int result = this.connection.createStatement().executeUpdate(query);
            checkSQLResult(result, query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        query = "CREATE TABLE IF NOT EXISTS `" + this.database
                + "`.`kvstorage` (`keyname` VARCHAR(256) NOT NULL UNIQUE, `val` LONGTEXT NOT NULL, `type` VARCHAR(32) NOT NULL, UNIQUE(`keyname`));";
        try {
            int result = this.connection.createStatement().executeUpdate(query);
            checkSQLResult(result, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void disconnect() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            Logger.severe("Failed to disconnect from database!");
            Logger.severe("Forcing database disconnect.");
        }
        this.connection = null;
    }

    @Override
    public boolean isConnected() {
        try {
            return connection == null ? false : !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> load() {
        Instant startTime = Instant.now();

        Map<String, Object> data = new HashMap<>();
        try {
            String query = "SELECT * FROM `" + this.database + "`.`kvstorage`;";
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String key = rs.getString("keyname");
                String rawValue = rs.getString("val");
                String valueType = rs.getString("type");

                Object value = rawValue;
                switch (valueType.toLowerCase()) {
                    case "double":
                        value = Double.parseDouble(rawValue);
                        break;

                    case "float":
                        value = Float.parseFloat(rawValue);
                        break;

                    case "integer":
                        value = Integer.parseInt(rawValue);
                        break;

                    case "boolean":
                        value = Boolean.parseBoolean(rawValue);
                        break;

                    case "string":
                    default:
                        value = rawValue;
                        break;
                }

                data.put(key, value);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Logger.info(String.format("Loaded data from database in %sms",
                String.valueOf(Duration.between(startTime, Instant.now()).toMillis())));

        return data;
    }

    @Override
    public void save(Map<String, Object> data) {
        Instant startTime = Instant.now();

        try {
            String clearQuery = "DELETE FROM `" + this.database + "`.`kvstorage`;";
            this.connection.createStatement().executeUpdate(clearQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String query = "INSERT INTO `" + this.database
                + "`.`kvstorage` (keyname, val, type) VALUES (?, ?, ?);";

        PreparedStatement stmt = null;
        try {
            this.connection.setAutoCommit(false);
            stmt = this.connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            String valueType = getClassName(entry.getValue());

            try {
                System.out.println(query.replaceFirst("\\?", key).replaceFirst("\\?", value)
                        .replaceFirst("\\?", valueType).replaceFirst("\\?", value).replaceFirst("\\?", valueType));
                stmt.clearParameters();
                stmt.setString(1, key);
                stmt.setString(2, value);
                stmt.setString(3, valueType);
                // stmt.setString(4, value);
                // stmt.setString(5, valueType);
                stmt.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            stmt.executeBatch();
            this.connection.commit();
            this.connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Logger.info(String.format("Saved data to database in %sms",
                String.valueOf(Duration.between(startTime, Instant.now()).toMillis())));
    }

    private void checkSQLResult(int result, String query) throws SQLException {
        if (result < 0) {
            throw new SQLException("Failed to execute SQL query (" + query + ")");
        }
    }

    private String getClassName(Object obj) {
        if (obj instanceof Integer) {
            return "Integer";
        }
        if (obj instanceof Boolean) {
            return "Boolean";
        }
        if (obj instanceof Float) {
            return "Float";
        }
        if (obj instanceof Double) {
            return "Double";
        }
        return "String";
    }

}
