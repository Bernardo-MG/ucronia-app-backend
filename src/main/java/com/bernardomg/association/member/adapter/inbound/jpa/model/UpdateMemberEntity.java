
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
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

@Entity(name = "UpdateMember")
@Table(schema = "directory", name = "members")
public class UpdateMemberEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 8139806507534262996L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @OneToOne
    @JoinColumn(name = "fee_type_id", referencedColumnName = "id")
    private FeeTypeEntity     feeType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private ProfileEntity     profile;

    @Column(name = "renew_membership", nullable = false)
    private Boolean           renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final UpdateMemberEntity other)) {
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

    public Long getId() {
        return id;
    }

    public ProfileEntity getProfile() {
        return profile;
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

    public void setId(final Long id) {
        this.id = id;
    }

    public void setProfile(final ProfileEntity profile) {
        this.profile = profile;
    }

    public void setRenew(final Boolean renew) {
        this.renew = renew;
    }

    @Override
    public String toString() {
        return "UpdateMemberEntity [id=" + id + ", profile=" + profile + ", feeType=" + feeType + ", active=" + active
                + ", renew=" + renew + "]";
    }

}
