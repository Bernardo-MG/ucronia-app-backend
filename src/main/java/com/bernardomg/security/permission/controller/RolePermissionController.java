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

package com.bernardomg.security.permission.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.security.permission.authorization.AuthorizedResource;
import com.bernardomg.security.permission.constant.Actions;
import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.permission.service.RolePermissionService;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.model.request.ValidatedPermissionCreate;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Fee REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/security/role/{id}/permission")
@AllArgsConstructor
public class RolePermissionController {

    private final RolePermissionService service;

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "ROLE", action = Actions.UPDATE)
    public RolePermission add(@PathVariable("id") final long id,
            @Valid @RequestBody final ValidatedPermissionCreate permission) {
        return service.addPermission(id, permission.getResourceId(), permission.getActionId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "ROLE", action = Actions.READ)
    public Iterable<Permission> readAll(@PathVariable("id") final long id, final Pageable pageable) {
        return service.getPermissions(id, pageable);
    }

    @DeleteMapping(path = "/{resource}/{action}", produces = MediaType.APPLICATION_JSON_VALUE)
    @AuthorizedResource(resource = "ROLE", action = Actions.UPDATE)
    public RolePermission remove(@PathVariable("id") final long id, @PathVariable("resource") final Long resource,
            @PathVariable("action") final Long action) {
        return service.removePermission(id, resource, action);
    }

}