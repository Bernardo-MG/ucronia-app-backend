
package com.bernardomg.security.auth.jwt.token.test.config;

import java.nio.charset.Charset;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public final class TokenConstants {

    public static final SecretKey KEY = Keys.hmacShaKeyFor(
        "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
            .getBytes(Charset.forName("UTF-8")));

    private TokenConstants() {
        super();
    }

}
