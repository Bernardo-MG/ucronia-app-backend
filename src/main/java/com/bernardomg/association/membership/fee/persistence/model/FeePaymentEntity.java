
package com.bernardomg.association.membership.fee.persistence.model;

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

@Entity(name = "FeePayment")
@Table(name = "fee_payments")
@IdClass(FeePaymentKey.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeePaymentEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -3540074544521251838L;

    @Id
    @Column(name = "fee_id", nullable = false, unique = true)
    private Long              feeId;

    @Id
    @Column(name = "transaction_id", nullable = false, unique = true)
    private Long              transactionId;

}
