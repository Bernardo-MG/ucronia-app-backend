
package com.bernardomg.association.fee.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import jakarta.persistence.Column;
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
    private Calendar          date;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "memberId", nullable = false)
    private Long              memberId;

    @Column(name = "name")
    private String            name;

    @Column(name = "paid")
    private Boolean           paid;

}
