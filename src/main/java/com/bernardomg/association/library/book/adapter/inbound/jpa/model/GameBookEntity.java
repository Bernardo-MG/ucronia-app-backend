
package com.bernardomg.association.library.book.adapter.inbound.jpa.model;

import java.io.Serializable;

import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity(name = "GameBook")
@DiscriminatorValue("game")
public class GameBookEntity extends RootBookEntity implements Serializable {

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

    public BookTypeEntity getBookType() {
        return bookType;
    }

    public Long getBookTypeId() {
        return bookTypeId;
    }

    public GameSystemEntity getGameSystem() {
        return gameSystem;
    }

    public Long getGameSystemId() {
        return gameSystemId;
    }

    public void setBookType(final BookTypeEntity bookType) {
        this.bookType = bookType;
    }

    public void setBookTypeId(final Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public void setGameSystem(final GameSystemEntity gameSystem) {
        this.gameSystem = gameSystem;
    }

    public void setGameSystemId(final Long gameSystemId) {
        this.gameSystemId = gameSystemId;
    }

}
