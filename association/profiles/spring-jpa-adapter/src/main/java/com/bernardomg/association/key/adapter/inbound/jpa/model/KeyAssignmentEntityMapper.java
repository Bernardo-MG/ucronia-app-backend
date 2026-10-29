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

import java.util.Optional;

import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedProfile;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.domain.model.Name;

import jakarta.persistence.Tuple;

public final class KeyAssignmentEntityMapper {

    public static final KeyAssignment toDomain(final KeyAssignmentEntity entity) {
        return toDomain(entity.getKey(), Optional.of(entity));
    }

    public static final KeyAssignment toDomain(final KeyEntity key, final Optional<KeyAssignmentEntity> assignment) {
        return new KeyAssignment(key.getNumber(), key.getAvailable(), key.getDescription(),
            assignment.map(KeyAssignmentEntity::getProfile)
                .map(KeyAssignmentEntityMapper::toDomain));
    }

    public static final KeyAssignment toDomain(final Tuple row) {
        final Optional<AssignedProfile> profile;

        profile = Optional.ofNullable(row.get("profileNumber", Long.class))
            .map(number -> new AssignedProfile(number,
                new Name(row.get("firstName", String.class), Optional.ofNullable(row.get("lastName", String.class))
                    .orElse(""), Optional.ofNullable(row.get("nickname", String.class))).fullName()));

        return new KeyAssignment(row.get("number", Long.class), row.get("available", Boolean.class),
            row.get("description", String.class), profile);
    }

    private static final AssignedProfile toDomain(final ProfileEntity profile) {
        final Name name;

        name = new Name(profile.getFirstName(), Optional.ofNullable(profile.getLastName())
            .orElse(""), Optional.ofNullable(profile.getNickname()));
        return new AssignedProfile(profile.getNumber(), name.fullName());
    }

    private KeyAssignmentEntityMapper() {
        super();
    }

}
