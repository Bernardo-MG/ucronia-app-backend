
package com.bernardomg.association.auth.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "UserMember")
@Table(name = "user_members")
@IdClass(UserMemberKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMemberEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -3540074544521251838L;

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long              userId;

    @Id
    @Column(name = "member_id", nullable = false, unique = true)
    private Long              memberId;

}
