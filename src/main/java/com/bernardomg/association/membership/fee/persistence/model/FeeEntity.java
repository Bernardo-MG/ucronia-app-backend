
package com.bernardomg.association.membership.fee.persistence.model;

import java.io.Serializable;
import java.time.YearMonth;

import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Builder
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
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "member_id", nullable = false)
    private Long              memberId;

    @Column(name = "paid", nullable = false)
    private Boolean           paid;

}
