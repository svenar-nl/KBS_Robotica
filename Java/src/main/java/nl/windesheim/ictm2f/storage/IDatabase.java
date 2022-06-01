package nl.windesheim.ictm2f.storage;

import java.util.Map;

public interface IDatabase {

    /**
     * Get the type of storage method used
     * 
     * @return name of the database storage method
     */
    public String getType();

    /**
     * Connect to the database
     * 
     * @return true if connected successfully, false otherwise
     */
    public boolean connect();

    /**
     * Disconnect from the database
     */
    public void disconnect();

    /**
     * Check if the database is online
     * 
     * @return true if connected to the database, false otherwise
     */
    public boolean isConnected();

    /**
     * Load data from the database into cache
     * @return Map with stored data (key-value)
     */
    public Map<String, Object> load();

    /**
     * Save cahce (key-value) to database
     * @param data
     */
    public void save(Map<String, Object> data);
}
