
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Member")
@Table(schema = "directory", name = "members")
public class MemberEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 8139806507534262996L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private ContactEntity     contact;

    @Id
    @Column(name = "contact_id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "renew_membership", nullable = false)
    private Boolean           renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final MemberEntity other = (MemberEntity) obj;
        return Objects.equals(active, other.active) && Objects.equals(contact, other.contact)
                && Objects.equals(id, other.id) && Objects.equals(renew, other.renew);
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
        return Objects.hash(active, contact, id, renew);
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
        return "MemberEntity [active=" + active + ", contact=" + contact + ", id=" + id + ", renew=" + renew + "]";
    }

}
