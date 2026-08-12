/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
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

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.KeyEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.KeyEntityMapper;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;

@Transactional
public final class JpaKeyRepository implements KeyRepository {

    private final KeySpringRepository keySpringRepository;

    public JpaKeyRepository(final KeySpringRepository keySpringRepository) {
        super();

        this.keySpringRepository = Objects.requireNonNull(keySpringRepository);
    }

    @Override
    public void delete(final Long number) {
        keySpringRepository.deleteByNumber(number);
    }

    @Override
    public boolean exists(final Long number) {
        return keySpringRepository.existsByNumber(number);
    }

    @Override
    public Collection<Key> findAll() {
        return keySpringRepository.findAllByOrderByNumberAsc()
            .stream()
            .map(KeyEntityMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Key> findOne(final Long number) {
        return keySpringRepository.findByNumber(number)
            .map(KeyEntityMapper::toDomain);
    }

    @Override
    public Key save(final Key key) {
        final KeyEntity entity;

        entity = keySpringRepository.findByNumber(key.number())
            .orElseGet(KeyEntity::new);

        entity.setNumber(key.number());
        entity.setAvailable(key.available());
        entity.setDescription(key.description());

        return KeyEntityMapper.toDomain(keySpringRepository.save(entity));
    }

}
