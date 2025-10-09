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

package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserPersonEntity;

public interface UserPersonSpringRepository extends JpaRepository<UserPersonEntity, Long> {

    public void deleteByUserId(final long id);

    @Query("""
               SELECT CASE WHEN COUNT(um) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM UserMember um
                 JOIN um.person p
                 JOIN um.user u
               WHERE p.number = :number AND u.username != :username
            """)
    public boolean existsByNotUsernameAndMemberNumber(@Param("username") final String username,
            @Param("number") final long number);

    public boolean existsByUserId(final long id);

    /**
     * Returns all the members not assigned to a user, in a paginated form.
     *
     * @param page
     *            pagination to apply
     * @return a page with the members not assigned to a user
     */
    @Query("""
               SELECT p
               FROM Person p
                 LEFT JOIN UserMember um ON p.number = um.person.number
               WHERE um.person IS NULL
            """)
    public Page<PersonEntity> findAllNotAssigned(final Pageable page);

    public Optional<UserPersonEntity> findByUserId(final long id);

}
