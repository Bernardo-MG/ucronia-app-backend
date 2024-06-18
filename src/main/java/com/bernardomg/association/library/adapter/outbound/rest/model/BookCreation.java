
package com.bernardomg.association.library.adapter.outbound.rest.model;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class BookCreation {

    @Builder.Default
    private Collection<BookCreationAuthor> authors = List.of();

    private BookCreationBookType           bookType;

    private Collection<BookCreationDonor>  donors;

    private BookCreationGameSystem         gameSystem;

    private String                         isbn;

    private String                         language;

    private BookCreationPublisher          publisher;

    private String                         title;

}
