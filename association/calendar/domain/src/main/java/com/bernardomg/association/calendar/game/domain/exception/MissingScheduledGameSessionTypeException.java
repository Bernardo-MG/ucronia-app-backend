
package com.bernardomg.association.calendar.game.domain.exception;

/**
 * Missing scheduled game session type.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public class MissingScheduledGameSessionTypeException extends RuntimeException {

    private static final long serialVersionUID = -2547922646355830379L;

    /**
     * Number which caused the exception.
     */
    private final Long        number;

    public MissingScheduledGameSessionTypeException(final long number) {
        super(String.format("Missing id %s for scheduled game session type", number));

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
