
package com.bernardomg.settings.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.settings.domain.model.Setting;

public interface SettingRepository {

    public Collection<Setting> findAll();

    public Optional<Setting> findOne(final String code);

    public Float getFloat(final String code);

    public Setting save(final Setting setting);

}
