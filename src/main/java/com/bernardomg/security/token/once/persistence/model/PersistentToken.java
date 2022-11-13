
package com.bernardomg.security.token.once.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

@Data
@Entity(name = "Token")
@Table(name = "tokens")
@TableGenerator(name = "seq_tokens_id", table = "sequences", pkColumnName = "sequence", valueColumnName = "count",
        allocationSize = 1)
public class PersistentToken implements Serializable {

    private static final long serialVersionUID = -216369933325209746L;

    @Column(name = "expiration_date", nullable = false)
    private Calendar          expirationDate;

    @Column(name = "expired", nullable = false, unique = true)
    private Boolean           expired;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_tokens_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    /**
     * Privilege name.
     */
    @Column(name = "token", nullable = false, unique = true, length = 60)
    private String            token;

}
