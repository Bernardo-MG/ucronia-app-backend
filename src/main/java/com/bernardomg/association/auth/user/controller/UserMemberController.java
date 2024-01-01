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

package com.bernardomg.association.auth.user.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.auth.user.model.UserMember;
import com.bernardomg.association.auth.user.service.UserMemberService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authentication.user.cache.UserCaches;
import com.bernardomg.security.authorization.permission.constant.Actions;

import lombok.AllArgsConstructor;

/**
 * User member REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/security/user/{userId}/member")
@AllArgsConstructor
@Transactional
public class UserMemberController {

    /**
     * User member service.
     */
    private final UserMemberService service;

    /**
     * Assigns a member to a user.
     *
     * @param userId
     *            user to assign the member
     * @param memberId
     *            member to assign
     * @return added permission
     */
    @PostMapping(path = "/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { UserCaches.USERS, UserCaches.USER }, allEntries = true) })
    public UserMember assign(@PathVariable("userId") final long userId, @PathVariable("memberId") final long memberId) {
        return service.assignMember(userId, memberId);
    }

    /**
     * Removes the member assigned to a user.
     *
     * @param userId
     *            user to assign the member
     * @param memberId
     *            member to assign
     * @return added permission
     */
    @DeleteMapping(path = "/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { UserCaches.USERS, UserCaches.USER }, allEntries = true) })
    public void delete(@PathVariable("userId") final long userId, @PathVariable("memberId") final long memberId) {
        service.deleteMember(userId, memberId);
    }

    /**
     * Reads the member assigned to a user.
     *
     * @param userId
     *            user to assign the member
     * @return added permission
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { UserCaches.USERS, UserCaches.USER }, allEntries = true) })
    public UserMember read(@PathVariable("userId") final long userId) {
        return service.readMember(userId);
    }

    /**
     * Updates the member assigned to a user.
     *
     * @param userId
     *            user to assign the member
     * @param memberId
     *            member to assign
     * @return added permission
     */
    @PutMapping(path = "/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "USER", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { UserCaches.USERS, UserCaches.USER }, allEntries = true) })
    public UserMember update(@PathVariable("userId") final long userId, @PathVariable("memberId") final long memberId) {
        return service.updateMember(userId, memberId);
    }

}
