
package com.bernardomg.association.test.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.time.Instant;

import com.bernardomg.framework.testing.architecture.predicates.IsJpaAnnotatedClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public class EntityRules {

    /**
     * JPA entities should only use Instant for date/time.
     */
    @ArchTest
    static final ArchRule jpa_entities_should_only_use_instant = fields().that()
        .areDeclaredInClassesThat(new IsJpaAnnotatedClass())
        .and()
        .areNotStatic()
        .and()
        .areNotFinal()
        .should()
        .haveRawType(Instant.class)
        .orShould()
        .notHaveRawType(java.util.Date.class)
        .andShould()
        .notHaveRawType(java.sql.Date.class)
        .andShould()
        .notHaveRawType(java.time.LocalDate.class)
        .andShould()
        .notHaveRawType(java.time.LocalDateTime.class)
        .andShould()
        .notHaveRawType(java.time.ZonedDateTime.class)
        .andShould()
        .notHaveRawType(java.time.Year.class)
        .andShould()
        .notHaveRawType(java.time.YearMonth.class)
        .andShould()
        .notHaveRawType(java.time.MonthDay.class)
        .because("Only java.time.Instant is allowed for representing date/time in entities.");

}
