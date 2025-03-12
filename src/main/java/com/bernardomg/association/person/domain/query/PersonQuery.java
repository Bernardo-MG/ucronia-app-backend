
package com.bernardomg.association.person.domain.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class PersonQuery {

    @Builder.Default
    private PersonStatus status = PersonStatus.ALL;

}
