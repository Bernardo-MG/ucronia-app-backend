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

package com.bernardomg.association.library.author.adapter.inbound.jpa.model;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

/**
 * Author repository mapper.
 */
public final class AuthorEntityMapper {

    public static final Author toDomain(final AuthorEntity entity) {
        return new Author(entity.getNumber(), entity.getName(), toDomain(entity.getAudit()));
    }

    public static final AuthorEntity toEntity(final Author domain) {
        final AuthorEntity entity;

        entity = new AuthorEntity();
        entity.setNumber(domain.number());
        entity.setName(domain.name());

        return entity;
    }

    private static final AuditUser toAuditDomain(final AuditUserEntity user) {
        final AuditUser auditUser;

        if (user == null) {
            auditUser = null;
        } else {
            auditUser = new AuditUser(user.getEmail(), user.getUsername(), user.getName());
        }

        return auditUser;
    }

    private static final AuditDetails toDomain(final AuditMetadata audit) {
        final AuditDetails auditDetails;

        if (audit == null) {
            auditDetails = new AuditDetails();
        } else {
            auditDetails = new AuditDetails(audit.getCreatedAt(), toAuditDomain(audit.getCreatedBy()),
                audit.getUpdatedAt(), toAuditDomain(audit.getUpdatedBy()));
        }

        return auditDetails;
    }

    private AuthorEntityMapper() {
        super();
    }

}
