
package com.bernardomg.association.crud.fee.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "Fee")
@Table(name = "fees")
@Data
@TableGenerator(name = "seq_fees_id", table = "sequences", pkColumnName = "seq_name", valueColumnName = "seq_count",
        initialValue = 10, allocationSize = 1)
public class PersistentFee implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "date", nullable = false)
    private Calendar          date;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_fees_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "member_id", nullable = false, unique = true)
    private Long              memberId;

    @Column(name = "paid", nullable = false, unique = true)
    private Boolean           paid;

}
