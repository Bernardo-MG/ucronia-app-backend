
package com.bernardomg.association.sponsor.adapter.inbound.jpa.model;

import java.io.Serializable;
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

@Entity(name = "Sponsor")
@Table(schema = "directory", name = "sponsors")
public class SponsorEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long   serialVersionUID = 8139806507534262996L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private ProfileEntity       profile;

    @ElementCollection
    @CollectionTable(name = "sponsor_years", schema = "directory", joinColumns = @JoinColumn(name = "sponsor_id"))
    @Column(name = "year")
    private Collection<Integer> years            = new HashSet<>();

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final SponsorEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Long getId() {
        return id;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public Collection<Integer> getYears() {
        return years;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setProfile(final ProfileEntity profile) {
        this.profile = profile;
    }

    public void setYears(final Collection<Integer> years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "UpdateSponsorEntity [id=" + id + ", profile=" + profile + ", years=" + years + "]";
    }

}
