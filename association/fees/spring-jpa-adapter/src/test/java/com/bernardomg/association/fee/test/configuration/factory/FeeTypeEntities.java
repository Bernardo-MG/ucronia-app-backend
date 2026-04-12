
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;

public final class FeeTypeEntities {

    public static final FeeTypeEntity nameChange() {
        final FeeTypeEntity entity;

        entity = new FeeTypeEntity();
        entity.setId(1L);
        entity.setNumber(FeeTypeConstants.NUMBER);
        entity.setName(FeeTypeConstants.ALTERNATIVE_NAME);
        entity.setAmount(FeeTypeConstants.AMOUNT);

        return entity;
    }

    public static final FeeTypeEntity positive() {
        final FeeTypeEntity entity;

        entity = new FeeTypeEntity();
        entity.setId(1L);
        entity.setNumber(FeeTypeConstants.NUMBER);
        entity.setName(FeeTypeConstants.NAME);
        entity.setAmount(FeeTypeConstants.AMOUNT);

        return entity;
    }

    private FeeTypeEntities() {
        super();
    }

}
