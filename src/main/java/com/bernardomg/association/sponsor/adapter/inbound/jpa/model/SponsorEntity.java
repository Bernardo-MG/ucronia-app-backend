
package com.bernardomg.association.sponsor.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(schema = "directory", name = "sponsors")
public class SponsorEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -358337123425520096L;

    @Id
    private Long              id;

    @ElementCollection
    @CollectionTable(name = "sponsor_years", schema = "directory", joinColumns = @JoinColumn(name = "sponsor_id"))
    @Column(name = "year")
    private Set<Integer>      years            = new HashSet<>();

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

    public Set<Integer> getYears() {
        return years;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setYears(final Set<Integer> years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "SponsorEntity [id=" + id + ", years=" + years + "]";
    }

}
