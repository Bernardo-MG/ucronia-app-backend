
package com.bernardomg.settings.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.settings.domain.model.Setting;

public interface SettingRepository {

    public Collection<Setting> findAll();

    public Optional<Setting> findOne(final String key);

    public Float getFloat(final String key);

    public Setting save(final Setting configuration);

}
