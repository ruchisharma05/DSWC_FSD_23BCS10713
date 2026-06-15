package com.streamcast;

import jakarta.persistence.*;

@Entity
@Table(name = "media_asset")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MediaAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long assetId;
    
    private String assetName;
    private String uploadDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    
    public MediaAsset() {}
    public MediaAsset(String assetName, String uploadDate, Author author) {
        this.assetName = assetName;
        this.uploadDate = uploadDate;
        this.author = author;
    }
    
    public Long getAssetId() { return assetId; }
    public String getAssetName() { return assetName; }
    public String getUploadDate() { return uploadDate; }
    public Author getAuthor() { return author; }
}
