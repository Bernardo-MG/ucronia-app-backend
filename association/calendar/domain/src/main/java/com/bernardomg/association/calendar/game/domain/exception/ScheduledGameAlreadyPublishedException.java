
package com.bernardomg.association.calendar.game.domain.exception;

/**
 * Scheduled game already published exception.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public class ScheduledGameAlreadyPublishedException extends RuntimeException {

    private static final long serialVersionUID = -2547922646355830379L;

    /**
     * Number which caused the exception.
     */
    private final Long        number;

    public ScheduledGameAlreadyPublishedException(final long number) {
        super(String.format("Scheduled game %d is already published", number));

        this.number = number;
    }

    /**
     * Returns the number which caused the exception.
     *
     * @return the number which caused the exception
     */
    public final Long getNumber() {
        return number;
    }

}
