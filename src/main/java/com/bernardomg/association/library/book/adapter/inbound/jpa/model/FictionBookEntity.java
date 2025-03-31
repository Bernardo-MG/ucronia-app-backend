
package com.bernardomg.association.library.book.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "FictionBook")
@DiscriminatorValue("fiction")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FictionBookEntity extends RootBookEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = -6518446575924375762L;

    @Builder(setterPrefix = "with")
    public FictionBookEntity(final Collection<AuthorEntity> authors, final LocalDate donationDate,
            final Collection<PersonEntity> donors, final Long id, final String isbn, final String language,
            final Long number, final LocalDate publishDate, final Collection<PublisherEntity> publishers,
            final String subtitle, final String supertitle, final String title) {
        super(authors, donationDate, donors, id, isbn, language, number, publishDate, publishers, subtitle, supertitle,
            title);
    }

}
