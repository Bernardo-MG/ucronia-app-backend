
package com.bernardomg.association.calendar.game.domain.exception;

import com.bernardomg.association.calendar.domain.model.CalendarStatus;

/**
 * Scheduled game can't be published exception.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public class ScheduledGameNotPublishableException extends RuntimeException {

    private static final long    serialVersionUID = -2547922646355830379L;

    /**
     * Number which caused the exception.
     */
    private final Long           number;

    private final CalendarStatus status;

    public ScheduledGameNotPublishableException(final long number, final CalendarStatus status) {
        super(String.format("Scheduled game %d is in state %s, which is not publishable", number, status));

        this.number = number;
        this.status = status;
    }

    /**
     * Returns the number which caused the exception.
     *
     * @return the number which caused the exception
     */
    public final Long getNumber() {
        return number;
    }

    public CalendarStatus getStatus() {
        return status;
    }

}
