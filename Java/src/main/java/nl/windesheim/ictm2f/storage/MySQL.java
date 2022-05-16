package nl.windesheim.ictm2f.storage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import nl.windesheim.ictm2f.util.Logger;

public class MySQL implements IDatabase {

    private Connection connection;
    
    @Override
    public String getType() {
        return "MySQL";
    }

    @Override
    public boolean connect() {
        // TODO Auto-generated method stub
        return false;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Map<String, Object> data) {
        // TODO Auto-generated method stub
        
    }
    
}
