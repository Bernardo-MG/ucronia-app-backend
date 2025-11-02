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

package com.bernardomg.association.person.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

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
    private ContactEntity       person;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final PersonContactMethodEntity other = (PersonContactMethodEntity) obj;
        return Objects.equals(contact, other.contact) && Objects.equals(contactMethod, other.contactMethod)
                && Objects.equals(person, other.person);
    }

    public String getContact() {
        return contact;
    }

    public ContactMethodEntity getContactMethod() {
        return contactMethod;
    }

    public ContactEntity getPerson() {
        return person;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact, contactMethod, person);
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public void setContactMethod(final ContactMethodEntity contactMethod) {
        this.contactMethod = contactMethod;
    }

    public void setPerson(final ContactEntity person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "PersonContactMethodEntity [contactMethod=" + contactMethod + ", contact=" + contact + "]";
    }
}
