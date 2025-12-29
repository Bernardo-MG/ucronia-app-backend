
package com.bernardomg.association.guest.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "UpdateGuest")
@Table(schema = "directory", name = "guests")
public class UpdateGuestEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long   serialVersionUID = 8139806507534262996L;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private ProfileEntity       profile;

    @ElementCollection
    @CollectionTable(schema = "directory", name = "guest_games", joinColumns = @JoinColumn(name = "guest_id"))
    @Column(name = "date", nullable = false)
    private Collection<Instant> games            = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                id;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final UpdateGuestEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public Collection<Instant> getGames() {
        return games;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setProfile(final ProfileEntity profile) {
        this.profile = profile;
    }

    public void setGames(final Collection<Instant> games) {
        this.games = games;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateGuestEntity [id=" + id + ", profile=" + profile + ", games=" + games + "]";
    }

}
