
package com.bernardomg.association.crud.member.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "Member")
@Table(name = "members")
@TableGenerator(name = "seq_members_id", table = "sequences", pkColumnName = "seq_name", valueColumnName = "seq_count",
        initialValue = 10, allocationSize = 1)
@Data
public class PersistentMember implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "active", nullable = false, unique = true)
    private Boolean           active;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_members_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "identifier", nullable = false, unique = true)
    private String            identifier;

    @Column(name = "name", nullable = false, unique = true)
    private String            name;

    @Column(name = "phone", nullable = false, unique = true)
    private String            phone;

    @Column(name = "surname", nullable = false, unique = true)
    private String            surname;

}
