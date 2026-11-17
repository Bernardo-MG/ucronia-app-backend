
package com.bernardomg.association.fee.test.adapter.inbound.jpa.model.unit;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntityMapper;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.test.configuration.factory.FeeEntities;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;

class TestFeeEntityMapper {

    @Test
    void preservesAuditOnUnpaidFees() {
        final Instant       createdAt = Instant.parse("2025-01-02T10:00:00Z");
        final AuditMetadata audit     = Mockito.mock(AuditMetadata.class);
        final FeeEntity     entity    = FeeEntities.currentMonth();

        Mockito.when(audit.getCreatedAt())
            .thenReturn(createdAt);
        entity.setAudit(audit);
        entity.setPaid(false);

        final Fee fee = FeeEntityMapper.toDomain(entity);

        Assertions.assertThat(fee.paid())
            .isFalse();
        Assertions.assertThat(fee.audit()
            .createdAt())
            .isEqualTo(createdAt);
    }

}
