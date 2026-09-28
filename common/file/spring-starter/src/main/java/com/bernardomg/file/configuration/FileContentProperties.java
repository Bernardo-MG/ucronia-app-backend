
package com.bernardomg.file.configuration;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties("file.content")
public class FileContentProperties {

    private Set<String> allowedMediaTypes = new LinkedHashSet<>(Set.of("application/json", "application/pdf",
        "application/zip", "application/msword", "application/vnd.ms-excel", "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "text/csv", "text/plain"));

    private DataSize    maximumSize       = DataSize.ofMegabytes(25);

    public FileContentProperties() {
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
