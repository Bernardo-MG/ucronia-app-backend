
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

@Entity(name = "MemberContact")
@Table(schema = "directory", name = "members")
@SecondaryTable(schema = "directory", name = "contacts",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class QueryMemberContactEntity implements Serializable {

    /**
     *
     */
    private static final long                           serialVersionUID = 2746178038075808052L;

    @Column(name = "active", table = "members", nullable = false)
    private Boolean                                     active;

    @Column(name = "birth_date", table = "contacts")
    private Instant                                     birthDate;

    @Column(name = "comments", table = "contacts")
    private String                                      comments;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "types", table = "contacts")
    private Collection<String>               types;

    
    public Collection<String> getTypes() {
        return types;
    }

    
    public void setTypes(Collection<String> types) {
        this.types = types;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Collection<QueryMemberContactChannelEntity> contactChannels;

    @Column(name = "first_name", table = "contacts", nullable = false)
    private String                                      firstName;

    @Id
    @Column(name = "id", table = "members", nullable = false, unique = true)
    private Long                                        id;

    @Column(name = "identifier", table = "contacts")
    private String                                      identifier;

    @Column(name = "last_name", table = "contacts")
    private String                                      lastName;

    @Column(name = "number", table = "contacts")
    private Long                                        number;

    @Column(name = "renew_membership", table = "members", nullable = false)
    private Boolean                                     renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final QueryMemberContactEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Boolean getActive() {
        return active;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public String getComments() {
        return comments;
    }

    public Collection<QueryMemberContactChannelEntity> getContactChannels() {
        return contactChannels;
    }

    public String getFirstName() {
        return firstName;
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

    public Boolean getRenew() {
        return renew;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setBirthDate(final Instant birthDate) {
        this.birthDate = birthDate;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setContactChannels(final Collection<QueryMemberContactChannelEntity> contactChannels) {
        this.contactChannels = contactChannels;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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

    public void setRenew(final Boolean renew) {
        this.renew = renew;
    }

    @Override
    public String toString() {
        return "QueryMemberContactEntity [id=" + id + ", number=" + number + ", firstName=" + firstName + ", lastName="
                + lastName + ", birthDate=" + birthDate + ", comments=" + comments + ", contactChannels="
                + contactChannels + ", active=" + active + ", identifier=" + identifier + ", renew=" + renew + "]";
    }

}
