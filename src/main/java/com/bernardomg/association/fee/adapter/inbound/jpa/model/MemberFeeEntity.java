
package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;

import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MemberFee")
@Table(schema = "association", name = "member_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class MemberFeeEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "date", nullable = false)
    @Convert(converter = YearMonthDateAttributeConverter.class)
    private YearMonth         date;

    @Column(name = "person_first_name")
    private String            firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "person_last_name")
    private String            lastName;

    @Column(name = "paid")
    private Boolean           paid;

    @Column(name = "payment_date", nullable = false)
    private LocalDate         paymentDate;

    @Column(name = "person_id", nullable = false)
    private Long              personId;

    @Column(name = "person_number", nullable = false)
    private Long              personNumber;

    @Column(name = "transaction_index", nullable = false, unique = true)
    private Long              transactionIndex;

}
