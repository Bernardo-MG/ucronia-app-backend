
package com.bernardomg.association.fee.persistence.model;

import java.io.Serializable;
import java.time.YearMonth;

import com.bernardomg.jpa.converter.YearMonthDateAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity(name = "MemberFee")
@Table(name = "member_fees")
@Data
public class PersistentMemberFee implements Serializable {

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

    @Column(name = "member_name")
    private String            memberName;

    @Column(name = "paid")
    private Boolean           paid;

    @Column(name = "active")
    private Boolean           active;

}
