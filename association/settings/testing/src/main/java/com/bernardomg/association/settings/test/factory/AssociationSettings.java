
package com.bernardomg.association.settings.test.factory;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.test.factory.SettingConstants;

public final class AssociationSettings {

    public static final Setting email() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsConstants.EMAIL_CODE,
            AssociationSettingsConstants.EMAIL);
    }

    public static final Setting googleMaps() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsConstants.GOOGLE_MAPS_CODE,
            AssociationSettingsConstants.GOOGLE_MAPS);
    }

    public static final Setting instagram() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsConstants.INSTAGRAM_CODE,
            AssociationSettingsConstants.INSTAGRAM);
    }

    public static final Setting teamUp() {
        return new Setting(SettingConstants.STRING_TYPE, AssociationSettingsConstants.TEAMUP_CODE,
            AssociationSettingsConstants.TEAMUP);
    }

    private AssociationSettings() {
        super();
    }

}
