package com.bernardomg.content.domain.policy;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.content.domain.exception.ContentEmptyException;
import com.bernardomg.content.domain.exception.ContentTooLargeException;
import com.bernardomg.content.domain.exception.ContentTypeNotAllowedException;

@DisplayName("Restricted content policy")
class TestRestrictedContentPolicy {

    private static final long MAXIMUM_SIZE = 10L;

    private final ContentPolicy policy = new RestrictedContentPolicy(MAXIMUM_SIZE, Set.of("image/png"));

    @Test
    @DisplayName("Content matching the restrictions is accepted")
    void testValid() {
        final ThrowingCallable callable;

        // WHEN
        callable = () -> policy.validate(MAXIMUM_SIZE, "IMAGE/PNG; charset=binary");

        // THEN
        Assertions.assertThatCode(callable)
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Empty content is rejected")
    void testEmpty() {
        final ThrowingCallable callable;

        // WHEN
        callable = () -> policy.validate(0L, "image/png");

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ContentEmptyException.class);
    }

    @Test
    @DisplayName("Oversized content is rejected")
    void testTooLarge() {
        final ThrowingCallable callable;

        // WHEN
        callable = () -> policy.validate(MAXIMUM_SIZE + 1, "image/png");

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ContentTooLargeException.class);
    }

    @Test
    @DisplayName("Content with an unsupported media type is rejected")
    void testUnsupportedMediaType() {
        final ThrowingCallable callable;

        // WHEN
        callable = () -> policy.validate(MAXIMUM_SIZE, "application/pdf");

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(ContentTypeNotAllowedException.class);
    }

}
