
package com.bernardomg.settings.test.config.factory;

import com.bernardomg.association.settings.usecase.AssociationSettingsKey;
import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;

public final class SettingEntities {

    public static final SettingsEntity amount() {
        return SettingsEntity.builder()
            .withCode(AssociationSettingsKey.FEE_AMOUNT)
            .withValue("1.0")
            .withType(SettingConstants.NUMBER_TYPE)
            .build();
    }

    public static final SettingsEntity valid() {
        return SettingsEntity.builder()
            .withCode(SettingConstants.CODE)
            .withValue(SettingConstants.STRING_VALUE)
            .withType(SettingConstants.NUMBER_TYPE)
            .build();
    }

    private SettingEntities() {
        super();
    }

}
