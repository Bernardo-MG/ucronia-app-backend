
package com.bernardomg.association.calendar.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "CalendarType")
@Table(schema = "calendar", name = "calendar_type")
public class CalendarTypeEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = -1678780311056221469L;

    @Column(name = "color", nullable = false, length = 20)
    private String            color;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "name", nullable = false, length = 100)
    private String            name;

    @Column(name = "number", nullable = false, unique = true)
    private Long              number;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final CalendarTypeEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public String getColor() {
        return color;
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

    public void setColor(final String color) {
        this.color = color;
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
        return "CalendarTypeEntity [id=" + id + ", number=" + number + ", name=" + name + ", color=" + color + "]";
    }

}
