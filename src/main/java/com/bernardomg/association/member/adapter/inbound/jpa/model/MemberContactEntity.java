
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MemberContact")
@Table(schema = "directory", name = "members")
public class MemberContactEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 4384596612578054734L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private ContactEntity     contact;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "renew_membership")
    private Boolean           renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final MemberContactEntity other)) {
            return false;
        }
        return Objects.equals(id, other.getId());
    }

    public Boolean getActive() {
        return active;
    }

    public ContactEntity getContact() {
        return contact;
    }

    public Long getId() {
        return id;
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

    public void setContact(final ContactEntity contact) {
        this.contact = contact;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setRenew(final Boolean renew) {
        this.renew = renew;
    }

    @Override
    public String toString() {
        return "MemberContactEntity [id=" + id + ", contact=" + contact
                + ", active=" + active + ", renew=" + renew + "]";
    }

}
