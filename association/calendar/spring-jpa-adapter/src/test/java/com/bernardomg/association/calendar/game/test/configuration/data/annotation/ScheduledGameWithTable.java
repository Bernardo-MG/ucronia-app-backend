
package com.bernardomg.association.calendar.game.test.configuration.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.jdbc.Sql;

@Sql("/db/queries/profile/valid.sql")
@Sql("/db/queries/gameTable/valid.sql")
@Sql({ "/db/queries/calendarInfo/weekly_game.sql", "/db/queries/scheduledGame/with_calendar.sql" })
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ScheduledGameWithTable {

}
