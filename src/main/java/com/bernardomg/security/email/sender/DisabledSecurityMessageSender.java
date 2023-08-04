
package com.bernardomg.security.email.sender;

public final class DisabledSecurityMessageSender implements SecurityMessageSender {

    public DisabledSecurityMessageSender() {
        super();
    }

    @Override
    public final void sendPasswordRecoveryMessage(final String email, final String token) {}

    @Override
    public final void sendSignUpMessage(final String username, final String email) {}

}