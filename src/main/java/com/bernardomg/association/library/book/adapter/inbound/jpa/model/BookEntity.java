
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

@Entity(name = "Book")
@Table(schema = "inventory", name = "books")
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

    public Collection<AuthorEntity> getAuthors() {
        return authors;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public Collection<PersonEntity> getDonors() {
        return donors;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }

    public Long getNumber() {
        return number;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public Collection<PublisherEntity> getPublishers() {
        return publishers;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getSupertitle() {
        return supertitle;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthors(final Collection<AuthorEntity> authors) {
        this.authors = authors;
    }

    public void setDonationDate(final LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public void setDonors(final Collection<PersonEntity> donors) {
        this.donors = donors;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public void setPublishDate(final LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public void setPublishers(final Collection<PublisherEntity> publishers) {
        this.publishers = publishers;
    }

    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
    }

    public void setSupertitle(final String supertitle) {
        this.supertitle = supertitle;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

}
