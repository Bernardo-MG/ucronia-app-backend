
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MemberProfile")
@Table(schema = "directory", name = "members")
@SecondaryTable(schema = "directory", name = "profiles",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class QueryMemberProfileEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long                           serialVersionUID = 2746178038075808052L;

    @Column(name = "active", table = "members", nullable = false)
    private Boolean                                     active;

    @Column(name = "address", table = "profiles")
    private String                                      address;

    @Column(name = "birth_date", table = "profiles")
    private Instant                                     birthDate;

    @Column(name = "comments", table = "profiles")
    private String                                      comments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Collection<QueryMemberContactChannelEntity> contactChannels;

    @ManyToOne
    @JoinColumn(name = "fee_type_id")
    private FeeTypeEntity                               feeType;

    @Column(name = "first_name", table = "profiles", nullable = false)
    private String                                      firstName;

    @Id
    @Column(name = "id", table = "members", nullable = false, unique = true)
    private Long                                        id;

    @Column(name = "identifier", table = "profiles")
    private String                                      identifier;

    @Column(name = "last_name", table = "profiles")
    private String                                      lastName;

    @Column(name = "number", table = "profiles")
    private Long                                        number;

    @Column(name = "renew_membership", table = "members", nullable = false)
    private Boolean                                     renew;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "types", table = "profiles")
    private Set<String>                                 types;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final QueryMemberProfileEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Boolean getActive() {
        return active;
    }

    public String getAddress() {
        return address;
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

    public FeeTypeEntity getFeeType() {
        return feeType;
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

    public Set<String> getTypes() {
        return types;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setAddress(final String address) {
        this.address = address;
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

    public void setFeeType(final FeeTypeEntity feeType) {
        this.feeType = feeType;
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

    public void setTypes(final Set<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "QueryMemberProfileEntity [id=" + id + ", identifier=" + identifier + ", feeType=" + feeType
                + ", firstName=" + firstName + ", lastName=" + lastName + ", active=" + active + ", birthDate="
                + birthDate + ", comments=" + comments + ", address=" + address + ", contactChannels=" + contactChannels
                + ", number=" + number + ", renew=" + renew + ", types=" + types + "]";
    }

}
