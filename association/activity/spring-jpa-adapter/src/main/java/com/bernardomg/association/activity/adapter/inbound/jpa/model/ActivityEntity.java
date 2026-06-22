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

package com.bernardomg.association.activity.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Activity")
@Table(schema = "calendar", name = "activities")
public class ActivityEntity implements Serializable {

    @Transient
    private static final long  serialVersionUID = 4603617058960663867L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id", nullable = false)
    private CalendarDateEntity calendarDate;

    @Column(name = "description", length = 200)
    private String             description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long               id;

    @Column(name = "image", length = 100)
    private String             image;

    @Column(name = "number", nullable = false, unique = true)
    private Long               number;

    public CalendarDateEntity getCalendarDate() {
        return calendarDate;
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

    public Long getNumber() {
        return number;
    }

    public void setCalendarDate(final CalendarDateEntity calendarDate) {
        this.calendarDate = calendarDate;
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

    public void setNumber(final Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ActivityEntity [id=" + id + ", number=" + number + ", calendarDate=" + calendarDate + ", description="
                + description + ", image=" + image + "]";
    }

}
