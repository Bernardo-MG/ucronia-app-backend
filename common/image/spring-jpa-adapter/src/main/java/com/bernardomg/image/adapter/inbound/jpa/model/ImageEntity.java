/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.model;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Image")
@Table(schema = "image", name = "images")
@EntityListeners(AuditingEntityListener.class)
public class ImageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditMetadata     audit            = new AuditMetadata();

    @Column(name = "description", nullable = false, length = 500)
    private String            description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "object_key", nullable = false, unique = true, length = 255)
    private String            key;

    @Column(name = "media_type", nullable = false, length = 100)
    private String            mediaType;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String            name;

    @Column(name = "number", nullable = false, unique = true)
    private Long              number;

    @Column(name = "size", nullable = false)
    private Long              size;

    public AuditMetadata getAudit() {
        return audit;
    }

    public String getDescription() {
        return description;
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

    public void setAudit(final AuditMetadata value) {
        audit = value;
    }

    public void setDescription(final String value) {
        description = value;
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

    public void setSize(final Long value) {
        size = value;
    }

    @Override
    public String toString() {
        return "ImageEntity [id=" + id + ", number=" + number + ", name=" + name + ", key=" + key + ", audit=" + audit
                + "]";
    }
}
