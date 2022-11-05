
package com.bernardomg.association.test.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = { "READ_BALANCE", "READ_FEE_YEAR", "READ_MEMBER_STATS", "CREATE_FEE", "READ_FEE",
        "UPDATE_FEE", "DELETE_FEE", "CREATE_MEMBER", "READ_MEMBER", "UPDATE_MEMBER", "DELETE_MEMBER",
        "CREATE_TRANSACTION", "READ_TRANSACTION", "UPDATE_TRANSACTION", "DELETE_TRANSACTION" })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
public @interface AllAuthoritiesMockUser {

}
