package nl.windesheim.ictm2f.storage;

import java.util.HashMap;
import java.util.Map;

public class CachedData {

    private Map<String, Object> data;

    public CachedData() {
        this.data = new HashMap<String, Object>();
    }

    /**
     * Get a string value from the cache
     * 
     * @param key
     * @return String
     */
    public String getString(String key) {
        Object value = data.get(key);
        if (value instanceof String) {
            return String.valueOf(value);
        } else {
            throw new IllegalStateException("[CachedData] Requested key is not a String");
        }
    }

    /**
     * Get a integer value from the cache
     * 
     * @param key
     * @return Integer
     */
    public int getInt(String key) {
        Object value = data.get(key);
        if (value instanceof Integer) {
            return (int) value;
        } else {
            throw new IllegalStateException("[CachedData] Requested key is not a Integer");
        }
    }

    /**
     * Get a boolean value from the cache
     * 
     * @param key
     * @return Boolean
     */
    public boolean getBool(String key) {
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (boolean) value;
        } else {
            throw new IllegalStateException("[CachedData] Requested key is not a Boolean");
        }
    }

    /**
     * Store data in the cache
     * 
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        this.data.put(key, value);
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean hasKey(String key) {
        return this.data.containsKey(key);
    }

    public void removeKey(String key) {
        if (hasKey(key)) {
            this.data.remove(key);
        }
    }
}
