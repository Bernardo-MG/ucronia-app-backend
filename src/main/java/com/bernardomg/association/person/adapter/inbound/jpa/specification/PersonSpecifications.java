
package com.bernardomg.association.person.adapter.inbound.jpa.specification;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.domain.query.PersonQuery;

public final class PersonSpecifications {

    private static final String ACTIVE_FIELD = "active";

    private static final String MEMBER_FIELD = "member";

    public static Optional<Specification<PersonEntity>> fromQuery(final PersonQuery query) {
        final Optional<Specification<PersonEntity>> nameSpec;
        final Optional<Specification<PersonEntity>> statusSpec;
        final Specification<PersonEntity>           spec;

        if (query.name()
            .isBlank()) {
            nameSpec = Optional.empty();
        } else {
            nameSpec = Optional.of(name(query.name()));
        }

        statusSpec = switch (query.status()) {
            case ACTIVE -> Optional.of(active());
            case INACTIVE -> Optional.of(inactive());
            case NO_MEMBER -> Optional.of(noMember());
            default -> Optional.empty();
        };

        spec = List.of(nameSpec, statusSpec)
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((BinaryOperator<Specification<PersonEntity>>) Specification::and)
            .orElse(null);
        return Optional.ofNullable(spec);
    }

    /**
     * Member and active specification.
     *
     * @return active specification
     */
    private static Specification<PersonEntity> active() {
        return (root, query, cb) -> cb.and(cb.isTrue(root.get(MEMBER_FIELD)), cb.isTrue(root.get(ACTIVE_FIELD)));
    }

    /**
     * Member and inactive specification.
     *
     * @return inactive specification
     */
    private static Specification<PersonEntity> inactive() {
        return (root, query, cb) -> cb.and(cb.isTrue(root.get(MEMBER_FIELD)), cb.isFalse(root.get(ACTIVE_FIELD)));
    }

    /**
     * Name, surname of combination of both. Accepting partial matching.
     *
     * @param pattern
     *            pattern to match
     * @return name specification
     */
    private static Specification<PersonEntity> name(final String pattern) {
        final String likePattern = "%" + pattern + "%";
        return (root, query, cb) -> cb.or(cb.like(cb.lower(root.get("firstName")), likePattern.toLowerCase()),
            cb.like(cb.lower(root.get("lastName")), likePattern.toLowerCase()),
            cb.like(cb.lower(cb.concat(root.get("firstName"), cb.concat(" ", root.get("lastName")))),
                likePattern.toLowerCase()));
    }

    /**
     * No member.
     *
     * @return no member specification
     */
    private static Specification<PersonEntity> noMember() {
        return (root, query, cb) -> cb.isFalse(root.get(MEMBER_FIELD));
    }

    private PersonSpecifications() {
        super();
    }

}
