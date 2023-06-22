
package com.bernardomg.association.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedMemberQuery implements MemberQuery {

    private Boolean active;

    private Long    id;

    private String  identifier;

    private String  name;

    private String  phone;

    private String  surname;

}
