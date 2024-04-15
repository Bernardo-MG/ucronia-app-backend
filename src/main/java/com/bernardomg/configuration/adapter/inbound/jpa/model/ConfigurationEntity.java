
package com.bernardomg.configuration.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Configuration")
@Table(name = "configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class ConfigurationEntity implements Serializable {

    private static final long serialVersionUID = -8109295491346275297L;

    @Column(name = "code", nullable = false)
    private String            code;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "restricted", nullable = false)
    private boolean           restricted;

    @Column(name = "value_type", nullable = false)
    private String            type;

    @Column(name = "config_value", nullable = false)
    private String            value;

}
