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

package com.bernardomg.association.member.adapter.inbound.jpa.model;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.domain.model.ProfileName;

/**
 * Update member entity mapper.
 */
public final class UpdateMemberEntityMapper {

    public static final Member toDomain(final UpdateMemberEntity entity) {
        final ProfileName name;

        name = new ProfileName(entity.getProfile()
            .getFirstName(),
            entity.getProfile()
                .getLastName());
        return new Member(entity.getProfile()
            .getNumber(), name, entity.getActive(), entity.getRenew());
    }

    public static final UpdateMemberEntity toEntity(final Member data) {
        final UpdateMemberEntity entity;
        final ProfileEntity      profile;

        profile = new ProfileEntity();
        profile.setNumber(data.number());
        profile.setFirstName(data.name()
            .firstName());
        profile.setLastName(data.name()
            .lastName());

        entity = new UpdateMemberEntity();
        entity.setProfile(profile);
        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
    }

    public static final UpdateMemberEntity toEntity(final UpdateMemberEntity entity, final Member data) {

        entity.getProfile()
            .setFirstName(data.name()
                .firstName());
        entity.getProfile()
            .setLastName(data.name()
                .lastName());
        entity.setActive(data.active());
        entity.setRenew(data.renew());

        return entity;
    }

    private UpdateMemberEntityMapper() {
        super();
    }

}
