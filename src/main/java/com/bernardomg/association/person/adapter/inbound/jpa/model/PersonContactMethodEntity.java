package com.bernardomg.association.person.adapter.inbound.jpa.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(schema = "association", name = "person_contact_methods")
public class PersonContactMethodEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -3239435918896603554L;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @Id
    @ManyToOne
    @JoinColumn(name = "contact_method_id", nullable = false)
    private ContactMethodEntity contactMethod;

    @Column(name = "contact", nullable = false)
    private String contact;

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public ContactMethodEntity getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(ContactMethodEntity contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
