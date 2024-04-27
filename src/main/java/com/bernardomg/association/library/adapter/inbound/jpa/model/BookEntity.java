
package com.bernardomg.association.library.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Book")
@Table(schema = "inventory", name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BookEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long        serialVersionUID = 1328776989450853491L;

    @OneToMany
    @JoinTable(schema = "inventory", name = "book_authors",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id", referencedColumnName = "id") })
    private Collection<AuthorEntity> authors;

    @OneToOne
    @JoinTable(schema = "inventory", name = "book_book_types",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "book_type_id", referencedColumnName = "id") })
    private BookTypeEntity           bookType;

    @OneToOne
    @JoinTable(schema = "inventory", name = "book_game_systems",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "game_system_id", referencedColumnName = "id") })
    private GameSystemEntity         gameSystem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                     id;

    @Column(name = "isbn", nullable = false)
    private String                   isbn;

    @Column(name = "language", nullable = false)
    private String                   language;

    @Column(name = "number", nullable = false, unique = true)
    private Long                     number;

    @OneToOne
    @JoinTable(schema = "inventory", name = "book_publishers",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "publisher_id", referencedColumnName = "id") })
    private PublisherEntity          publisher;

    @Column(name = "title", nullable = false)
    private String                   title;

}
