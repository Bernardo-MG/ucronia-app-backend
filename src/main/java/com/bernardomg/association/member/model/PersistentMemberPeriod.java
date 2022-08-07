
package com.bernardomg.association.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "MemberPeriod")
@Table(name = "member_periods")
@Data
public class PersistentMemberPeriod {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "endMonth", nullable = false, unique = true)
    private Integer           endMonth         = -1;

    @Column(name = "endYear", nullable = false, unique = true)
    private Integer           endYear          = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id               = -1L;

    @Column(name = "member", nullable = false, unique = true)
    private Long              member           = -1L;

    @Column(name = "startMonth", nullable = false, unique = true)
    private Integer           startMonth       = -1;

    @Column(name = "startYear", nullable = false, unique = true)
    private Integer           startYear        = -1;

}
