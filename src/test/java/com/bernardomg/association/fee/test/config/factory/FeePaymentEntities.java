
package com.bernardomg.association.fee.test.config.factory;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeePaymentEntity;

public final class FeePaymentEntities {

    public static final FeePaymentEntity forFee(final Long fee, final Long transaction) {
        return FeePaymentEntity.builder()
            .withFeeId(fee)
            .withTransactionId(transaction)
            .build();
    }

    private FeePaymentEntities() {
        super();
    }

}
