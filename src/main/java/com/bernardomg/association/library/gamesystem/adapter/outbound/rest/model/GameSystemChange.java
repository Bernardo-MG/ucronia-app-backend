
package com.bernardomg.association.library.gamesystem.adapter.outbound.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class GameSystemChange {

    private String name;

    private Long   number;

}
