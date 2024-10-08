
package com.bernardomg.association.settings.adapter.inbound.source;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.bernardomg.association.settings.usecase.AssociationSettingsKey;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Component
public final class AssociationPersistenceSettingsSource implements AssociationSettingsSource {

    private final SettingRepository settingsRepository;

    public AssociationPersistenceSettingsSource(final SettingRepository settingRepo) {
        super();

        settingsRepository = Objects.requireNonNull(settingRepo);
    }

    @Override
    public final Float getFeeAmount() {
        return settingsRepository.getFloat(AssociationSettingsKey.FEE_AMOUNT);
    }

}
