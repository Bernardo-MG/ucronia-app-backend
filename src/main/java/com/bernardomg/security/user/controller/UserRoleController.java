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

package com.bernardomg.security.user.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.security.permission.authorization.AuthorizedResource;
import com.bernardomg.security.permission.constant.Actions;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.model.request.ValidatedUserRoleAdd;
import com.bernardomg.security.user.service.UserRoleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/security/user/{id}/role")
@AllArgsConstructor
@Transactional
public class UserRoleController {

    private final UserRoleService service;

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.UPDATE)
    public UserRole add(@PathVariable("id") final long id, @Valid @RequestBody final ValidatedUserRoleAdd role) {
        return service.addRole(id, role.getId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.READ)
    public Iterable<Role> readAll(@PathVariable("id") final long id, final Pageable pageable) {
        return service.getRoles(id, pageable);
    }

    @DeleteMapping(path = "/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "USER", action = Actions.UPDATE)
    public UserRole remove(@PathVariable("id") final long id, @PathVariable("role") final Long role) {
        return service.removeRole(id, role);
    }
}
