
package com.bernardomg.association.balance.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MonthlyBalance")
@Table(name = "monthly_balances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistentMonthlyBalance implements Serializable {

    private static final long serialVersionUID = 4603617058960663867L;

    @Column(name = "cumulative", nullable = false)
    private Float             cumulative;

    @Id
    @Column(name = "date", nullable = false)
    private Calendar          date;

    @Column(name = "total", nullable = false)
    private Float             total;

}
