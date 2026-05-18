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

package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "FeeMember")
@Table(schema = "directory", name = "profiles")
@SecondaryTable(schema = "directory", name = "members",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id"))
public class FeeMemberEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "active", table = "members", nullable = false)
    private Boolean           active;

    @OneToOne
    @JoinColumn(name = "fee_type_id", table = "members", referencedColumnName = "id")
    private FeeTypeEntity     feeType;

    @Column(name = "first_name", table = "profiles", nullable = false)
    private String            firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "last_name", table = "profiles")
    private String            lastName;

    @Column(name = "number", table = "profiles")
    private Long              number;

    @Column(name = "renew_membership", table = "members", nullable = false)
    private Boolean           renew;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final FeeMemberEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public Boolean getActive() {
        return active;
    }

    public FeeTypeEntity getFeeType() {
        return feeType;
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

    public Boolean getRenew() {
        return renew;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public void setFeeType(final FeeTypeEntity feeType) {
        this.feeType = feeType;
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

    public void setRenew(final Boolean renew) {
        this.renew = renew;
    }

    @Override
    public String toString() {
        return "ProfileEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", number=" + number
                + ", renew=" + renew + "]";
    }

}
