
package com.bernardomg.security.test.constant;

import java.nio.charset.Charset;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public final class TokenConstants {

    public static final SecretKey KEY   = Keys.hmacShaKeyFor(
        "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
            .getBytes(Charset.forName("UTF-8")));

    public static final String    TOKEN = "MTY2ODM4NTE2MTEyNzowODkxOWEyNzNjNGM4OGYwYzliMWEzOTg3MTU2MmNmODgxYzM3OTc1YzA1ZjhhOTI5YjE4YmI3Y2MwYzNkZDFmOmFkbWluOjcyMTVkZjhkNmVkMTE2OWRmNmNjZTk3M2I4YjQxNmE0NjI2NmFmMjIyMmQ0OGFiYzhlZjc1ZDQ4YTc5ZTI5MGQ1N2VkYzZlMTUzOTQxODZiZWNiYWQ5Njg5N2UxNTA0ZjZlYzFiMTQzOGRmMmM0ZDE5ZmNjYzQ4NDMyODE0Nzc5";

    private TokenConstants() {
        super();
    }

}
