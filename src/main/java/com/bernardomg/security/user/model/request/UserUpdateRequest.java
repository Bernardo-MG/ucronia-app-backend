
package com.bernardomg.security.user.model.request;

public interface UserUpdateRequest {

    /**
     * Returns the credentials expired flag.
     * <p>
     * This usually means that the password is no longer valid.
     *
     * @return the credentials expired flag
     */
    public Boolean getCredentialsExpired();

    /**
     * Returns the user email.
     *
     * @return the user email
     */
    public String getEmail();

    /**
     * Returns the user enabled flag.
     *
     * @return the user enabled flag
     */
    public Boolean getEnabled();

    /**
     * Returns the user expired flag.
     * <p>
     * This means the user is no longer valid.
     *
     * @return the user expired flag
     */
    public Boolean getExpired();

    /**
     * Returns the user id.
     *
     * @return the user id
     */
    public Long getId();

    /**
     * Returns the user locked flag.
     *
     * @return the user locked flag
     */
    public Boolean getLocked();

    /**
     * Returns the user name.
     *
     * @return the user name
     */
    public String getName();

    /**
     * Returns the user username.
     *
     * @return the user username
     */
    public String getUsername();

}
