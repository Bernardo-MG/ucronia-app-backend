
package com.bernardomg.security.email.sender;

public final class DisabledSecurityEmailSender implements SecurityEmailSender {

    public DisabledSecurityEmailSender() {
        super();
    }

    @Override
    public final void sendPasswordRecoveryEmail(final String email) {}

    @Override
    public final void sendSignUpEmail(final String username, final String email) {}

}
