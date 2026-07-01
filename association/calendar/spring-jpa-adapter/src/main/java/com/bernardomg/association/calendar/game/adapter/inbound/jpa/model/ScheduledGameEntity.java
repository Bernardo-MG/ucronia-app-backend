
package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import java.time.Instant;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "ScheduledGame")
@Table(schema = "calendar", name = "scheduled_games")
@PrimaryKeyJoinColumn(name = "calendar_info")
public class ScheduledGameEntity extends CalendarInfoEntity {

    @Transient
    private static final long          serialVersionUID = 1633865182622321844L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "master_id", nullable = false)
    private ScheduledGameProfileEntity master;

    @Column(name = "max_players", nullable = false)
    private Integer                    maxPlayers;

    @Column(name = "published", nullable = false)
    private Boolean                    published;

    @Embedded
    private RecurrenceEmbeddable       recurrence;

    @Column(name = "start_date", nullable = false)
    private Instant                    start;

    public ScheduledGameProfileEntity getMaster() {
        return master;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Boolean getPublished() {
        return published;
    }

    public RecurrenceEmbeddable getRecurrence() {
        return recurrence;
    }

    public Instant getStart() {
        return start;
    }

    public void setMaster(final ScheduledGameProfileEntity master) {
        this.master = master;
    }

    public void setMaxPlayers(final Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setPublished(final Boolean published) {
        this.published = published;
    }

    public void setRecurrence(final RecurrenceEmbeddable recurrence) {
        this.recurrence = recurrence;
    }

    public void setStart(final Instant start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "ScheduledGameEntity [id=" + getId() + ", number=" + getNumber() + ", calendarDates="
                + getCalendarDates() + ", description=" + getDescription() + ", image=" + getImage() + ", location="
                + getLocation() + ", title=" + getTitle() + ", types=" + getTypes() + ", master=" + master
                + ", maxPlayers=" + maxPlayers + ", published=" + published + ", recurrence=" + recurrence + ", start="
                + start + "]";
    }

}
