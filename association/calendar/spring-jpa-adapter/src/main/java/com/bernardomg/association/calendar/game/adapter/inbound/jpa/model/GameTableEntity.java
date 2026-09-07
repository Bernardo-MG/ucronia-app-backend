
package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "GameTable")
@Table(schema = "calendar", name = "game_tables")
public class GameTableEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = -1678780311056221469L;

    @Column(name = "description", nullable = false, length = 200)
    private String            description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "name", nullable = false, length = 50)
    private String            name;

    @Column(name = "number", nullable = false, unique = true)
    private Long              number;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final GameTableEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setDescription(final String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "CalendarTypeEntity [id=" + id + ", number=" + number + ", name=" + name + ", description=" + description
                + "]";
    }

}
