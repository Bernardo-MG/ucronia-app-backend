
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Member")
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "identifier")
    private String            identifier;

    @Column(name = "name", nullable = false)
    private String            name;

    @Column(name = "number")
    private Long              number;

    @Column(name = "phone")
    private String            phone;

    @Column(name = "surname")
    private String            surname;

}
