
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
    private static final long   serialVersionUID = -3239435918896603554L;

    @Column(name = "contact", nullable = false)
    private String              contact;

    @Id
    @ManyToOne
    @JoinColumn(name = "contact_method_id", nullable = false)
    private ContactMethodEntity contactMethod;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity        person;

    public String getContact() {
        return contact;
    }

    public ContactMethodEntity getContactMethod() {
        return contactMethod;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public void setContactMethod(final ContactMethodEntity contactMethod) {
        this.contactMethod = contactMethod;
    }

    public void setPerson(final PersonEntity person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonContactMethodEntity [contactMethod=" + contactMethod + ", contact=" + contact + "]";
    }
}
