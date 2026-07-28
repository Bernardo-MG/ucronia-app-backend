
package com.bernardomg.audit.jpa;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.bernardomg.security.adapter.inbound.jpa.model.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class AuditMetadata {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant    createdAt;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, updatable = false)
    private UserEntity createdBy;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant    updatedAt;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private UserEntity updatedBy;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserEntity getUpdatedBy() {
        return updatedBy;
    }

}
