
package com.bernardomg.association.settings.test.configuration.factory;

import com.bernardomg.association.settings.usecase.constant.AssociationSettingsKey;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.test.configuration.factory.SettingConstants;

public final class AssociationSettings {

    public static final Setting email() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsKey.EMAIL,
            AssociationSettingsConstants.EMAIL);
    }

    public static final Setting googleMaps() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsKey.GOOGLE_MAPS,
            AssociationSettingsConstants.GOOGLE_MAPS);
    }

    public static final Setting instagram() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsKey.INSTAGRAM,
            AssociationSettingsConstants.INSTAGRAM);
    }

    public static final Setting teamUp() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsKey.TEAMUP,
            AssociationSettingsConstants.TEAMUP);
    }

    private AssociationSettings() {
        super();
    }

}
