
package com.bernardomg.association.settings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.settings.adapter.inbound.source.AssociationPersistenceSettingsSource;
import com.bernardomg.association.settings.security.register.AssociationSettingsPermissionRegister;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.security.permission.initializer.usecase.PermissionRegister;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Configuration
public class AssociationSettingsConfig {

    @Bean("associationSettingsPermissionRegister")
    public PermissionRegister getAssociationSettingsPermissionRegister() {
        return new AssociationSettingsPermissionRegister();
    }

    @Bean("associationSettingsSource")
    public AssociationSettingsSource getAssociationSettingsSource(final SettingRepository settingRepo) {
        return new AssociationPersistenceSettingsSource(settingRepo);
    }

}
