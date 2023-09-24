
package com.bernardomg.association.funds.balance.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;

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

    @Column(name = "difference", nullable = false)
    private Float             difference;

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate         month;

    @Column(name = "total", nullable = false)
    private Float             total;

}
