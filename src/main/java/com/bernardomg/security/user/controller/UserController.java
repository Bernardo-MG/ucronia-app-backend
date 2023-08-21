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

package com.bernardomg.security.user.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.security.permission.authorization.AuthorizedResource;
import com.bernardomg.security.permission.constant.Actions;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserQuery;
import com.bernardomg.security.user.model.request.ValidatedUserUpdate;
import com.bernardomg.security.user.service.UserService;

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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @AuthorizedResource(resource = "USER", action = Actions.CREATE)
    public User create(@Valid @RequestBody final ValidatedUserCreate user) {
        return service.registerNewUser(user);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.DELETE)
    public void delete(@PathVariable("id") final long id) {
        service.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.READ)
    public Iterable<User> readAll(@Valid final ValidatedUserQuery user, final Pageable pageable) {
        return service.getAll(user, pageable);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.READ)
    public User readOne(@PathVariable("id") final long id) {
        return service.getOne(id)
            .orElse(null);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.UPDATE)
    public User update(@PathVariable("id") final long id, @Valid @RequestBody final ValidatedUserUpdate form) {
        return service.update(id, form);
    }

}
