package io.collective;

public class ExpirableEntry {
    long retentionInMillis;
    long saveTime;

    private final Object entry;
    public ExpirableEntry(long saveTime, long expiryTime, Object entry) {
        this.saveTime = saveTime;
        this.retentionInMillis = expiryTime;
        this.entry = entry;
    }

    public Object getValue() {
        return entry;
    }

}