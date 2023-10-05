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

package com.bernardomg.security.permission.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.security.permission.persistence.model.PersistentPermission;

/**
 * Repository for action.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PermissionRepository extends JpaRepository<PersistentPermission, Long> {

    @Query("SELECT p FROM Permission p WHERE p.id NOT IN (SELECT p.id FROM RolePermission rp INNER JOIN Permission p ON rp.permissionId = p.id WHERE rp.granted = true AND rp.roleId = :roleId)")
    public Page<PersistentPermission> findAvailableToRole(@Param("roleId") final Long roleId, final Pageable pageable);

    @Query("SELECT p FROM RolePermission rp INNER JOIN Permission p ON rp.permissionId = p.id WHERE rp.granted = true AND rp.roleId = :roleId")
    public Page<PersistentPermission> findByRole(@Param("roleId") final Long roleId, final Pageable pageable);

}
