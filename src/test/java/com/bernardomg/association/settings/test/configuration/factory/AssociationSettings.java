
package com.bernardomg.association.settings.test.configuration.factory;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.test.configuration.factory.SettingConstants;

public final class AssociationSettings {

    public static final Setting googleMaps() {
        return new Setting(SettingConstants.STRING_TYPE, "a", AssociationSettingsConstants.GOOGLE_MAPS);
    }

    public static final Setting teamUp() {
        return new Setting(SettingConstants.STRING_TYPE, "a", AssociationSettingsConstants.TEAMUP);
    }

    private AssociationSettings() {
        super();
    }

}
