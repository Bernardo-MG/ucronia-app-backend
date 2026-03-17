
package com.bernardomg.association.settings.test.factory;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.test.factory.SettingConstants;

public final class AssociationSettings {

    public static final Setting email() {
        return new Setting(SettingConstants.STRING_TYPE, SettingConstants.EMAIL, SettingConstants.EMAIL);
    }

    public static final Setting googleMaps() {
        return new Setting(SettingConstants.STRING_TYPE, SettingConstants.GOOGLE_MAPS, SettingConstants.GOOGLE_MAPS);
    }

    public static final Setting instagram() {
        return new Setting(SettingConstants.STRING_TYPE, SettingConstants.INSTAGRAM, SettingConstants.INSTAGRAM);
    }

    public static final Setting teamUp() {
        return new Setting(SettingConstants.STRING_TYPE, SettingConstants.TEAMUP, SettingConstants.TEAMUP);
    }

    private AssociationSettings() {
        super();
    }

}
