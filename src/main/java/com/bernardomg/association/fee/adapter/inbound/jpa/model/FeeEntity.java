
package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
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

    @Column(name = "paid")
    private Boolean           paid;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity      person;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long              personId;

    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private TransactionEntity transaction;

    public Instant getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public Boolean getPaid() {
        return paid;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public Long getPersonId() {
        return personId;
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

    public void setPaid(final Boolean paid) {
        this.paid = paid;
    }

    public void setPerson(final PersonEntity person) {
        this.person = person;
    }

    public void setPersonId(final Long personId) {
        this.personId = personId;
    }

    public void setTransaction(final TransactionEntity transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "FeeEntity [date=" + date + ", id=" + id + ", paid=" + paid + ", person=" + person + ", personId="
                + personId + ", transaction=" + transaction + "]";
    }

}
