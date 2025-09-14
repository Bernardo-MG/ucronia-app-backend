
package com.bernardomg.association.security.user.adapter.inbound.jpa.model;

import java.io.Serializable;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.security.user.adapter.inbound.jpa.model.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "UserMember")
@Table(schema = "security", name = "user_persons")
public class UserPersonEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -3540074544521251838L;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity      person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity        user;

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long              userId;

    public PersonEntity getPerson() {
        return person;
    }

    public UserEntity getUser() {
        return user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setPerson(final PersonEntity person) {
        this.person = person;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPersonEntity [person=" + person + ", user=" + user + ", userId=" + userId + "]";
    }

}
