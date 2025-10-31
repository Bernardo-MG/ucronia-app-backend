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

package com.bernardomg.association.security.user.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.security.user.adapter.outbound.rest.model.UserPersonDtoMapper;
import com.bernardomg.association.security.user.usecase.service.UserPersonService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.UserPersonApi;
import com.bernardomg.ucronia.openapi.model.PersonPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * User member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class UserPersonController implements UserPersonApi {

    /**
     * User member service.
     */
    private final UserPersonService service;

    public UserPersonController(final UserPersonService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "USER", action = Actions.UPDATE)
    public PersonResponseDto assignPersonToUser(final String username, final Long memberNumber) {
        Person person;

        person = service.assignPerson(username, memberNumber);
        return UserPersonDtoMapper.toResponseDto(person);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAuthorization(resource = "USER", action = Actions.READ)
    public PersonResponseDto getAssignedPerson(final String username) {
        final Optional<Person> person;

        person = service.getPerson(username);

        return UserPersonDtoMapper.toResponseDto(person);
    }

    @Override
    @RequireResourceAuthorization(resource = "USER", action = Actions.READ)
    public PersonPageResponseDto getAvailablePersons(@Min(1) @Valid final Integer page,
            @Min(1) @Valid final Integer size, @Valid final List<String> sort) {
        Page<Person>     persons;
        final Pagination pagination;
        final Sorting    sorting;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        persons = service.getAvailablePerson(pagination, sorting);

        return UserPersonDtoMapper.toResponseDto(persons);
    }

    @Override
    @RequireResourceAuthorization(resource = "USER", action = Actions.UPDATE)
    public PersonResponseDto unassignPerson(final String username) {
        Person person;

        person = service.unassignPerson(username);

        return UserPersonDtoMapper.toResponseDto(person);
    }

}
