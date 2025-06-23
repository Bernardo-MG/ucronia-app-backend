
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MonthlyMemberBalance")
@Table(schema = "association", name = "member_monthly_balances")
public class MonthlyMemberBalanceEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 4603617058960663867L;

    @Id
    @Column(name = "month", nullable = false)
    private LocalDate         month;

    @Column(name = "total", nullable = false)
    private Long              total;

    public LocalDate getMonth() {
        return month;
    }

    public Long getTotal() {
        return total;
    }

    public void setMonth(final LocalDate month) {
        this.month = month;
    }

    public void setTotal(final Long total) {
        this.total = total;
    }

}
