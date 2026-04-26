
package com.bernardomg.association.library.lending.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Borrower")
@Table(schema = "directory", name = "members")
@SecondaryTable(schema = "directory", name = "profiles",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class BorrowerEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 8139806507534262996L;

    @Column(name = "first_name", table = "profiles", nullable = false)
    private String            firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "identifier", table = "profiles")
    private String            identifier;

    @Column(name = "last_name", table = "profiles")
    private String            lastName;

    @Column(name = "number", table = "profiles")
    private Long              number;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final BorrowerEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
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

    public Long getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public void setNumber(final Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "MemberProfileEntity [id=" + id + ", number=" + number + ", firstName=" + firstName + ", lastName="
                + lastName + ", identifier=" + identifier + "]";
    }

}
