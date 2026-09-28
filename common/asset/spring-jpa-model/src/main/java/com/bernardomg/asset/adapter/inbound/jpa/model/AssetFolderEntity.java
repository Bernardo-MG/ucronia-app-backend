
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

@Entity(name = "AssetFolder")
@Table(schema = "asset", name = "asset_folders")
@EntityListeners(AuditingEntityListener.class)
public class AssetFolderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditMetadata     audit            = new AuditMetadata();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long              id;

    @Column(nullable = false, length = 100)
    private String            name;

    @Column(nullable = false)
    private Long              number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AssetFolderEntity parent;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private AssetType         type;

    public AuditMetadata getAudit() {
        return audit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNumber() {
        return number;
    }

    public AssetFolderEntity getParent() {
        return parent;
    }

    public AssetType getType() {
        return type;
    }

    public void setAudit(final AuditMetadata value) {
        audit = value;
    }

    public void setId(final Long value) {
        id = value;
    }

    public void setName(final String value) {
        name = value;
    }

    public void setNumber(final Long value) {
        number = value;
    }

    public void setParent(final AssetFolderEntity value) {
        parent = value;
    }

    public void setType(final AssetType value) {
        type = value;
    }

    @Override
    public String toString() {
        return "AssetFolderEntity [id=" + id + ", number=" + number + ", name=" + name + ", audit=" + audit
                + ", parent=" + parent + ", type=" + type + "]";
    }

}
