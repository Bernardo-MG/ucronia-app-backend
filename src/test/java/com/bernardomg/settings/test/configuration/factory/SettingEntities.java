
package com.bernardomg.settings.test.configuration.factory;

import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;

public final class SettingEntities {

    public static final SettingsEntity intValue() {
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
