
package com.bernardomg.association.persistence.member.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ActiveMember")
@Table(name = "active_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveMemberEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "active")
    private Boolean           active;

    @Id
    @Column(name = "member_id", nullable = false, unique = true)
    private Long              memberId;

}
