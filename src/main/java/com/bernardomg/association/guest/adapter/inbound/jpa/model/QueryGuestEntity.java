
package com.bernardomg.association.guest.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
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

@Entity(name = "Guest")
@Table(schema = "directory", name = "guests")
@SecondaryTable(schema = "directory", name = "contacts",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class QueryGuestEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long   serialVersionUID = 8139806507534262996L;

    @Column(name = "first_name", table = "contacts", nullable = false)
    private String              firstName;

    @ElementCollection
    @CollectionTable(schema = "directory", name = "guest_games", joinColumns = @JoinColumn(name = "guest_id"))
    @Column(name = "date", nullable = false)
    private Collection<Instant> games            = new HashSet<>();

    @Id
    @Column(name = "id", table = "guests", nullable = false, unique = true)
    private Long                id;

    @Column(name = "last_name", table = "contacts")
    private String              lastName;

    @Column(name = "number", table = "contacts")
    private Long                number;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final QueryGuestEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public String getFirstName() {
        return firstName;
    }

    public Collection<Instant> getGames() {
        return games;
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setGames(final Collection<Instant> games) {
        this.games = games;
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

    @Override
    public String toString() {
        return "QueryGuestEntity [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", number="
                + number + ", games=" + games + "]";
    }

}
