
package com.bernardomg.association.member.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Member")
@Table(schema = "directory", name = "members")
@SecondaryTable(schema = "directory", name = "contacts",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "contact_id"))
public class MemberEntity implements Serializable {

    /**
     *
     */
    @Transient
    private static final long serialVersionUID = 8139806507534262996L;

    @Column(name = "active", table = "members", nullable = false)
    private Boolean           active;

    @Column(name = "first_name", table = "contacts", nullable = false)
    private String            firstName;

    @Id
    @Column(name = "contact_id", table = "members", nullable = false, unique = true)
    private Long              id;

    @Column(name = "last_name", table = "contacts")
    private String            lastName;

    @Column(name = "number", table = "contacts")
    private Long              number;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final MemberEntity other = (MemberEntity) obj;
        return Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
                && Objects.equals(lastName, other.lastName) && Objects.equals(number, other.number);
    }

    public Boolean getActive() {
        return active;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, id, lastName, number);
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "MemberEntity [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", number=" + number
                + "]";
    }

}
