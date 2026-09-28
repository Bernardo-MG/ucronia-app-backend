
package com.bernardomg.asset.adapter.inbound.jpa.model;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Asset")
@Table(schema = "asset", name = "assets")
@EntityListeners(AuditingEntityListener.class)
public class AssetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditMetadata     audit            = new AuditMetadata();

    @Column(name = "description", nullable = false, length = 500)
    private String            description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private AssetFolderEntity folder;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "object_key", nullable = false, unique = true, length = 255)
    private String            key;

    @Column(name = "media_type", nullable = false, length = 100)
    private String            mediaType;

    @Column(name = "name", nullable = false, length = 100)
    private String            name;

    @Column(name = "number", nullable = false)
    private Long              number;

    @Column(name = "public_access", nullable = false)
    private boolean           publicAccess;

    @Column(name = "size", nullable = false)
    private Long              size;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private AssetType         type;

    public AuditMetadata getAudit() {
        return audit;
    }

    public String getDescription() {
        return description;
    }

    public AssetFolderEntity getFolder() {
        return folder;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getName() {
        return name;
    }

    public Long getNumber() {
        return number;
    }

    public Long getSize() {
        return size;
    }

    public AssetType getType() {
        return type;
    }

    public boolean isPublicAccess() {
        return publicAccess;
    }

    public void setAudit(final AuditMetadata value) {
        audit = value;
    }

    public void setDescription(final String value) {
        description = value;
    }

    public void setFolder(final AssetFolderEntity value) {
        folder = value;
    }

    public void setId(final Long value) {
        id = value;
    }

    public void setKey(final String value) {
        key = value;
    }

    public void setMediaType(final String value) {
        mediaType = value;
    }

    public void setName(final String value) {
        name = value;
    }

    public void setNumber(final Long value) {
        number = value;
    }

    public void setPublicAccess(final boolean value) {
        publicAccess = value;
    }

    public void setSize(final Long value) {
        size = value;
    }

    public void setType(final AssetType value) {
        type = value;
    }

    @Override
    public String toString() {
        return "AssetEntity [id=" + id + ", key=" + key + ", name=" + name + ", description=" + description + ", audit="
                + audit + ", folder=" + folder + ", mediaType=" + mediaType + ", number=" + number + ", publicAccess="
                + publicAccess + ", size=" + size + ", type=" + type + "]";
    }

}
