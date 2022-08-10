
package com.bernardomg.association.balance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Payment")
@Table(name = "payments")
public class PersistentPayment implements Serializable {

    private static final long serialVersionUID = 4603617058960663867L;

    @Column(name = "day", nullable = false)
    private Integer           day;

    @Column(name = "description", length = 200)
    private String            description      = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id               = -1L;

    @Column(name = "month", nullable = false)
    private Integer           month;

    @Column(name = "quantity", nullable = false)
    private Long              quantity         = -1L;

    @Column(name = "type", nullable = false)
    private PaymentType       type;

    @Column(name = "year", nullable = false)
    private Integer           year;

}
