package com.streamcast;

import jakarta.persistence.*;

@Entity
@Table(name = "video_asset")
public class VideoAsset extends MediaAsset {
    private String resolution;
    private long durationSeconds;
    
    public VideoAsset() {}
    public VideoAsset(String assetName, String uploadDate, Author author, String resolution) {
        super(assetName, uploadDate, author);
        this.resolution = resolution;
    }
    
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public long getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(long durationSeconds) { this.durationSeconds = durationSeconds; }
}
