
package com.bernardomg.association.transaction.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Transaction")
@Table(schema = "association", name = "transactions")
public class TransactionEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 4603617058960663867L;

    @Column(name = "amount", nullable = false)
    private Float             amount;

    @Column(name = "date", nullable = false)
    private LocalDate         date;

    @Column(name = "description", length = 200)
    private String            description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "index", nullable = false, unique = true)
    private Long              index;

    public Float getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Long getIndex() {
        return index;
    }

    public void setAmount(final Float amount) {
        this.amount = amount;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIndex(final Long index) {
        this.index = index;
    }

}
