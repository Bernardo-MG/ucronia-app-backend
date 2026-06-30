
package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "ScheduledGame")
@Table(schema = "calendar", name = "scheduled_games")
public class ScheduledGameEntity implements Serializable {

    @Transient
    private static final long          serialVersionUID = 1633865182622321844L;

    @Column(name = "description", nullable = false, length = 4000)
    private String                     description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                       id;

    @Column(name = "image", nullable = false)
    private String                     image;

    @Column(name = "location", nullable = false)
    private String                     location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "master_id", nullable = false)
    private ScheduledGameProfileEntity master;

    @Column(name = "max_players", nullable = false)
    private Integer                    maxPlayers;

    @Column(name = "number", nullable = false, unique = true)
    private Long                       number;

    @Column(name = "published", nullable = false)
    private Boolean                    published;

    @Embedded
    private RecurrenceEmbeddable       recurrence;

    @Column(name = "start_date", nullable = false)
    private Instant                    start;

    @Column(name = "title", nullable = false)
    private String                     title;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final ScheduledGameEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public ScheduledGameProfileEntity getMaster() {
        return master;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Long getNumber() {
        return number;
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

    public String getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setMaster(final ScheduledGameProfileEntity master) {
        this.master = master;
    }

    public void setMaxPlayers(final Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setNumber(final Long number) {
        this.number = number;
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

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ScheduledGameEntity [id=" + id + ", number=" + number + ", title=" + title + ", description="
                + description + ", image=" + image + ", location=" + location + ", master=" + master + ", maxPlayers="
                + maxPlayers + ", published=" + published + ", recurrence=" + recurrence + ", start=" + start + "]";
    }

}
