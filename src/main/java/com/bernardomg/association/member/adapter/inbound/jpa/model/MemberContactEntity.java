
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.util.Objects;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MemberContact")
@Table(schema = "directory", name = "members")
@PrimaryKeyJoinColumn(name = "contact_id", referencedColumnName = "id")
public class MemberContactEntity extends ContactEntity {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 4384596612578054734L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @Column(name = "renew_membership")
    private Boolean           renewMembership;

    @Override
    public boolean equals(final Object obj) {
        // Basic checks
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || (getClass() != obj.getClass())) {
            return false;
        }
        final MemberContactEntity other = (MemberContactEntity) obj;
        return Objects.equals(active, other.active) && Objects.equals(renewMembership, other.renewMembership);
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getRenewMembership() {
        return renewMembership;
    }

    @Override
    public int hashCode() {
        // Combine superclass and subclass fields
        return Objects.hash(super.hashCode(), active, renewMembership);
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setRenewMembership(final Boolean renewMembership) {
        this.renewMembership = renewMembership;
    }

    @Override
    public String toString() {
        return "MemberEntity [" + "id=" + getId() + ", firstName=" + getFirstName() + ", lastName=" + getLastName()
                + ", identifier=" + getIdentifier() + ", number=" + getNumber() + ", birthDate=" + getBirthDate()
                + ", active=" + active + ", renewMembership=" + renewMembership + "]";
    }

}
