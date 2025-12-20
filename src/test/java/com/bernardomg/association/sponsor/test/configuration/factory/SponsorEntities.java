
package com.bernardomg.association.sponsor.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorEntity;

public final class SponsorEntities {

    public static final QuerySponsorEntity alternative() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ContactConstants.ALTERNATIVE_LAST_NAME);
        entity.setYears(List.of(SponsorConstants.YEAR));

        return entity;
    }

    public static final QuerySponsorEntity created() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setYears(List.of(SponsorConstants.YEAR));

        return entity;
    }

    public static final QuerySponsorEntity valid() {
        final QuerySponsorEntity entity;

        entity = new QuerySponsorEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setYears(List.of(SponsorConstants.YEAR));

        return entity;
    }

    private SponsorEntities() {
        super();
    }

}
