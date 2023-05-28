/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.security.data.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.model.request.DtoRoleCreationRequest;
import com.bernardomg.security.data.model.request.DtoUserCreationRequest;
import com.bernardomg.security.data.model.request.DtoUserQueryRequest;
import com.bernardomg.security.data.model.request.DtoUserUpdateRequest;
import com.bernardomg.security.data.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/security/user")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PutMapping(path = "/{id}/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean addRole(@PathVariable("id") final Long id, @Valid @RequestBody final DtoRoleCreationRequest role) {
        return service.addRole(id, role.getId());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User create(@Valid @RequestBody final DtoUserCreationRequest user) {
        return service.create(user);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable("id") final Long id) {
        return service.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> readAll(final DtoUserQueryRequest user, final Pageable pageable) {
        return service.getAll(user, pageable);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User readOne(@PathVariable("id") final Long id) {
        return service.getOne(id)
            .orElse(null);
    }

    @GetMapping(path = "/{id}/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Role> readRoles(@PathVariable("id") final Long id, final Pageable pageable) {
        return service.getRoles(id, pageable);
    }

    @DeleteMapping(path = "/{id}/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean removeRole(@PathVariable("id") final Long id, @PathVariable("role") final Long role) {
        return service.removeRole(id, role);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@PathVariable("id") final Long id, @Valid @RequestBody final DtoUserUpdateRequest form) {
        form.setId(id);

        return service.update(form);
    }

}
