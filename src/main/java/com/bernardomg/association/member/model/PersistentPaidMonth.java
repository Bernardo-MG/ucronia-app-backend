
package com.bernardomg.association.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "PaidMonth")
@Table(name = "paid_months")
@Data
public class PersistentPaidMonth {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id               = -1L;

    @Column(name = "member", nullable = false, unique = true)
    private Long              member           = -1L;

    @Column(name = "month", nullable = false, unique = true)
    private Integer           month            = -1;

    @Column(name = "year", nullable = false, unique = true)
    private Integer           year             = -1;

}
