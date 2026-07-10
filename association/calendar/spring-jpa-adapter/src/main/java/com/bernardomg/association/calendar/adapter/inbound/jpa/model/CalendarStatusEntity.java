
package com.bernardomg.association.calendar.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.calendar.domain.model.CalendarStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "CalendarStatus")
@Table(schema = "calendar", name = "calendar_status")
public class CalendarStatusEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 1646672820201318399L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private CalendarStatus    name;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final CalendarStatusEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Long getId() {
        return id;
    }

    public CalendarStatus getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final CalendarStatus name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CalendarStatusEntity [id=" + id + ", name=" + name + "]";
    }

}
