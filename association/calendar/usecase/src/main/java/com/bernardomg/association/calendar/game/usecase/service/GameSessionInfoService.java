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

package com.bernardomg.association.calendar.game.usecase.service;

import java.util.Optional;

import com.bernardomg.association.calendar.game.domain.model.GameSessionInfo;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

/**
 * GameSessionInfo service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface GameSessionInfoService {

    /**
     * Persists the received game session.
     *
     * @param gameSessionInfo
     *            game session to persist
     * @return the persisted game session
     */
    public GameSessionInfo create(final GameSessionInfo gameSessionInfo);

    /**
     * Deletes the game session with the received id.
     *
     * @param id
     *            id of the game session to delete
     * @return the deleted game session
     */
    public GameSessionInfo delete(final long id);

    /**
     * Returns all the game sessions matching the sample. If the sample fields are empty, then all the game sessions are
     * returned.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the game sessions matching the sample
     */
    public Page<GameSessionInfo> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the game session for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the game session to acquire
     * @return an {@code Optional} with the game session, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<GameSessionInfo> getOne(final long id);

    /**
     * Updates the received game session.
     *
     * @param gameSessionInfo
     *            new data for the game session
     * @return the updated game session
     */
    public GameSessionInfo update(final GameSessionInfo gameSessionInfo);

}
