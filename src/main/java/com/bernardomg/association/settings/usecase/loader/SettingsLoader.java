
package com.bernardomg.association.settings.usecase.loader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.settings.usecase.constant.AssociationSettingsKey;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

import jakarta.transaction.Transactional;

@Transactional
public final class SettingsLoader implements Runnable {

    private final SettingRepository settingRepository;

    public SettingsLoader(final SettingRepository settingRepo) {
        super();

        settingRepository = settingRepo;
    }

    @Override
    public final void run() {
        final Collection<String> codes;
        Collection<Setting>      settings;
        Setting                  setting;

        codes = List.of(AssociationSettingsKey.EMAIL, AssociationSettingsKey.GOOGLE_MAPS,
            AssociationSettingsKey.INSTAGRAM, AssociationSettingsKey.TEAMUP);
        for (final String code : codes) {
            // TODO: save as a list
            settings = new ArrayList<>();
            if (!settingRepository.exists(code)) {
                setting = new Setting("string", code, "");
                settings.add(setting);
            }
            settingRepository.saveAll(settings);
        }
    }

}
