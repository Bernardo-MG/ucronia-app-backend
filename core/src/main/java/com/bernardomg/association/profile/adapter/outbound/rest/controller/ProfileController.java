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

package com.bernardomg.association.profile.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.profile.adapter.outbound.rest.model.ProfileDtoMapper;
import com.bernardomg.association.profile.domain.filter.ProfileQuery;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.usecase.service.ProfileService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.ProfileApi;
import com.bernardomg.ucronia.openapi.model.ProfileCreationDto;
import com.bernardomg.ucronia.openapi.model.ProfilePageResponseDto;
import com.bernardomg.ucronia.openapi.model.ProfilePatchDto;
import com.bernardomg.ucronia.openapi.model.ProfileResponseDto;
import com.bernardomg.ucronia.openapi.model.ProfileUpdateDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Profile REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ProfileController implements ProfileApi {

    /**
     * Profile service.
     */
    private final ProfileService service;

    public ProfileController(final ProfileService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "PROFILE", action = Actions.CREATE)
    public ProfileResponseDto createProfile(@Valid final ProfileCreationDto profileCreationDto) {
        final Profile profile;
        final Profile created;

        profile = ProfileDtoMapper.toDomain(profileCreationDto);
        created = service.create(profile);

        return ProfileDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "PROFILE", action = Actions.DELETE)
    public ProfileResponseDto deleteProfile(final Long number) {
        final Profile profile;

        profile = service.delete(number);

        return ProfileDtoMapper.toResponseDto(profile);
    }

    @Override
    @RequireResourceAuthorization(resource = "PROFILE", action = Actions.READ)
    public ProfilePageResponseDto getAllProfiles(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort, @Valid final String name) {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        filter = new ProfileQuery(name);
        profiles = service.getAll(filter, pagination, sorting);

        return ProfileDtoMapper.toResponseDto(profiles);
    }

    @Override
    @RequireResourceAuthorization(resource = "PROFILE", action = Actions.READ)
    public ProfileResponseDto getProfileByNumber(final Long number) {
        Optional<Profile> profile;

        profile = service.getOne(number);

        return ProfileDtoMapper.toResponseDto(profile);
    }

    @Override
    @RequireResourceAuthorization(resource = "PROFILE", action = Actions.UPDATE)
    public ProfileResponseDto patchProfile(final Long number, @Valid final ProfilePatchDto profilePatchDto) {
        final Profile profile;
        final Profile updated;

        profile = ProfileDtoMapper.toDomain(number, profilePatchDto);
        updated = service.patch(profile);

        return ProfileDtoMapper.toResponseDto(updated);
    }

    @Override
    @RequireResourceAuthorization(resource = "PROFILE", action = Actions.UPDATE)
    public ProfileResponseDto updateProfile(final Long number, @Valid final ProfileUpdateDto profileUpdateDto) {
        final Profile profile;
        final Profile updated;

        profile = ProfileDtoMapper.toDomain(number, profileUpdateDto);
        updated = service.update(profile);

        return ProfileDtoMapper.toResponseDto(updated);
    }

}
