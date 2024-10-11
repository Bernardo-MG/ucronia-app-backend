
package com.bernardomg.association.person.adapter.inbound.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "association", name = "memberships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MembershipEntity {

    @Column(name = "active", nullable = false)
    private Boolean      active;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "person", referencedColumnName = "id", nullable = false, unique = true)
    private PersonEntity person;

}
