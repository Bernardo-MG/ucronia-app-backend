
package com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "GameSystem")
@Table(schema = "inventory", name = "game_systems")
public class GameSystemEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "name", nullable = false)
    private String            name;

    @Column(name = "number", nullable = false, unique = true)
    private Long              number;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNumber() {
        return number;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

}
