
package com.bernardomg.association.member.domain.model;

import com.bernardomg.association.person.domain.model.PersonName;

public record Member(Long number, String identifier, PersonName name, Boolean active, String phone) {

}
