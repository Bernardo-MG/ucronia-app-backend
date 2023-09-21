
package com.bernardomg.association.membership.calendar.model.request;

import com.bernardomg.association.membership.member.model.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DtoFeeCalendarQueryRequest {

    private MemberStatus active;

}
