
package com.bernardomg.association.crud.transaction.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

@Data
@Entity(name = "Transaction")
@Table(name = "transactions")
@TableGenerator(name = "seq_transactions_id", table = "sequences", pkColumnName = "sequence", valueColumnName = "count",
        allocationSize = 1)
public class PersistentTransaction implements Serializable {

    private static final long serialVersionUID = 4603617058960663867L;

    @Column(name = "amount", nullable = false)
    private Float             amount;

    @Column(name = "date", nullable = false)
    private Calendar          date;

    @Column(name = "description", length = 200)
    private String            description;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_transactions_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

}
