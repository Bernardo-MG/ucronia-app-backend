
package com.bernardomg.association.person.adapter.inbound.jpa.specification;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.domain.query.PersonQuery;

public final class PersonSpecifications {

    public static Optional<Specification<PersonEntity>> fromQuery(final PersonQuery query) {
        return switch (query.getStatus()) {
            case ACTIVE -> Optional.of(active());
            case INACTIVE -> Optional.of(inactive());
            case NO_MEMBER -> Optional.of(noMember());
            default -> Optional.empty();
        };
    }

    /**
     * Member and active specification.
     *
     * @return active specification
     */
    private static Specification<PersonEntity> active() {
        return (root, query, cb) -> cb.and(cb.isTrue(root.get("member")), cb.isTrue(root.get("active")));
    }

    /**
     * Member and inactive specification.
     *
     * @return inactive specification
     */
    private static Specification<PersonEntity> inactive() {
        return (root, query, cb) -> cb.and(cb.isTrue(root.get("member")), cb.isFalse(root.get("active")));
    }

    /**
     * No member.
     *
     * @return no member specification
     */
    private static Specification<PersonEntity> noMember() {
        return (root, query, cb) -> cb.isFalse(root.get("member"));
    }

    private PersonSpecifications() {
        super();
    }

}
