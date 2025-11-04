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
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Contact")
@Table(schema = "association", name = "contacts")
public class ContactEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long                serialVersionUID = 1328776989450853491L;

    @Column(name = "active", nullable = false)
    private Boolean                          active;

    @Column(name = "birth_date")
    private Instant                          birthDate;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ContactChannelEntity> contactChannels;

    @Column(name = "first_name", nullable = false)
    private String                           firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                             id;

    @Column(name = "identifier")
    private String                           identifier;

    @Column(name = "last_name")
    private String                           lastName;

    @Column(name = "member", nullable = false)
    private Boolean                          member;

    @Column(name = "number")
    private Long                             number;

    @Column(name = "renew_membership")
    private Boolean                          renewMembership;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final ContactEntity other = (ContactEntity) obj;
        return Objects.equals(active, other.active) && Objects.equals(birthDate, other.birthDate)
                && Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
                && Objects.equals(identifier, other.identifier) && Objects.equals(lastName, other.lastName)
                && Objects.equals(member, other.member) && Objects.equals(number, other.number)
                && Objects.equals(renewMembership, other.renewMembership);
    }

    public Boolean getActive() {
        return active;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public Collection<ContactChannelEntity> getContactChannels() {
        return contactChannels;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getMember() {
        return member;
    }

    public Long getNumber() {
        return number;
    }

    public Boolean getRenewMembership() {
        return renewMembership;
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, birthDate, firstName, id, identifier, lastName, member, number, renewMembership);
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setBirthDate(final Instant birthDate) {
        this.birthDate = birthDate;
    }

    public void setContactChannels(final Collection<ContactChannelEntity> contacts) {
        contactChannels = contacts;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setMember(final Boolean member) {
        this.member = member;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public void setRenewMembership(final Boolean renewMembership) {
        this.renewMembership = renewMembership;
    }

}
