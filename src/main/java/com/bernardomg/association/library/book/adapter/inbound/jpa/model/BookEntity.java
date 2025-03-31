
package com.bernardomg.association.library.book.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Book")
@Table(schema = "inventory", name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity implements Serializable {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long           serialVersionUID = 5800151370938640858L;

    @OneToMany
    @JoinTable(schema = "inventory", name = "book_authors",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id", referencedColumnName = "id") })
    private Collection<AuthorEntity>    authors;

    @Column(name = "donation_date")
    private LocalDate                   donationDate;

    @OneToMany
    @JoinTable(schema = "inventory", name = "book_donors",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "donor_id", referencedColumnName = "id") })
    private Collection<PersonEntity>    donors;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                        id;

    @Column(name = "isbn", nullable = false)
    private String                      isbn;

    @Column(name = "language", nullable = false)
    private String                      language;

    @Column(name = "number", nullable = false, unique = true)
    private Long                        number;

    @Column(name = "publish_date")
    private LocalDate                   publishDate;

    @OneToMany
    @JoinTable(schema = "inventory", name = "book_publishers",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "publisher_id", referencedColumnName = "id") })
    private Collection<PublisherEntity> publishers;

    @Column(name = "subtitle", nullable = false)
    private String                      subtitle;

    @Column(name = "supertitle", nullable = false)
    private String                      supertitle;

    @Column(name = "title", nullable = false)
    private String                      title;

}
