
package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.model.MyFee;
import com.bernardomg.association.fee.domain.repository.FeeProfileRepository;
import com.bernardomg.association.fee.domain.repository.MyFeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeProfiles;
import com.bernardomg.association.fee.test.configuration.factory.MyFees;
import com.bernardomg.association.fee.test.configuration.factory.UserConstants;
import com.bernardomg.association.fee.usecase.service.DefaultMyFeesService;
import com.bernardomg.association.fee.usecase.session.UserSessionProvider;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMyFeesService - getAllForUserInSession")
class TestDefaultMyFeesServiceGetAllForUserInSession {

    @Mock
    private FeeProfileRepository feeProfileRepository;

    @Mock
    private MyFeeRepository      myFeeRepository;

    @InjectMocks
    private DefaultMyFeesService myFeesService;

    @Mock
    private UserSessionProvider  userSessionProvider;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetAllForUserInSession() {
        final Page<MyFee> fees;
        final Page<MyFee> existing;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        given(userSessionProvider.getUsername()).willReturn(UserConstants.USERNAME);

        given(feeProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(FeeProfiles.valid()));

        existing = new Page<>(List.of(MyFees.paid()), 0, 0, 0, 0, 0, false, false, sorting);
        given(myFeeRepository.findAllForMember(MemberConstants.NUMBER, pagination, sorting)).willReturn(existing);

        // WHEN
        fees = myFeesService.getAllForUserInSession(pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("When there is data, then it is returned")
            .isSameAs(existing);
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetAllForUserInSession_NoData() {
        final Page<MyFee> fees;
        final Page<MyFee> existing;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        given(userSessionProvider.getUsername()).willReturn(UserConstants.USERNAME);

        given(feeProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(FeeProfiles.valid()));

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(myFeeRepository.findAllForMember(MemberConstants.NUMBER, pagination, sorting)).willReturn(existing);

        // WHEN
        fees = myFeesService.getAllForUserInSession(pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When the user has no member, then nothing is returned")
    void testGetAllForUserInSession_NoMember() {
        final Page<MyFee> fees;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        given(userSessionProvider.getUsername()).willReturn(UserConstants.USERNAME);

        given(feeProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        fees = myFeesService.getAllForUserInSession(pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("fees")
            .isEmpty();
    }

}
