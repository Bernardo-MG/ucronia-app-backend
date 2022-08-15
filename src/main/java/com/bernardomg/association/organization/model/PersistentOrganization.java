
package com.bernardomg.association.organization.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Organization")
@Table(name = "organizations")
public class PersistentOrganization implements Serializable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 8168282556339183836L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "name", nullable = false, unique = true)
    private String            name;

}
