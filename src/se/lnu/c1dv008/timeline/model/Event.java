package se.lnu.c1dv008.timeline.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event extends AllEvents {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	public long timelineId;
	public String name;
	public String description;
	public String startTime;
	public String endTime;
	public String color;

	public Event() {

	}

	public Event(String name, String description, String startTime, String endTime, String color, long timelineId) {
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.color = color;
        this.timelineId = timelineId;
	}

	public String getName() {
		return this.name;
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

	public int compareTo(Event o) {
		return LocalDate.parse(this.getStartTime()).compareTo(LocalDate.parse(o.getStartTime()));
	}
}

