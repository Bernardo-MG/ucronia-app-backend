
package com.bernardomg.association.library.adapter.outbound.rest.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class BookCreation {

    private Collection<String> authors;

    private String             bookType;

    private String             gameSystem;

    private String             isbn;

    private String             language;

    private String             title;

}
