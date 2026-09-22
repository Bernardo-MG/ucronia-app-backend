
package com.bernardomg.image.configuration;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties("image.content")
public class ImageContentProperties {

    private Set<String> allowedMediaTypes = new LinkedHashSet<>(
        Set.of("image/gif", "image/jpeg", "image/png", "image/webp"));

    private DataSize    maximumSize       = DataSize.ofMegabytes(10);

    public ImageContentProperties() {
        super();
    }

    public Set<String> getAllowedMediaTypes() {
        return allowedMediaTypes;
    }

    public DataSize getMaximumSize() {
        return maximumSize;
    }

    public void setAllowedMediaTypes(final Set<String> value) {
        allowedMediaTypes = value;
    }

    public void setMaximumSize(final DataSize value) {
        maximumSize = value;
    }

}
