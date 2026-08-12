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

package com.bernardomg.association.member.adapter.outbound.rest.model;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyCreationDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyUpdateDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeysResponseDto;
import com.bernardomg.association.member.domain.model.Key;

public final class KeyDtoMapper {

    public static Key toDomain(final KeyCreationDto creation) {
        final Boolean available;
        final String  description;

        if (creation.getAvailable() == null) {
            available = false;
        } else {
            available = creation.getAvailable();
        }
        if (creation.getDescription() == null) {
            description = "";
        } else {
            description = creation.getDescription();
        }

        return new Key(creation.getNumber(), available, description);
    }

    public static Key toDomain(final long number, final KeyUpdateDto change) {
        return new Key(number, change.getAvailable(), change.getDescription());
    }

    public static KeysResponseDto toResponseDto(final Collection<Key> keys) {
        return new KeysResponseDto().content(keys.stream()
            .map(KeyDtoMapper::toDto)
            .toList());
    }

    public static KeyResponseDto toResponseDto(final Key key) {
        return new KeyResponseDto().content(toDto(key));
    }

    public static KeyResponseDto toResponseDto(final Optional<Key> key) {
        return new KeyResponseDto().content(key.map(KeyDtoMapper::toDto)
            .orElse(null));
    }

    private static KeyDto toDto(final Key key) {
        return new KeyDto().number(key.number())
            .available(key.available())
            .description(key.description());
    }

    private KeyDtoMapper() {
        super();
    }

}
