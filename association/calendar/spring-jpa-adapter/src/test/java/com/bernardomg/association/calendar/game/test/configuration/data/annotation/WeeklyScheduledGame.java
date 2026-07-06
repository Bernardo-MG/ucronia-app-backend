
package com.bernardomg.association.calendar.game.test.configuration.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.jdbc.Sql;

@Sql("/db/queries/profile/valid.sql")
@Sql({ "/db/queries/calendarInfo/game.sql", "/db/queries/scheduledGame/weekly.sql" })
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WeeklyScheduledGame {

}
