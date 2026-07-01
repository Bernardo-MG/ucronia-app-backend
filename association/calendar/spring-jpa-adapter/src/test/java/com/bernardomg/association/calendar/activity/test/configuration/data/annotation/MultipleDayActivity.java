
package com.bernardomg.association.calendar.activity.test.configuration.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.jdbc.Sql;

@Sql("/db/queries/calendarInfo/activity.sql")
@Sql("/db/queries/calendarDate/multiple_day.sql")
@Sql("/db/queries/calendarInfo/relationship_multiple_date.sql")
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MultipleDayActivity {

}
