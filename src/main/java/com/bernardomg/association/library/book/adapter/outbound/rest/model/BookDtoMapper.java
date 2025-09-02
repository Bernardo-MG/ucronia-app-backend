
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.AuthorDto;
import com.bernardomg.ucronia.openapi.model.BookCreationDto;
import com.bernardomg.ucronia.openapi.model.BookLendingInfoDto;
import com.bernardomg.ucronia.openapi.model.BookTitleDto;
import com.bernardomg.ucronia.openapi.model.BookTypeDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.DonationDto;
import com.bernardomg.ucronia.openapi.model.FictionBookDto;
import com.bernardomg.ucronia.openapi.model.FictionBookPageResponseDto;
import com.bernardomg.ucronia.openapi.model.FictionBookResponseDto;
import com.bernardomg.ucronia.openapi.model.FictionBookUpdateDto;
import com.bernardomg.ucronia.openapi.model.GameBookDto;
import com.bernardomg.ucronia.openapi.model.GameBookPageResponseDto;
import com.bernardomg.ucronia.openapi.model.GameBookResponseDto;
import com.bernardomg.ucronia.openapi.model.GameBookUpdateDto;
import com.bernardomg.ucronia.openapi.model.GameSystemDto;
import com.bernardomg.ucronia.openapi.model.MinimalContactDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.PublisherDto;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class BookDtoMapper {

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
                .map(a -> new Author(a, ""))
                .toList();
        }

        // Publishers
        if (fictionBookUpdateDto.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = fictionBookUpdateDto.getPublishers()
                .stream()
                .map(p -> new Publisher(p, ""))
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

    public static final GameBook toDomain(final GameBookUpdateDto gameBookUpdateDto, final long number) {
        final Collection<Author>    authors;
        final Collection<Publisher> publishers;
        final Optional<BookType>    bookType;
        final Optional<GameSystem>  gameSystem;
        final Collection<Donor>     donors;
        final Title                 title;
        final String                supertitle;
        final String                subtitle;
        final Instant               donationDate;
        final Optional<Donation>    donation;

        // Authors
        if (gameBookUpdateDto.getAuthors() == null) {
            authors = List.of();
        } else {
            authors = gameBookUpdateDto.getAuthors()
                .stream()
                .map(a -> new Author(a, ""))
                .toList();
        }

        // Publishers
        if (gameBookUpdateDto.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = gameBookUpdateDto.getPublishers()
                .stream()
                .map(p -> new Publisher(p, ""))
                .toList();
        }

        // Book type
        if ((gameBookUpdateDto.getBookType() == null) || (gameBookUpdateDto.getBookType() == null)) {
            bookType = Optional.empty();
        } else {
            bookType = Optional.of(new BookType(gameBookUpdateDto.getBookType(), ""));
        }

        // Game system
        if ((gameBookUpdateDto.getGameSystem() == null) || (gameBookUpdateDto.getGameSystem() == null)) {
            gameSystem = Optional.empty();
        } else {
            gameSystem = Optional.of(new GameSystem(gameBookUpdateDto.getGameSystem(), ""));
        }

        // Donation
        if (gameBookUpdateDto.getDonation() == null) {
            donation = Optional.empty();
        } else {
            if (gameBookUpdateDto.getDonation()
                .getDonors() == null) {
                donors = List.of();
            } else {
                donors = gameBookUpdateDto.getDonation()
                    .getDonors()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(d -> new Donor(d.getNumber(), new PersonName("", "")))
                    .toList();
            }
            if (gameBookUpdateDto.getDonation()
                .getDate() == null) {
                donationDate = null;
            } else {
                donationDate = gameBookUpdateDto.getDonation()
                    .getDate();
            }
            if ((donationDate == null) && (donors.isEmpty())) {
                donation = Optional.empty();
            } else {
                donation = Optional.of(new Donation(donationDate, donors));
            }
        }

        // Title
        if (gameBookUpdateDto.getTitle()
            .getSupertitle() == null) {
            supertitle = "";
        } else {
            supertitle = gameBookUpdateDto.getTitle()
                .getSupertitle();
        }
        if (gameBookUpdateDto.getTitle()
            .getSubtitle() == null) {
            subtitle = "";
        } else {
            subtitle = gameBookUpdateDto.getTitle()
                .getSubtitle();
        }
        title = new Title(supertitle, gameBookUpdateDto.getTitle()
            .getTitle(), subtitle);
        return new GameBook(number, title, gameBookUpdateDto.getIsbn(), gameBookUpdateDto.getLanguage(),
            gameBookUpdateDto.getPublishDate(), false, authors, List.of(), publishers, donation, bookType, gameSystem);
    }

    public static final FictionBook toFictionDomain(final BookCreationDto bookCreationDto) {
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
        return new FictionBook(-1, title, bookCreationDto.getIsbn(), bookCreationDto.getLanguage(), null, false,
            List.of(), List.of(), List.of(), Optional.empty());
    }

    public static final FictionBookResponseDto toFictionResponseDto(final Optional<FictionBook> fictionBooks) {
        return new FictionBookResponseDto().content(fictionBooks.map(BookDtoMapper::toDto)
            .orElse(null));
    }

    public static final FictionBookPageResponseDto toFictionResponseDto(final Page<FictionBook> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList());
        return new FictionBookPageResponseDto().content(page.content()
            .stream()
            .map(BookDtoMapper::toDto)
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

    public static final GameBook toGameDomain(final BookCreationDto bookCreationDto) {
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
        return new GameBook(-1, title, bookCreationDto.getIsbn(), bookCreationDto.getLanguage(), null, false, List.of(),
            List.of(), List.of(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static final GameBookResponseDto toGameResponseDto(final Optional<GameBook> gameBooks) {
        return new GameBookResponseDto().content(gameBooks.map(BookDtoMapper::toDto)
            .orElse(null));
    }

    public static final GameBookPageResponseDto toGameResponseDto(final Page<GameBook> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList());
        return new GameBookPageResponseDto().content(page.content()
            .stream()
            .map(BookDtoMapper::toDto)
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

    public static final FictionBookResponseDto toResponseDto(final FictionBook fictionBook) {
        return new FictionBookResponseDto().content(toDto(fictionBook));
    }

    public static final GameBookResponseDto toResponseDto(final GameBook gameBook) {
        return new GameBookResponseDto().content(toDto(gameBook));
    }

    private static final AuthorDto toDto(final Author author) {
        return new AuthorDto().number(author.number());
    }

    private static final BookLendingInfoDto toDto(final BookLendingInfo lending) {
        final ContactNameDto    contactName;
        final MinimalContactDto borrower;

        contactName = new ContactNameDto().firstName(lending.borrower()
            .name()
            .firstName())
            .lastName(lending.borrower()
                .name()
                .lastName())
            .fullName(lending.borrower()
                .name()
                .fullName());
        borrower = new MinimalContactDto().name(contactName)
            .number(lending.borrower()
                .number());
        return new BookLendingInfoDto().borrower(borrower)
            .lendingDate(lending.lendingDate())
            .returnDate(lending.returnDate());
    }

    private static final DonationDto toDto(final Donation donation) {
        final List<MinimalContactDto> donors;

        donors = donation.donors()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        return new DonationDto().date(donation.date())
            .donors(donors);
    }

    private static final MinimalContactDto toDto(final Donor donor) {
        final ContactNameDto contactName;

        contactName = new ContactNameDto().firstName(donor.name()
            .firstName())
            .lastName(donor.name()
                .lastName())
            .fullName(donor.name()
                .fullName());
        return new MinimalContactDto().number(donor.number())
            .name(contactName);
    }

    private static final FictionBookDto toDto(final FictionBook fictionBook) {
        final BookTitleDto             title;
        final List<AuthorDto>          authors;
        final List<PublisherDto>       publishers;
        final DonationDto              donation;
        final List<BookLendingInfoDto> lendings;

        title = new BookTitleDto().title(fictionBook.title()
            .title())
            .subtitle(fictionBook.title()
                .subtitle())
            .supertitle(fictionBook.title()
                .supertitle())
            .fullTitle(fictionBook.title()
                .fullTitle());
        if (fictionBook.donation()
            .isPresent()) {
            donation = toDto(fictionBook.donation()
                .get());
        } else {
            donation = null;
        }
        authors = fictionBook.authors()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        publishers = fictionBook.publishers()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        lendings = fictionBook.lendings()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        return new FictionBookDto().number(fictionBook.number())
            .title(title)
            .isbn(fictionBook.isbn())
            .language(fictionBook.language())
            .publishDate(fictionBook.publishDate())
            .authors(authors)
            .publishers(publishers)
            .lendings(lendings)
            .lent(fictionBook.lent())
            .donation(donation);
    }

    private static final GameBookDto toDto(final GameBook gameBook) {
        final BookTitleDto             title;
        final List<AuthorDto>          authors;
        final List<PublisherDto>       publishers;
        final DonationDto              donation;
        final GameSystemDto            gameSystem;
        final BookTypeDto              bookType;
        final List<BookLendingInfoDto> lendings;

        title = new BookTitleDto().title(gameBook.title()
            .title())
            .subtitle(gameBook.title()
                .subtitle())
            .supertitle(gameBook.title()
                .supertitle())
            .fullTitle(gameBook.title()
                .fullTitle());
        if (gameBook.donation()
            .isPresent()) {
            donation = toDto(gameBook.donation()
                .get());
        } else {
            donation = null;
        }
        authors = gameBook.authors()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        publishers = gameBook.publishers()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        lendings = gameBook.lendings()
            .stream()
            .map(BookDtoMapper::toDto)
            .toList();
        if (gameBook.gameSystem()
            .isPresent()) {
            gameSystem = new GameSystemDto().number(gameBook.gameSystem()
                .get()
                .number());
        } else {
            gameSystem = null;
        }
        if (gameBook.bookType()
            .isPresent()) {
            bookType = new BookTypeDto().number(gameBook.bookType()
                .get()
                .number());
        } else {
            bookType = null;
        }
        return new GameBookDto().number(gameBook.number())
            .title(title)
            .isbn(gameBook.isbn())
            .language(gameBook.language())
            .publishDate(gameBook.publishDate())
            .authors(authors)
            .publishers(publishers)
            .lendings(lendings)
            .lent(gameBook.lent())
            .donation(donation)
            .gameSystem(gameSystem)
            .bookType(bookType);
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

    private static final PublisherDto toDto(final Publisher publisher) {
        return new PublisherDto().number(publisher.number());
    }

    private BookDtoMapper() {
        super();
    }

}
