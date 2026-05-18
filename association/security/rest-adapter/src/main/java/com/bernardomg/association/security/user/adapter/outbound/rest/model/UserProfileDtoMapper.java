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

package com.bernardomg.association.security.user.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.adapter.outbound.rest.dto.ProfileDto;
import com.bernardomg.association.security.adapter.outbound.rest.dto.ProfileNameDto;
import com.bernardomg.association.security.adapter.outbound.rest.dto.ProfileResponseDto;

public final class UserProfileDtoMapper {

    public static final ProfileResponseDto toResponseDto(final Optional<Profile> profile) {
        return new ProfileResponseDto().content(profile.map(UserProfileDtoMapper::toDto)
            .orElse(null));
    }

    public static final ProfileResponseDto toResponseDto(final Profile profile) {
        return new ProfileResponseDto().content(UserProfileDtoMapper.toDto(profile));
    }

    private static final ProfileDto toDto(final Profile profile) {
        ProfileNameDto name;

        name = new ProfileNameDto().firstName(profile.name()
            .firstName())
            .lastName(profile.name()
                .lastName())
            .fullName(profile.name()
                .fullName());
        return new ProfileDto().identifier(profile.identifier())
            .number(profile.number())
            .name(name);
    }

    private UserProfileDtoMapper() {
        super();
    }

}
