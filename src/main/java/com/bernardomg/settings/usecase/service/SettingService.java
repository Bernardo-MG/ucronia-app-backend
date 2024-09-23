
package com.bernardomg.settings.usecase.service;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.settings.domain.model.Setting;

public interface SettingService {

    public Collection<Setting> getAll();

    public Optional<Setting> getOne(final String code);

    public Setting update(final String code, final String value);

}
