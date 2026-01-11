
package com.bernardomg.settings.test.configuration.factory;

import com.bernardomg.settings.domain.model.Setting;

public final class Settings {

    public static final Setting first() {
        return new Setting(SettingConstants.STRING_TYPE, "a", SettingConstants.STRING_VALUE);
    }

    public static final Setting floatValue() {
        return new Setting(SettingConstants.NUMBER_TYPE, SettingConstants.CODE, "10.1");
    }

    public static final Setting intValue() {
        // TODO: shouldn't all use the code constant?
        return new Setting(SettingConstants.NUMBER_TYPE, SettingConstants.CODE, SettingConstants.NUMBER_VALUE);
    }

    public static final Setting second() {
        return new Setting(SettingConstants.STRING_TYPE, "b", SettingConstants.STRING_VALUE);
    }

    public static final Setting stringValue() {
        return new Setting(SettingConstants.STRING_TYPE, SettingConstants.CODE, SettingConstants.STRING_VALUE);
    }

    public static final Setting valid() {
        return new Setting(SettingConstants.STRING_TYPE, SettingConstants.CODE, SettingConstants.STRING_VALUE);
    }

    private Settings() {
        super();
    }

}
