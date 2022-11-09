
package com.bernardomg.security.email.sender;

public final class DisabledSecurityEmailSender implements SecurityEmailSender {

    public DisabledSecurityEmailSender() {}

    @Override
    public final void sendSignUpEmail(final String username, final String email) {}

}
