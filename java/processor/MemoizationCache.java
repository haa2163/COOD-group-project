package processor;

import java.util.HashMap;
import java.util.Map;

public class MemoizationCache {

    private static MemoizationCache instance;
    private final Map<String, Object> cache = new HashMap<>();

    private MemoizationCache() {}

    public static MemoizationCache getInstance() {
        if (instance == null) {
            instance = new MemoizationCache();
        }
        return instance;
    }

    public boolean has(String key) {
        return cache.containsKey(key);
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public int getInt(String key) {
        return (int) cache.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, T> getMap(String key) {
        return (Map<String, T>) cache.get(key);
    }
}