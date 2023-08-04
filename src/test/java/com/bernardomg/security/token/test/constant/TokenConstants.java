
package com.bernardomg.security.token.test.constant;

import java.nio.charset.Charset;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public final class TokenConstants {

    public static final SecretKey KEY   = Keys.hmacShaKeyFor(
        "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
            .getBytes(Charset.forName("UTF-8")));

    public static final String    TOKEN = "MTY5MTE1MDA1MDkwMDo3NmVlNTJhY2JkNzgwZDRkMzE4YWFhMGU2MGJkNzhmNGZhMDlkM2U0MzBiNmEyMWY2NWUzZTQ5MTQwYTU3MmE1OmFkbWluOjA1MDE1ODE5MzQ0MjEyOTAyZTI4YTBmNDkyMzdkM2FiNzEyNGM0OGU3MTM0YmI4MjViN2Q3Y2FlNzI1YjkyZjAwM2QxMDJkZWM3NDI1NmZmMzJlNjkyMzBhMmFkZjdlMTA5OGE2MjVkZmIwMGM1OTU1NzU0ZDFmMjdlZDM5NzI5";

    private TokenConstants() {
        super();
    }

}
