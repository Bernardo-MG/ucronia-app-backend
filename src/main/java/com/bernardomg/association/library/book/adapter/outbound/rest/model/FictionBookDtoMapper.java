
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.AuthorRefDto;
import com.bernardomg.ucronia.openapi.model.BookCreationDto;
import com.bernardomg.ucronia.openapi.model.BookTitleDto;
import com.bernardomg.ucronia.openapi.model.DonationDto;
import com.bernardomg.ucronia.openapi.model.DonorRefDto;
import com.bernardomg.ucronia.openapi.model.FictionBookDto;
import com.bernardomg.ucronia.openapi.model.FictionBookPageResponseDto;
import com.bernardomg.ucronia.openapi.model.FictionBookResponseDto;
import com.bernardomg.ucronia.openapi.model.FictionBookUpdateDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.PublisherRefDto;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class FictionBookDtoMapper {

    public static final FictionBook toDomain(final BookCreationDto bookCreationDto, final long number) {
        final Title  title;
        final String supertitle;
        final String subtitle;

        if (bookCreationDto.getTitle()
            .getSupertitle() == null) {
            supertitle = "";
        } else {
            supertitle = bookCreationDto.getTitle()
                .getSupertitle();
        }
        if (bookCreationDto.getTitle()
            .getSubtitle() == null) {
            subtitle = "";
        } else {
            subtitle = bookCreationDto.getTitle()
                .getSubtitle();
        }

        title = new Title(supertitle, bookCreationDto.getTitle()
            .getTitle(), subtitle);
        return new FictionBook(number, title, bookCreationDto.getIsbn(), bookCreationDto.getLanguage(), null, false,
            List.of(), List.of(), List.of(), Optional.empty());
    }

    public static final FictionBook toDomain(final FictionBookUpdateDto fictionBookUpdateDto, final long number) {
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Collection<Donor>     donors;
        final Title                 title;
        final String                supertitle;
        final String                subtitle;
        final Instant               donationDate;
        final Optional<Donation>    donation;

        // Authors
        if (fictionBookUpdateDto.getAuthors() == null) {
            authors = List.of();
        } else {
            authors = fictionBookUpdateDto.getAuthors()
                .stream()
                .map(a -> new Author(a.getNumber(), ""))
                .toList();
        }

        // Publishers
        if (fictionBookUpdateDto.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = fictionBookUpdateDto.getPublishers()
                .stream()
                .map(p -> new Publisher(p.getNumber(), ""))
                .toList();
        }

        // Donation
        if (fictionBookUpdateDto.getDonation() == null) {
            donation = Optional.empty();
        } else {
            if (fictionBookUpdateDto.getDonation()
                .getDonors() == null) {
                donors = List.of();
            } else {
                donors = fictionBookUpdateDto.getDonation()
                    .getDonors()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(d -> new Donor(d.getNumber(), new PersonName("", "")))
                    .toList();
            }
            if (fictionBookUpdateDto.getDonation()
                .getDate() == null) {
                donationDate = null;
            } else {
                donationDate = fictionBookUpdateDto.getDonation()
                    .getDate();
            }
            if ((donationDate == null) && (donors.isEmpty())) {
                donation = Optional.empty();
            } else {
                donation = Optional.of(new Donation(donationDate, donors));
            }
        }

        // Title
        if (fictionBookUpdateDto.getTitle()
            .getSupertitle() == null) {
            supertitle = "";
        } else {
            supertitle = fictionBookUpdateDto.getTitle()
                .getSupertitle();
        }
        if (fictionBookUpdateDto.getTitle()
            .getSubtitle() == null) {
            subtitle = "";
        } else {
            subtitle = fictionBookUpdateDto.getTitle()
                .getSubtitle();
        }
        title = new Title(supertitle, fictionBookUpdateDto.getTitle()
            .getTitle(), subtitle);
        return new FictionBook(number, title, fictionBookUpdateDto.getIsbn(), fictionBookUpdateDto.getLanguage(),
            fictionBookUpdateDto.getPublishDate(), false, authors, List.of(), publishers, donation);
    }

    public static final FictionBookResponseDto toResponseDto(final FictionBook fictionBook) {
        return new FictionBookResponseDto().content(toDto(fictionBook));
    }

    public static final FictionBookResponseDto toResponseDto(final Optional<FictionBook> fictionBook) {
        return new FictionBookResponseDto().content(fictionBook.map(FictionBookDtoMapper::toDto)
            .orElse(null));
    }

    public static final FictionBookPageResponseDto toResponseDto(final Page<FictionBook> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(FictionBookDtoMapper::toDto)
            .toList());
        return new FictionBookPageResponseDto().content(page.content()
            .stream()
            .map(FictionBookDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sortingResponse);
    }

    private static final AuthorRefDto toDto(final Author author) {
        return new AuthorRefDto().number(author.number());
    }

    private static final DonationDto toDto(final Donation donation) {
        final List<DonorRefDto> donors;

        donors = donation.donors()
            .stream()
            .map(FictionBookDtoMapper::toDto)
            .toList();
        return new DonationDto().date(donation.date())
            .donors(donors);
    }

    private static final DonorRefDto toDto(final Donor donor) {
        return new DonorRefDto().number(donor.number());
    }

    private static final FictionBookDto toDto(final FictionBook fictionBook) {
        final BookTitleDto          title;
        final List<AuthorRefDto>    authors;
        final List<PublisherRefDto> publishers;
        final DonationDto           donation;

        title = new BookTitleDto().title(fictionBook.title()
            .title())
            .subtitle(fictionBook.title()
                .subtitle())
            .supertitle(fictionBook.title()
                .supertitle());
        if (fictionBook.donation()
            .isPresent()) {
            donation = toDto(fictionBook.donation()
                .get());
        } else {
            donation = null;
        }
        authors = fictionBook.authors()
            .stream()
            .map(FictionBookDtoMapper::toDto)
            .toList();
        publishers = fictionBook.publishers()
            .stream()
            .map(FictionBookDtoMapper::toDto)
            .toList();
        return new FictionBookDto().number(fictionBook.number())
            .title(title)
            .isbn(fictionBook.isbn())
            .language(fictionBook.language())
            .publishDate(fictionBook.publishDate())
            .authors(authors)
            .publishers(publishers)
            .donation(donation);
    }

    private static final PropertyDto toDto(final Property property) {
        final DirectionEnum direction;

        if (property.direction() == Direction.ASC) {
            direction = DirectionEnum.ASC;
        } else {
            direction = DirectionEnum.DESC;
        }
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private static final PublisherRefDto toDto(final Publisher publisher) {
        return new PublisherRefDto().number(publisher.number());
    }

    private FictionBookDtoMapper() {
        super();
    }

}
