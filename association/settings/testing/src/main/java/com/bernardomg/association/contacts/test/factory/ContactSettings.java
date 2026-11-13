
package com.bernardomg.association.contacts.test.factory;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.test.factory.SettingConstants;

public final class ContactSettings {

    public static final Setting email() {
        return new Setting(SettingConstants.STRING_TYPE, ContactSettingsConstants.EMAIL_CODE,
            ContactSettingsConstants.EMAIL);
    }

    public static final Setting googleMaps() {
        return new Setting(SettingConstants.STRING_TYPE, ContactSettingsConstants.GOOGLE_MAPS_CODE,
            ContactSettingsConstants.GOOGLE_MAPS);
    }

    public static final Setting instagram() {
        return new Setting(SettingConstants.STRING_TYPE, ContactSettingsConstants.INSTAGRAM_CODE,
            ContactSettingsConstants.INSTAGRAM);
    }

    public static final Setting teamUp() {
        return new Setting(SettingConstants.STRING_TYPE, ContactSettingsConstants.TEAMUP_CODE,
            ContactSettingsConstants.TEAMUP);
    }

    private ContactSettings() {
        super();
    }

}
