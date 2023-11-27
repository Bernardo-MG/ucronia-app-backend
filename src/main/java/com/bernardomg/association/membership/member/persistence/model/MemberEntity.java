
package com.bernardomg.association.membership.member.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Member")
@Table(name = "members")
@TableGenerator(name = "seq_members_id", table = "sequences", pkColumnName = "sequence", valueColumnName = "count",
        allocationSize = 1)
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

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_members_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "identifier")
    private String            identifier;

    @Column(name = "name", nullable = false)
    private String            name;

    @Column(name = "phone")
    private String            phone;

    @Column(name = "surname")
    private String            surname;

}
