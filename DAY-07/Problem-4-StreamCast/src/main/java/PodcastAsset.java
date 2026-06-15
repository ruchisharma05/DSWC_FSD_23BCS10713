package com.streamcast;

import jakarta.persistence.*;

@Entity
@Table(name = "podcast_asset")
public class PodcastAsset extends MediaAsset {
    private String episodeNumber;
    private long audioLengthSeconds;
    
    public PodcastAsset() {}
    public PodcastAsset(String assetName, String uploadDate, Author author, String episodeNumber) {
        super(assetName, uploadDate, author);
        this.episodeNumber = episodeNumber;
    }
    
    public String getEpisodeNumber() { return episodeNumber; }
    public void setEpisodeNumber(String episodeNumber) { this.episodeNumber = episodeNumber; }
    public long getAudioLengthSeconds() { return audioLengthSeconds; }
    public void setAudioLengthSeconds(long audioLengthSeconds) { this.audioLengthSeconds = audioLengthSeconds; }
}
