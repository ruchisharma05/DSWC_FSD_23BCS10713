package com.streamcast;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;
    
    private String authorName;
    private String username;
    
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private java.util.List<MediaAsset> mediaAssets;
    
    public Author() {}
    public Author(String authorName, String username) {
        this.authorName = authorName;
        this.username = username;
    }
    
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getUsername() { return username; }
    public java.util.List<MediaAsset> getMediaAssets() { return mediaAssets; }
    public void setMediaAssets(java.util.List<MediaAsset> mediaAssets) { this.mediaAssets = mediaAssets; }
}
