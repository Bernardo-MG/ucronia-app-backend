
package com.bernardomg.settings.test.configuration.factory;

import com.bernardomg.association.settings.usecase.AssociationSettingsKey;
import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;

public final class SettingEntities {

    public static final SettingsEntity amount() {
        SettingsEntity entity;

        entity = new SettingsEntity();
        entity.setCode(AssociationSettingsKey.FEE_AMOUNT);
        entity.setValue("1.0");
        entity.setType(SettingConstants.NUMBER_TYPE);

        return entity;
    }

    public static final SettingsEntity valid() {
        SettingsEntity entity;

        entity = new SettingsEntity();
        entity.setCode(SettingConstants.CODE);
        entity.setValue(SettingConstants.STRING_VALUE);
        entity.setType(SettingConstants.NUMBER_TYPE);

        return entity;
    }

    private SettingEntities() {
        super();
    }

}
