
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "UpdateMemberContact")
@Table(schema = "directory", name = "members")
public class UpdateMemberContactEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 8139806507534262996L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private ProfileEntity     contact;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "renew_membership", table = "members", nullable = false)
    private Boolean           renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final UpdateMemberContactEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Boolean getActive() {
        return active;
    }

    public ProfileEntity getContact() {
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

    public void setContact(final ProfileEntity contact) {
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
        return "UpdateMemberContactEntity [id=" + id + ", contact=" + contact + ", active=" + active + ", renew="
                + renew + "]";
    }

}
