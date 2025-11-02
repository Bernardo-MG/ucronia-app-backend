
package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;
import com.bernardomg.association.person.test.configuration.factory.Contacts;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeMaintenanceService")
public class TestFeeMaintenanceService {

    @Mock
    private FeeRepository                feeRepository;

    @Mock
    private ContactRepository            personRepository;

    @InjectMocks
    private DefaultFeeMaintenanceService service;

    @Test
    @DisplayName("When there is a member to renew a new fee is saved")
    void testRegisterMonthFees() {

        // GIVEN
        given(personRepository.findAllToRenew()).willReturn(List.of(Contacts.membershipActive()));
        given(feeRepository.exists(ContactConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(false);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of(Fees.toCreate()));
    }

    @Test
    @DisplayName("When there is a member to renew, but the fee already exists, nothing is saved")
    void testRegisterMonthFees_Exists() {

        // GIVEN
        given(personRepository.findAllToRenew()).willReturn(List.of(Contacts.membershipActive()));
        given(feeRepository.exists(ContactConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(true);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of());
    }

    @Test
    @DisplayName("When there are no members to renew, nothing is saved")
    void testRegisterMonthFees_NotActive() {

        // GIVEN
        given(personRepository.findAllToRenew()).willReturn(List.of());

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of());
    }

}
