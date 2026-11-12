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

package com.bernardomg.association.member.adapter.outbound.rest.security;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

public final class PublicMemberStatusAuthorizer {

    private final ResourcePermissionEvaluator permissionEvaluator;

    public PublicMemberStatusAuthorizer(final ResourcePermissionEvaluator permissionEvaluator) {
        super();

        this.permissionEvaluator = Objects.requireNonNull(permissionEvaluator);
    }

    public final void checkCanRead(final MemberStatus status) {
        final Authentication authentication;

        if (status != MemberStatus.ACTIVE) {
            authentication = SecurityContextHolder.getContext()
                .getAuthentication();
            if ((authentication == null)
                    || !permissionEvaluator.isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ)) {
                throw new AccessDeniedException("No permission to read inactive members");
            }
        }
    }

}
