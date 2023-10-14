
package com.bernardomg.security.token.store;

public interface UserTokenStore {

    /**
     * Consumes the token, marking it as already used.
     *
     * @param token
     *            token to consume
     */
    public void consumeToken(final String token);

    /**
     * Returns a new token for a user.
     *
     * @param userId
     *            id for the user who generates the token
     * @param username
     *            username for the user who generates the token
     * @return token for the subject
     */
    public String createToken(final Long userId, final String username);

    /**
     * Checks if the received token exists.
     *
     * @param token
     *            token to check
     * @return {@code true} if the token exists, {@code false} otherwise
     */
    public boolean exists(final String token);

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
    public boolean isValid(final String token);

    /**
     * Revokes all the tokens for a user, so they can no longer be used.
     *
     * @param userId
     *            user id
     */
    public void revokeExistingTokens(final Long userId);

}
