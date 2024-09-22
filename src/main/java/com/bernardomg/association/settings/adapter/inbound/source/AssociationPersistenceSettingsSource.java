
package com.bernardomg.association.settings.adapter.inbound.source;

import java.util.Objects;

import com.bernardomg.association.settings.usecase.AssociationSettingsKey;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.settings.domain.repository.SettingRepository;

public final class AssociationPersistenceSettingsSource implements AssociationSettingsSource {

    private final SettingRepository settingsRepository;

    public AssociationPersistenceSettingsSource(final SettingRepository configurationRepo) {
        super();

        settingsRepository = Objects.requireNonNull(configurationRepo);
    }

    @Override
    public final Float getFeeAmount() {
        return settingsRepository.getFloat(AssociationSettingsKey.FEE_AMOUNT);
    }

}
