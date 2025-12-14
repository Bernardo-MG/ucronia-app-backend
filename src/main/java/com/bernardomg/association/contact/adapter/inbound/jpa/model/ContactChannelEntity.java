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

package com.bernardomg.association.contact.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "ContactChannel")
@Table(schema = "directory", name = "contact_channels")
public class ContactChannelEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long   serialVersionUID = -3239435918896603554L;

    @Id
    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private ContactEntity       contact;

    @Id
    @ManyToOne
    @JoinColumn(name = "contact_method_id", nullable = false)
    private ContactMethodEntity contactMethod;

    @Column(name = "detail", nullable = false)
    private String              detail;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final ContactChannelEntity other)) {
            return false;
        }
        return Objects.equals(contact.getId(), other.contact.getId())
                && Objects.equals(contactMethod.getId(), other.contactMethod.getId());
    }

    public ContactEntity getContact() {
        return contact;
    }

    public ContactMethodEntity getContactMethod() {
        return contactMethod;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contact.getId(), contactMethod.getId());
    }

    public void setContact(final ContactEntity contact) {
        this.contact = contact;
    }

    public void setContactMethod(final ContactMethodEntity contactMethod) {
        this.contactMethod = contactMethod;
    }

    public void setDetail(final String code) {
        detail = code;
    }

    @Override
    public String toString() {
        return "ContactChannelEntity [contact=" + contact.getId() + ", contactMethod=" + contactMethod + ", detail="
                + detail + "]";
    }

}
