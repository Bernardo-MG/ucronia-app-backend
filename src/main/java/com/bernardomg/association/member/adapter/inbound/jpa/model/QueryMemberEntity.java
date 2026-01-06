
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Member")
@Table(schema = "directory", name = "members")
@SecondaryTable(schema = "directory", name = "profiles",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class QueryMemberEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 8139806507534262996L;

    @Column(name = "active", table = "members", nullable = false)
    private Boolean           active;

    @ManyToOne
    @JoinColumn(name = "fee_type_id")
    private FeeTypeEntity     feeType;

    @Column(name = "first_name", table = "profiles", nullable = false)
    private String            firstName;

    @Id
    @Column(name = "id", table = "members", nullable = false, unique = true)
    private Long              id;

    @Column(name = "last_name", table = "profiles")
    private String            lastName;

    @Column(name = "number", table = "profiles")
    private Long              number;

    @Column(name = "renew_membership", table = "members", nullable = false)
    private Boolean           renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final QueryMemberEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Boolean getActive() {
        return active;
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

    public void setFeeType(final FeeTypeEntity feeType) {
        this.feeType = feeType;
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

    public void setRenew(final Boolean renew) {
        this.renew = renew;
    }

    @Override
    public String toString() {
        return "QueryMemberEntity [id=" + id + ", feeType=" + feeType + ", firstName=" + firstName + ", lastName="
                + lastName + ", active=" + active + ", number=" + number + ", renew=" + renew + "]";
    }

}
