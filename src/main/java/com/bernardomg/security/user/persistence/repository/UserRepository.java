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

package com.bernardomg.security.user.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.persistence.model.PersistentUser;

/**
 * Repository for users.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface UserRepository extends JpaRepository<PersistentUser, Long> {

    /**
     * Returns whether an user with the given email exists.
     *
     * @param email
     *            email to search for
     * @return {@code true} if the user exists, {@code false} otherwise
     */
    public Boolean existsByEmail(final String email);

    public Boolean existsByIdAndUsername(final Long id, final String username);

    public Boolean existsByIdNotAndEmail(final Long Id, final String email);

    /**
     * Returns whether an user with the given username exists.
     *
     * @param username
     *            username to search for
     * @return {@code true} if the user exists, {@code false} otherwise
     */
    public Boolean existsByUsername(final String username);

    @Query("SELECT r FROM Role r JOIN UserRoles ur ON r.id = ur.roleId JOIN User u ON ur.userId = u.id WHERE u.id = :id")
    public Page<Role> findAllRoles(@Param("id") final Long id, final Pageable pageable);

    /**
     * Returns the user for the received email.
     *
     * @param email
     *            email to search for
     * @return the user details for the received email
     */
    public Optional<PersistentUser> findOneByEmail(final String email);

    /**
     * Returns the user for the received username.
     *
     * @param username
     *            username to search for
     * @return the user details for the received username
     */
    public Optional<PersistentUser> findOneByUsername(final String username);

}
