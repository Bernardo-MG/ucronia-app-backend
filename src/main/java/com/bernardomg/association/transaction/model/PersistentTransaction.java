
package com.bernardomg.association.transaction.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Transaction")
@Table(name = "transaction")
public class PersistentTransaction implements Serializable {

    private static final long serialVersionUID = 4603617058960663867L;

    @Column(name = "pay_day", nullable = false)
    private Integer           day;

    @Column(name = "description", length = 200)
    private String            description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "pay_month", nullable = false)
    private Integer           month;

    @Column(name = "quantity", nullable = false)
    private Long              quantity;

    @Column(name = "pay_year", nullable = false)
    private Integer           year;

}
