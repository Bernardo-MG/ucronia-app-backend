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

package com.bernardomg.association.sponsor.adapter.inbound.jpa.model;

import java.util.ArrayList;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.sponsor.domain.model.Sponsor;

/**
 * Query sponsor entity mapper.
 */
public final class QuerySponsorEntityMapper {

    public static final Sponsor toDomain(final QuerySponsorEntity entity) {
        final ContactName name;

        name = new ContactName(entity.getFirstName(), entity.getLastName());
        return new Sponsor(entity.getNumber(), name, new ArrayList<>(entity.getYears()));
    }

    public static final QuerySponsorEntity toEntity(final Sponsor data) {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setFirstName(data.name()
            .firstName());
        entity.setLastName(data.name()
            .lastName());
        entity.setYears(new ArrayList<>(data.years()));

        return entity;
    }

    private QuerySponsorEntityMapper() {
        super();
    }

}
