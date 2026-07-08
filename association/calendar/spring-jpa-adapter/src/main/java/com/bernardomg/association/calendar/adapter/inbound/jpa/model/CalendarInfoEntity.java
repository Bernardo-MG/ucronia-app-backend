/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.calendar.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "CalendarInfo")
@Table(schema = "calendar", name = "calendar_info")
@Inheritance(strategy = InheritanceType.JOINED)
public class CalendarInfoEntity implements Serializable {

    @Transient
    private static final long       serialVersionUID = 4603617058960663867L;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "calendar", name = "calendar_info_dates",
            joinColumns = @JoinColumn(name = "calendar_info_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "calendar_date_id", nullable = false))
    @OrderBy("start, end ASC")
    private Set<CalendarDateEntity> calendarDates;

    @Column(name = "description", length = 200)
    private String                  description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                    id;

    @Column(name = "image", length = 200)
    private String                  image;

    @Column(name = "location", length = 200)
    private String                  location;

    @Column(name = "number", nullable = false, unique = true)
    private Long                    number;

    @Column(name = "title", length = 100)
    private String                  title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "calendar", name = "calendar_info_types",
            joinColumns = @JoinColumn(name = "calendar_info_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "calendar_type_id", nullable = false))
    private Set<CalendarTypeEntity> types;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final CalendarInfoEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Set<CalendarDateEntity> getCalendarDates() {
        return calendarDates;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public Long getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public Set<CalendarTypeEntity> getTypes() {
        return types;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setCalendarDates(final Set<CalendarDateEntity> calendarDates) {
        this.calendarDates = calendarDates;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setTypes(final Set<CalendarTypeEntity> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "ActivityEntity [id=" + id + ", number=" + number + ", calendarDates=" + calendarDates + ", description="
                + description + ", location=" + location + ", title=" + title + ", image=" + image + ", types=" + types
                + "]";
    }

}
