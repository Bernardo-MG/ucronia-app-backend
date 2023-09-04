
package com.bernardomg.configuration.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Configuration")
@Table(name = "configurations")
@TableGenerator(name = "seq_configurations_id", table = "sequences", pkColumnName = "sequence",
        valueColumnName = "count", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistentConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_configurations_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long   id;

    @Column(name = "property", nullable = false)
    private String key;

    @Column(name = "data", nullable = false)
    private String value;

}
