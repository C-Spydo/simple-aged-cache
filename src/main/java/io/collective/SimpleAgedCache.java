package io.collective;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

public class SimpleAgedCache {

    private final Clock clock;
    private final Map<Object, ExpirableEntry> entries = new HashMap<>();

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this(Clock.systemDefaultZone());

    }

    public void put(Object key, Object value, int retentionInMillis) {
        ExpirableEntry entry = new ExpirableEntry(clock.millis(), retentionInMillis, value);
        entries.put(key,entry);
    }

    public boolean isEmpty() {
        clearExpired();
        if(entries.size() <= 0) return true;
        else return false;
    }

    public void clearExpired(){
        for(Map.Entry<Object, ExpirableEntry> entryObject: entries.entrySet()){
            ExpirableEntry entry = entryObject.getValue();
            if((clock.millis() - entry.saveTime) >= entry.retentionInMillis) {
                entries.remove(entryObject.getKey());
            }
        }
    }
    public int size() {
        clearExpired();
        return entries.size();
    }

    public Object get(Object key) {
        if(entries.containsKey(key)){
            ExpirableEntry entry = entries.get(key);
            if((clock.millis() - entry.saveTime) < entry.retentionInMillis) {
                return entry.getValue();
            }
        }
        return null;

    }



}