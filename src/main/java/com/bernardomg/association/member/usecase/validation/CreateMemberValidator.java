
package com.bernardomg.association.member.usecase.validation;

import java.util.List;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateMemberValidator extends AbstractFieldRuleValidator<Member> {

    public CreateMemberValidator() {
        super(List.of(new MemberNameNotEmptyRule()));
    }

}
