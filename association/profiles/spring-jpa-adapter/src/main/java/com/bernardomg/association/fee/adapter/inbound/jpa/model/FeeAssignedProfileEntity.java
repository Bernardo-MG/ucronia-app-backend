
package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "FeeAssignedProfile")
@Table(schema = "security", name = "users")
public class FeeAssignedProfileEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -6158871790662554821L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long              id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(schema = "security", name = "user_profiles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
    private FeeProfileEntity  profile;

    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String            username;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final FeeAssignedProfileEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Long getId() {
        return id;
    }

    public FeeProfileEntity getProfile() {
        return profile;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, profile, username);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setProfile(final FeeProfileEntity profile) {
        this.profile = profile;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "FeeAssignedProfileEntity [id=" + id + ", username=" + username + ", profile=" + profile + "]";
    }

}
