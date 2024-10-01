
package com.bernardomg.association.member.domain.model;

import com.bernardomg.association.person.domain.model.PersonName;

public record PublicMember(Long number, PersonName name, Boolean active) {

}
