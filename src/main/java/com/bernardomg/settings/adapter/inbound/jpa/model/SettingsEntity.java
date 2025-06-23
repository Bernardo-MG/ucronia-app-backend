
package com.bernardomg.settings.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Setting")
@Table(schema = "configuration", name = "settings")
public class SettingsEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = -8109295491346275297L;

    @Column(name = "code", nullable = false)
    private String            code;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "value_type", nullable = false)
    private String            type;

    @Column(name = "config_value", nullable = false)
    private String            value;

    public String getCode() {
        return code;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}
