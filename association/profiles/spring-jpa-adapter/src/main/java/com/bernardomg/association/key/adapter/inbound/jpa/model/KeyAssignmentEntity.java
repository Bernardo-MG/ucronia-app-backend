/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
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

package com.bernardomg.association.key.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "KeyAssignment")
@Table(schema = "directory", name = "key_assignments")
public class KeyAssignmentEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "key_id", nullable = false)
    private Long              id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "key_id", nullable = false)
    private KeyEntity         key;

    @OneToOne(optional = false)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private ProfileEntity     profile;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final KeyAssignmentEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Long getId() {
        return id;
    }

    public KeyEntity getKey() {
        return key;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setKey(final KeyEntity key) {
        this.key = key;
    }

    public void setProfile(final ProfileEntity profile) {
        this.profile = profile;
    }

}
