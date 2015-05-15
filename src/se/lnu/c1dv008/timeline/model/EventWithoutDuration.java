package se.lnu.c1dv008.timeline.model;

import javax.persistence.*;

@Entity
@Table(name = "eventsWithoutDuration")
public class EventWithoutDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public long timelineId;
    public String name;
    public String description;
    public String startTime;
    public String color;

    public EventWithoutDuration() {

    }

    public EventWithoutDuration(String name, String description, String startTime, String color, long timelineId) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.color = color;
        this.timelineId = timelineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(long timelineId) {
        this.timelineId = timelineId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

