
package com.bernardomg.association.funds.transaction.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Transaction")
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistentTransaction implements Serializable {

    private static final long serialVersionUID = 4603617058960663867L;

    @Column(name = "amount", nullable = false)
    private Float             amount;

    @Column(name = "date", nullable = false)
    private LocalDate         date;

    @Column(name = "description", length = 200)
    private String            description;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

}
