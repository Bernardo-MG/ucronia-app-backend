
package com.bernardomg.association.person.adapter.outbound.rest.model;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.model.Person.PersonContact;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.MembershipDto;
import com.bernardomg.ucronia.openapi.model.PersonChangeDto;
import com.bernardomg.ucronia.openapi.model.PersonContactDto;
import com.bernardomg.ucronia.openapi.model.PersonCreationDto;
import com.bernardomg.ucronia.openapi.model.PersonDto;
import com.bernardomg.ucronia.openapi.model.PersonPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class PersonDtoMapper {

    public static final Person toDomain(final long number, final PersonChangeDto change) {
        final PersonName           name;
        final Optional<Membership> membership;

        name = new PersonName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        if (change.getMembership() == null) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(change.getMembership()
                .getActive(),
                change.getMembership()
                    .getRenew()));
        }

        return new Person(change.getIdentifier(), number, name, change.getBirthDate(), membership, List.of());
    }

    public static final Person toDomain(final PersonCreationDto creation) {
        final PersonName           name;
        final Optional<Membership> membership;

        name = new PersonName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());
        if (creation.getMember() == null) {
            membership = Optional.of(new Membership(true, true));
        } else {
            membership = Optional.empty();
        }

        return new Person("", -1L, name, null, membership, List.of());
    }

    public static final PersonResponseDto toResponseDto(final Optional<Person> person) {
        return new PersonResponseDto().content(person.map(PersonDtoMapper::toDto)
            .orElse(null));
    }

    public static final PersonPageResponseDto toResponseDto(final Page<Person> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(PersonDtoMapper::toDto)
            .toList());
        return new PersonPageResponseDto().content(page.content()
            .stream()
            .map(PersonDtoMapper::toDto)
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

    public static final PersonResponseDto toResponseDto(final Person person) {
        return new PersonResponseDto().content(PersonDtoMapper.toDto(person));
    }

    private static final PersonDto toDto(final Person person) {
        ContactNameDto         name;
        MembershipDto          membership;
        List<PersonContactDto> contacts;

        name = new ContactNameDto().firstName(person.name()
            .firstName())
            .lastName(person.name()
                .lastName())
            .fullName(person.name()
                .fullName());
        if (person.membership()
            .isPresent()) {
            membership = new MembershipDto().active(person.membership()
                .get()
                .active())
                .renew(person.membership()
                    .get()
                    .renew());
        } else {
            membership = null;
        }
        contacts = person.contacts()
            .stream()
            .map(PersonDtoMapper::toDto)
            .toList();

        return new PersonDto().identifier(person.identifier())
            .number(person.number())
            .name(name)
            .birthDate(person.birthDate())
            .membership(membership)
            .contacts(contacts);
    }

    private static final PersonContactDto toDto(final PersonContact contact) {
        ContactMethodDto method;

        method = new ContactMethodDto().number(contact.method()
            .number())
            .name(contact.method()
                .name());

        return new PersonContactDto().contact(contact.contact())
            .method(method);
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

    private PersonDtoMapper() {
        super();
    }

}
