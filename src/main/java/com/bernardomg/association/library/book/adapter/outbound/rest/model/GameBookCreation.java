
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class GameBookCreation {

    private String          isbn;

    private String          language;

    private BookChangeTitle title;

}
