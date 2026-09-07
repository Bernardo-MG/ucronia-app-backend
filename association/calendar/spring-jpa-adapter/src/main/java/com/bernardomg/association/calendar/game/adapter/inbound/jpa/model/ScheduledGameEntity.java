
package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;

import jakarta.persistence.Column;
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

    @ManyToOne
    @JoinColumn(name = "game_table_id")
    private GameTableEntity            table;

    public ScheduledGameProfileEntity getMaster() {
        return master;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public GameTableEntity getTable() {
        return table;
    }

    public void setMaster(final ScheduledGameProfileEntity master) {
        this.master = master;
    }

    public void setMaxPlayers(final Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setTable(final GameTableEntity table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "ScheduledGameEntity [id=" + getId() + ", number=" + getNumber() + ", status=" + getStatus()
                + ", calendarDates=" + getCalendarDates() + ", description=" + getDescription() + ", image="
                + getImage() + ", location=" + getLocation() + ", table=" + getTable() + ", title=" + getTitle()
                + ", types=" + getTypes() + ", master=" + master + ", maxPlayers=" + maxPlayers + ", recurrence="
                + getRecurrence() + ", start=" + getStart() + "]";
    }

}
