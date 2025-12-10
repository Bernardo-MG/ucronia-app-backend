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

package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Fee")
@Table(schema = "association", name = "fees")
public class FeeEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    // TODO: should be called month
    @Column(name = "date", nullable = false)
    private Instant           date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @OneToOne(optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private MemberEntity      member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long              memberId;

    @Column(name = "paid")
    private Boolean           paid;

    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private TransactionEntity transaction;

    public Instant getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public MemberEntity getMember() {
        return member;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Boolean getPaid() {
        return paid;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setDate(final Instant date) {
        this.date = date;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setMember(final MemberEntity member) {
        this.member = member;
    }

    public void setMemberId(final Long memberId) {
        this.memberId = memberId;
    }

    public void setPaid(final Boolean paid) {
        this.paid = paid;
    }

    public void setTransaction(final TransactionEntity transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "FeeEntity [date=" + date + ", id=" + id + ", paid=" + paid + ", member=" + member + ", memberId="
                + memberId + ", transaction=" + transaction + "]";
    }

}
