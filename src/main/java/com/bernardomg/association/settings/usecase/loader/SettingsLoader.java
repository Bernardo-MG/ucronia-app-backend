
package com.bernardomg.association.settings.usecase.loader;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.settings.usecase.service.AssociationSettingsKey;
import com.bernardomg.security.initializer.usecase.loader.Loader;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

import jakarta.transaction.Transactional;

/**
 * TODO: it is using an interface from the security library
 */
@Transactional
public final class SettingsLoader implements Loader {

    private final SettingRepository settingRepository;

    public SettingsLoader(final SettingRepository settingRepo) {
        super();

        settingRepository = settingRepo;
    }

    @Override
    public final void load() {
        final Collection<String> settingNames;
        Setting                  setting;

        settingNames = List.of(AssociationSettingsKey.EMAIL, AssociationSettingsKey.GOOGLE_MAPS,
            AssociationSettingsKey.INSTAGRAM, AssociationSettingsKey.TEAMUP);
        for (final String settingName : settingNames) {
            setting = new Setting("string", settingName, "");
            // TODO: save as a list
            settingRepository.save(setting);
        }
    }

}
