package com.krillsson.sysapi.core.domain.storage;

public class DiskMetrics {
    private final long usableSpace;
    private final long totalSpace;
    private final long openFileDescriptors;
    private final long maxFileDescriptors;
    private final long reads;
    private final long readBytes;
    private final long writes;
    private final long writeBytes;

    public DiskMetrics(long usableSpace, long totalSpace, long openFileDescriptors, long maxFileDescriptors, long reads, long readBytes, long writes, long writeBytes) {
        this.usableSpace = usableSpace;
        this.totalSpace = totalSpace;
        this.openFileDescriptors = openFileDescriptors;
        this.maxFileDescriptors = maxFileDescriptors;
        this.reads = reads;
        this.readBytes = readBytes;
        this.writes = writes;
        this.writeBytes = writeBytes;
    }
}
