
package com.bernardomg.association.sponsor.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Sponsor")
@Table(schema = "directory", name = "sponsors")
@SecondaryTable(schema = "directory", name = "contacts",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class QuerySponsorEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long   serialVersionUID = 8139806507534262996L;

    @Column(name = "first_name", table = "contacts", nullable = false)
    private String              firstName;

    @Id
    @Column(name = "id", table = "sponsors", nullable = false, unique = true)
    private Long                id;

    @Column(name = "last_name", table = "contacts")
    private String              lastName;

    @Column(name = "number", table = "contacts")
    private Long                number;

    @ElementCollection
    @CollectionTable(name = "sponsor_years", schema = "directory", joinColumns = @JoinColumn(name = "sponsor_id"))
    @Column(name = "year")
    private Collection<Integer> years            = new HashSet<>();

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final QuerySponsorEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getNumber() {
        return number;
    }

    public Collection<Integer> getYears() {
        return years;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public void setYears(final Collection<Integer> years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "QuerySponsorEntity [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", number="
                + number + ", years=" + years + "]";
    }

}
