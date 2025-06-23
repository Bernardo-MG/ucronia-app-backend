/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.security.user.usecase.service.UserPersonService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

/**
 * User member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/security/user/{username}/person")
public class UserPersonController {

    /**
     * User member service.
     */
    private final UserPersonService service;

    public UserPersonController(final UserPersonService service) {
        super();
        this.service = service;
    }

    /**
     * Assigns a member to a user.
     *
     * @param username
     *            username of the user to assign the member
     * @param memberNumber
     *            member to assign
     * @return added permission
     */
    @PostMapping(path = "/{memberNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.UPDATE)
    public Person assign(@PathVariable("username") final String username,
            @PathVariable("memberNumber") final long memberNumber) {
        return service.assignPerson(username, memberNumber);
    }

    /**
     * Reads the member assigned to a user.
     *
     * @param username
     *            username of the user to read the member
     * @return member assigned to the user
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.READ)
    public Person read(@PathVariable("username") final String username) {
        return service.getPerson(username)
            .orElse(null);
    }

    /**
     * Reads members available to assign.
     *
     * @return members available to assign
     */
    @GetMapping(path = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.READ)
    public Iterable<Person> readAvailable(final Pagination pagination, final Sorting sorting) {
        return service.getAvailablePerson(pagination, sorting);
    }

    /**
     * Removes the member assigned to a user.
     *
     * @param username
     *            username of the user to delete the member
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.UPDATE)
    public void unassign(@PathVariable("username") final String username) {
        service.unassignPerson(username);
    }

}
