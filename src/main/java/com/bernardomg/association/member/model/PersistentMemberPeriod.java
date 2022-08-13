
package com.bernardomg.association.member.model;

import java.io.Serializable;

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
public class PersistentMemberPeriod implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @Column(name = "end_month", nullable = false, unique = true)
    private Integer           endMonth         = -1;

    @Column(name = "end_year", nullable = false, unique = true)
    private Integer           endYear          = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id               = -1L;

    @Column(name = "member", nullable = false, unique = true)
    private Long              member           = -1L;

    @Column(name = "start_month", nullable = false, unique = true)
    private Integer           startMonth       = -1;

    @Column(name = "start_year", nullable = false, unique = true)
    private Integer           startYear        = -1;

}
