
package com.bernardomg.security.token.test.constant;

import java.nio.charset.Charset;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public final class TokenConstants {

    public static final SecretKey KEY   = Keys.hmacShaKeyFor(
        "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
            .getBytes(Charset.forName("UTF-8")));

    public static final String    SCOPE = "scope";

    public static final String    TOKEN = "MTY5MTI0NTI5ODcwMToxYzFlNWY4ZDQ2NDgyNmI5MjZkMzNiYzRlNjBmZmJhZDUyZTc0ODRlOGFhYmNmY2E0YWYzZWM2ZDk1NmVkNDM1OmFkbWluOjM0NWRhMTM0NDMzNTU4ZGE0MGQ1MzE1MDc1MmQxODhiNDZiYWVkYWNlNDU2ZjdmODdiNDUyZjhlZmE4NWQ4NDI2MTZmM2YxODIzOTBlMWJlNDY2NTYwYjgyNDQ2NWNmOGI0MDA1ZGExZDJiYzMxODc1M2MzYzEzY2NjOWQ5ZDQ2";

    private TokenConstants() {
        super();
    }

}
