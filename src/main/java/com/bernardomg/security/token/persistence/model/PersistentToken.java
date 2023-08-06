
package com.bernardomg.security.token.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Data;

@Data
@Entity(name = "Token")
@Table(name = "tokens")
@TableGenerator(name = "seq_tokens_id", table = "sequences", pkColumnName = "sequence", valueColumnName = "count",
        allocationSize = 1)
public class PersistentToken implements Serializable {

    private static final long serialVersionUID = -216369933325209746L;

    @Column(name = "consumed", nullable = false)
    private boolean           consumed;

    @Column(name = "creation_date", nullable = false)
    private Calendar          creationDate;

    @Column(name = "expiration_date", nullable = false)
    private Calendar          expirationDate;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_tokens_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "revoked", nullable = false)
    private boolean           revoked;

    @Column(name = "scope", nullable = false, unique = true, length = 20)
    private String            scope;

    @Column(name = "token", nullable = false, unique = true, length = 300)
    private String            token;

    @Column(name = "user_id", nullable = false)
    private Long              userId;

}
