
package com.bernardomg.association.person.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(schema = "association", name = "memberships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class MembershipEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -3469522129424620731L;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false, unique = true)
    @ToString.Exclude
    private PersonEntity      person;

}
