
package com.bernardomg.association.fee.model;

import java.io.Serializable;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity(name = "Fee")
@Table(name = "fees")
@Data
@TableGenerator(name = "seq_fees_id", table = "sequences", pkColumnName = "sequence", valueColumnName = "count",
        allocationSize = 1)
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
