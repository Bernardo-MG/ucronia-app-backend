
package com.bernardomg.association.security.user.adapter.inbound.jpa.model;

import java.io.Serializable;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.security.user.data.adapter.inbound.jpa.model.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "UserMember")
@Table(schema = "security", name = "user_persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
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

}
