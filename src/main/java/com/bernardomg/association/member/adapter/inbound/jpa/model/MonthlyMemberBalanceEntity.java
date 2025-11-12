/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "MonthlyMemberBalance")
@Table(schema = "directory", name = "member_monthly_balances")
public class MonthlyMemberBalanceEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 4603617058960663867L;

    @Id
    @Column(name = "month", nullable = false)
    private Instant           month;

    @Column(name = "total", nullable = false)
    private Long              total;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final MonthlyMemberBalanceEntity other = (MonthlyMemberBalanceEntity) obj;
        return Objects.equals(month, other.month) && Objects.equals(total, other.total);
    }

    public Instant getMonth() {
        return month;
    }

    public Long getTotal() {
        return total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, total);
    }

    public void setMonth(final Instant month) {
        this.month = month;
    }

    public void setTotal(final Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MonthlyMemberBalanceEntity [month=" + month + ", total=" + total + "]";
    }

}
