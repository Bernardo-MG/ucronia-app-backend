
package com.bernardomg.association.guest.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Guest")
@Table(schema = "directory", name = "guests")
@SecondaryTable(schema = "directory", name = "profiles",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class QueryGuestEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long                          serialVersionUID = 8139806507534262996L;

    @Column(name = "birth_date", table = "profiles")
    private Instant                                    birthDate;

    @Column(name = "comments", table = "profiles")
    private String                                     comments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Collection<QueryGuestContactChannelEntity> contactChannels;

    @Column(name = "first_name", table = "profiles", nullable = false)
    private String                                     firstName;

    @ElementCollection
    @CollectionTable(schema = "directory", name = "guest_games", joinColumns = @JoinColumn(name = "guest_id"))
    @Column(name = "date", nullable = false)
    private Collection<Instant>                        games            = new HashSet<>();

    @Id
    @Column(name = "id", table = "guests", nullable = false, unique = true)
    private Long                                       id;

    @Column(name = "identifier", table = "profiles")
    private String                                     identifier;

    @Column(name = "last_name", table = "profiles")
    private String                                     lastName;

    @Column(name = "number", table = "profiles")
    private Long                                       number;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "types", table = "profiles")
    private Set<String>                                types;

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

    public Instant getBirthDate() {
        return birthDate;
    }

    public String getComments() {
        return comments;
    }

    public Collection<QueryGuestContactChannelEntity> getContactChannels() {
        return contactChannels;
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

    public String getIdentifier() {
        return identifier;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getNumber() {
        return number;
    }

    public Set<String> getTypes() {
        return types;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setBirthDate(final Instant birthDate) {
        this.birthDate = birthDate;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setContactChannels(final Collection<QueryGuestContactChannelEntity> contactChannels) {
        this.contactChannels = contactChannels;
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

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public void setTypes(final Set<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "QueryGuestEntity [id=" + id + ", identifier=" + identifier + ", firstName=" + firstName + ", lastName="
                + lastName + ", birthDate=" + birthDate + ", comments=" + comments + ", contactChannels="
                + contactChannels + ", games=" + games + ", number=" + number + ", types=" + types + "]";
    }

}
