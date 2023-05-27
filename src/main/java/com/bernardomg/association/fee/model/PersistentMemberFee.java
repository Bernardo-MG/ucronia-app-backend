
package com.bernardomg.association.fee.model;

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

    @Column(name = "memberId", nullable = false, unique = true)
    private Long              memberId;

    @Column(name = "name", nullable = false, unique = true)
    private String            name;

    @Column(name = "paid", nullable = false, unique = true)
    private Boolean           paid;

    @Column(name = "surname", nullable = false, unique = true)
    private String            surname;

}
