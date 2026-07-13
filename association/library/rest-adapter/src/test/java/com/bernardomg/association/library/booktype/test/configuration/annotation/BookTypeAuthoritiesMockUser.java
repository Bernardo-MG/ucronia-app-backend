
package com.bernardomg.association.library.booktype.test.configuration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = { "LIBRARY_BOOK_TYPE:READ", "LIBRARY_BOOK_TYPE:CREATE", "LIBRARY_BOOK_TYPE:UPDATE",
        "LIBRARY_BOOK_TYPE:DELETE" })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
@Documented
public @interface BookTypeAuthoritiesMockUser {

}
