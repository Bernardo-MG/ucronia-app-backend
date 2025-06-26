
package com.bernardomg.association.person.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Person")
@Table(schema = "association", name = "persons")
public class PersonEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<PersonContactMethodEntity> contacts;

    @Column(name = "active", nullable = false)
    private Boolean           active;

    @Column(name = "birth_date")
    private LocalDate         birthDate;

    @Column(name = "first_name", nullable = false)
    private String            firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "identifier")
    private String            identifier;

    @Column(name = "last_name")
    private String            lastName;

    @Column(name = "member", nullable = false)
    private Boolean           member;

    @Column(name = "number")
    private Long              number;

    @Column(name = "renew_membership")
    private Boolean           renewMembership;

    public Boolean getActive() {
        return active;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
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

    
    public Collection<PersonContactMethodEntity> getContacts() {
        return contacts;
    }

    
    public void setContacts(Collection<PersonContactMethodEntity> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "PersonEntity [active=" + active + ", birthDate=" + birthDate + ", firstName=" + firstName + ", id=" + id
                + ", identifier=" + identifier + ", lastName=" + lastName + ", member=" + member + ", number=" + number
                + ", renewMembership=" + renewMembership+ ", contacts=" + contacts + "]";
    }

}
