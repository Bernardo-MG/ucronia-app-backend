
package com.bernardomg.settings.test.config.factory;

import com.bernardomg.association.settings.usecase.AssociationSettingsKey;
import com.bernardomg.settings.domain.model.Setting;

public final class Settings {

    public static final Setting amount() {
        return Setting.builder()
            .withCode(AssociationSettingsKey.FEE_AMOUNT)
            .withValue("1.0")
            .withType(SettingConstants.NUMBER_TYPE)
            .build();
    }

    public static final Setting first() {
        return Setting.builder()
            .withCode("a")
            .withValue(SettingConstants.STRING_VALUE)
            .withType(SettingConstants.STRING_TYPE)
            .build();
    }

    public static final Setting floatValue() {
        return Setting.builder()
            .withCode(SettingConstants.CODE)
            .withValue("10.1")
            .withType(SettingConstants.NUMBER_TYPE)
            .build();
    }

    public static final Setting intValue() {
        return Setting.builder()
            .withCode(SettingConstants.CODE)
            .withValue(SettingConstants.NUMBER_VALUE)
            .withType(SettingConstants.NUMBER_TYPE)
            .build();
    }

    public static final Setting second() {
        return Setting.builder()
            .withCode("b")
            .withValue(SettingConstants.STRING_VALUE)
            .withType(SettingConstants.STRING_TYPE)
            .build();
    }

    public static final Setting stringValue() {
        return Setting.builder()
            .withCode(SettingConstants.CODE)
            .withValue(SettingConstants.STRING_VALUE)
            .withType(SettingConstants.STRING_TYPE)
            .build();
    }

    public static final Setting valid() {
        return Setting.builder()
            .withCode(SettingConstants.CODE)
            .withValue(SettingConstants.STRING_VALUE)
            .withType(SettingConstants.STRING_TYPE)
            .build();
    }

    private Settings() {
        super();
    }

}
