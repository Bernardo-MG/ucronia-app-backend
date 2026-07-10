
package com.bernardomg.association.calendar.game.test.factory;

import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoRecurrence;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntity;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameProfileEntity;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGameConstants;
import com.bernardomg.association.calendar.test.factory.CalendarStatusEntities;
import com.bernardomg.association.calendar.test.factory.CalendarTypeEntities;

public final class ScheduledGameEntities {

    public static final ScheduledGameEntity titleChange() {
        final ScheduledGameEntity        entity;
        final ScheduledGameProfileEntity profile;
        entity = new ScheduledGameEntity();
        entity.setNumber(ScheduledGameConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ScheduledGameConstants.ALTERNATIVE_TITLE);
        entity.setDescription(ScheduledGameConstants.DESCRIPTION);
        entity.setLocation(ScheduledGameConstants.LOCATION);
        entity.setImage(ScheduledGameConstants.IMAGE);
        entity.setMaxPlayers(ScheduledGameConstants.MAX_PLAYERS);
        entity.setStart(ScheduledGameConstants.START);
        entity.setCalendarDates(new HashSet<>());

        profile = ScheduledGameProfileEntities.master();
        entity.setMaster(profile);

        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.oneshot())));

        return entity;
    }

    public static final ScheduledGameEntity weeklyCampaign() {
        final ScheduledGameEntity        entity;
        final ScheduledGameProfileEntity profile;
        final CalendarInfoRecurrence     recurrence;

        entity = new ScheduledGameEntity();
        entity.setNumber(ScheduledGameConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ScheduledGameConstants.TITLE);
        entity.setDescription(ScheduledGameConstants.DESCRIPTION);
        entity.setLocation(ScheduledGameConstants.LOCATION);
        entity.setImage(ScheduledGameConstants.IMAGE);
        entity.setMaxPlayers(ScheduledGameConstants.MAX_PLAYERS);
        entity.setStart(ScheduledGameConstants.START);

        recurrence = new CalendarInfoRecurrence();
        recurrence.setInterval(1);
        recurrence.setUnit(RecurrenceUnit.WEEKLY);
        entity.setRecurrence(recurrence);

        profile = ScheduledGameProfileEntities.master();
        entity.setMaster(profile);

        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.campaign())));

        return entity;
    }

    public static final ScheduledGameEntity weeklyOneshot() {
        final ScheduledGameEntity        entity;
        final ScheduledGameProfileEntity profile;
        final CalendarInfoRecurrence     recurrence;

        entity = new ScheduledGameEntity();
        entity.setNumber(ScheduledGameConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ScheduledGameConstants.TITLE);
        entity.setDescription(ScheduledGameConstants.DESCRIPTION);
        entity.setLocation(ScheduledGameConstants.LOCATION);
        entity.setImage(ScheduledGameConstants.IMAGE);
        entity.setMaxPlayers(ScheduledGameConstants.MAX_PLAYERS);
        entity.setStart(ScheduledGameConstants.START);

        recurrence = new CalendarInfoRecurrence();
        recurrence.setInterval(1);
        recurrence.setUnit(RecurrenceUnit.WEEKLY);
        entity.setRecurrence(recurrence);

        profile = ScheduledGameProfileEntities.master();
        entity.setMaster(profile);

        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.oneshot())));

        return entity;
    }

    private ScheduledGameEntities() {
        super();
    }

}
