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

package com.bernardomg.security.permission.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto implementation of {@code Action}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Entity(name = "RoleGrantedPermission")
@Table(name = "role_granted_permissions")
@IdClass(RoleGrantedPermissionKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistentRoleGrantedPermission implements Serializable {

    /**
     * Serialization id.
     */
    private static final long serialVersionUID = 8513041662486312372L;

    @Column(name = "action", nullable = false)
    private String            action;

    @Id
    @Column(name = "action_id", nullable = false)
    private Long              actionId;

    @Column(name = "resource", nullable = false)
    private String            resource;

    @Id
    @Column(name = "resource_id", nullable = false)
    private Long              resourceId;

    @Column(name = "role", nullable = false)
    private String            role;

    @Id
    @Column(name = "role_id", nullable = false)
    private Long              roleId;

}