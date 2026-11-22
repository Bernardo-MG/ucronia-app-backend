
package com.bernardomg.asset.domain.policy;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.asset.domain.exception.ContentEmptyException;
import com.bernardomg.asset.domain.exception.ContentTooLargeException;
import com.bernardomg.asset.domain.exception.ContentTypeNotAllowedException;

@DisplayName("RestrictedContentPolicy - validate")
class TestRestrictedContentPolicy {

    private static final long   MAXIMUM_SIZE = 10L;

    private final ContentPolicy policy       = new RestrictedContentPolicy(MAXIMUM_SIZE, Set.of("image/png"));

    @Test
    @DisplayName("When empty content is submitted, then the content is rejected")
    void testEmpty() {
        final ThrowingCallable action;

        // WHEN
        action = () -> policy.validate(0L, "image/png");

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Empty content is rejected")
            .isInstanceOf(ContentEmptyException.class);
    }

    @Test
    @DisplayName("When oversized content is submitted, then the content is rejected")
    void testTooLarge() {
        final ThrowingCallable action;

        // WHEN
        action = () -> policy.validate(MAXIMUM_SIZE + 1, "image/png");

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Oversized content is rejected")
            .isInstanceOf(ContentTooLargeException.class);
    }

    @Test
    @DisplayName("When content with an unsupported media type is submitted, then the content is rejected")
    void testUnsupportedMediaType() {
        final ThrowingCallable action;

        // WHEN
        action = () -> policy.validate(MAXIMUM_SIZE, "application/pdf");

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Content with an unsupported media type is rejected")
            .isInstanceOf(ContentTypeNotAllowedException.class);
    }

    @Test
    @DisplayName("When content matching the restrictions, then the request is accepted")
    void testValid() {
        final ThrowingCallable action;

        // WHEN
        action = () -> policy.validate(MAXIMUM_SIZE, "IMAGE/PNG; charset=binary");

        // THEN
        Assertions.assertThatCode(action)
            .as("Content matching the restrictions is accepted")
            .doesNotThrowAnyException();
    }

}
