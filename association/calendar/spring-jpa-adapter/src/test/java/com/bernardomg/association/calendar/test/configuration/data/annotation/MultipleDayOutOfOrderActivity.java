
package com.bernardomg.association.calendar.test.configuration.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.jdbc.Sql;

@Sql("/db/queries/dateInfo/valid.sql")
@Sql("/db/queries/calendarDate/multiple_day_out_of_order.sql")
@Sql("/db/queries/dateInfo/relationship_multiple_date.sql")
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MultipleDayOutOfOrderActivity {

}
