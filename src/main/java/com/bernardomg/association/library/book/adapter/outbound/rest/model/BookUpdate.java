
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

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
public class BookUpdate {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {

        private Long number;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookType {

        private Long number;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Donor {

        private Long number;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameSystem {

        private Long number;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Publisher {

        private Long number;

    }

    @Builder.Default
    private Collection<Author>    authors = List.of();

    /**
     * TODO: optional
     */
    private BookType              bookType;

    private Collection<Donor>     donors;

    /**
     * TODO: optional
     */
    private GameSystem            gameSystem;

    private String                isbn;

    private String                language;

    private Collection<Publisher> publishers;

    private String                title;

}
