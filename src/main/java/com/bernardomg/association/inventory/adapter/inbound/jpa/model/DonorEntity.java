
package com.bernardomg.association.inventory.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Donor")
@Table(schema = "inventory", name = "donors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class DonorEntity implements Serializable {

    private static final long serialVersionUID = 7433916087090772826L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "member")
    private Long              member;

    @Column(name = "name", nullable = false)
    private String            name;

    @Column(name = "number", nullable = false)
    private Long              number;

}
