
package com.bernardomg.security.token.store;

public interface TokenStore {

    public void consumeToken(final String token);

    public boolean exists(final String token);

    /**
     * Returns a token for a user.
     *
     * @param userId
     *            id for the user who generates the token
     * @param username
     *            username for the user who generates the token
     * @param purpose
     *            purpose for the token
     * @return token for the subject
     */
    public String generateToken(final Long userId, final String username, final String purpose);

    /**
     * Returns the username for the token.
     *
     * @param token
     *            token to decode as the object
     * @return username for the token
     */
    public String getUsername(final String token);

    /**
     * Check if the token has expired.
     *
     * @param token
     *            token to validate
     * @return {@code true} if the token has expired, {@code false} otherwise
     */
    public Boolean isValid(final String token);

}