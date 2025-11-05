
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Member")
@Table(schema = "directory", name = "members")
@PrimaryKeyJoinColumn(name = "contact_id")
public class MemberEntity extends ContactEntity {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 4384596612578054734L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @Column(name = "renew_membership")
    private Boolean           renewMembership;

    public Boolean getActive() {
        return active;
    }

    public Boolean getRenewMembership() {
        return renewMembership;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setRenewMembership(final Boolean renewMembership) {
        this.renewMembership = renewMembership;
    }

}
