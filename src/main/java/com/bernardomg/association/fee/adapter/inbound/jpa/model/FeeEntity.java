
package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.YearMonth;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Fee")
@Table(name = "fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class FeeEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "date", nullable = false)
    @Convert(converter = YearMonthDateAttributeConverter.class)
    private YearMonth         date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity      person;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long              personId;

}
