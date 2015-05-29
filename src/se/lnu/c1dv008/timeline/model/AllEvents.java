package se.lnu.c1dv008.timeline.model;

import java.time.LocalDate;

/**
 * Created by otto on 2015-05-20.
 */
public class AllEvents implements Comparable<AllEvents> {

    public long id;
    public long timelineId;
    public String name;
    public String description;
    public String startTime;
    public String endTime;
    public String color;


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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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


    public int compareTo(AllEvents o) {
        return LocalDate.parse(this.getStartTime()).compareTo(LocalDate.parse(o.getStartTime()));
    }
}
