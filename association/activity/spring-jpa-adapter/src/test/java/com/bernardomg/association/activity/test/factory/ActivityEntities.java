
package com.bernardomg.association.activity.test.factory;

import com.bernardomg.association.activity.adapter.inbound.jpa.model.ActivityEntity;
import com.bernardomg.association.activity.test.configuration.factory.ActivityConstants;

public final class ActivityEntities {

    public static final ActivityEntity valid() {
        final ActivityEntity entity = new ActivityEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setDate(ActivityConstants.DATE);
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        return entity;
    }

    private ActivityEntities() {
        super();
    }

}
