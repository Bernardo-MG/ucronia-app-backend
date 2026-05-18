/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    @Override
    public String toString() {
        return "GameBookEntity [bookType=" + bookType + ", bookTypeId=" + bookTypeId + ", gameSystem=" + gameSystem
                + ", gameSystemId=" + gameSystemId + ", toString()=" + super.toString() + "]";
    }

}
