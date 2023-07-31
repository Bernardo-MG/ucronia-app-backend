
package com.bernardomg.security.token.provider;

public interface TokenStore<T> {

    public void closeToken(final String token);

    /**
     * Returns an object decoded from the token.
     *
     * @param token
     *            token to decode as the object
     * @return object from the token
     */
    public T decode(final String token);

    public boolean exists(final String token);

    /**
     * Returns a token for the subject.
     *
     * @param subject
     *            subject of the token
     * @return token for the subject
     */
    public String generateToken(final String subject);

    /**
     * Check if the token has expired.
     *
     * @param token
     *            token to validate
     * @return {@code true} if the token has expired, {@code false} otherwise
     */
    public Boolean isValid(final String token);

}
