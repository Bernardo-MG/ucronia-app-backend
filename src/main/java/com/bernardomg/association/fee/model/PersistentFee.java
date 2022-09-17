
package com.bernardomg.association.fee.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "Fee")
@Table(name = "fees")
@Data
public class PersistentFee implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "member", nullable = false, unique = true)
    private Long              member;

    @Column(name = "paid", nullable = false, unique = true)
    private Boolean           paid;

    @Column(name = "pay_date", nullable = false)
    private Calendar          payDate;

}
