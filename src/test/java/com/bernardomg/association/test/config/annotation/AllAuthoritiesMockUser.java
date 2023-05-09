
package com.bernardomg.association.test.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = { "BALANCE:READ", "FEE_YEAR:READ", "MEMBER_STATS:READ", "FEE:CREATE", "FEE:READ",
        "FEE:UPDATE", "FEE:DELETE", "MEMBER:CREATE", "MEMBER:READ", "MEMBER:UPDATE", "MEMBER:DELETE",
        "TRANSACTION:CREATE", "TRANSACTION:READ", "TRANSACTION:UPDATE", "TRANSACTION:DELETE" })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
public @interface AllAuthoritiesMockUser {

}
