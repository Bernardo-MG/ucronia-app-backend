
package com.bernardomg.association.library.book.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Book")
@DiscriminatorValue("game")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookEntity extends AbstractBookEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;

    @OneToOne
    @JoinColumn(name = "book_type_id", referencedColumnName = "id")
    private BookTypeEntity    bookType;

    @Column(name = "book_type_id", insertable = false, updatable = false)
    private Long              bookTypeId;

    @OneToOne
    @JoinColumn(name = "game_system_id", referencedColumnName = "id")
    private GameSystemEntity  gameSystem;

    @Column(name = "game_system_id", insertable = false, updatable = false)
    private Long              gameSystemId;

    @Builder(setterPrefix = "with")
    public BookEntity(final Collection<AuthorEntity> authors, final LocalDate donationDate,
            final Collection<PersonEntity> donors, final Long id, final String isbn, final String language,
            final Long number, final LocalDate publishDate, final Collection<PublisherEntity> publishers,
            final String subtitle, final String supertitle, final String title, final BookTypeEntity bookType,
            final Long bookTypeId, final GameSystemEntity gameSystem, final Long gameSystemId) {
        super(authors, donationDate, donors, id, isbn, language, number, publishDate, publishers, subtitle, supertitle,
            title);
        this.bookType = bookType;
        this.bookTypeId = bookTypeId;
        this.gameSystem = gameSystem;
        this.gameSystemId = gameSystemId;
    }

}
