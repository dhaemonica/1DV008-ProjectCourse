package se.lnu.c1dv008.timeline.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

	@Id
	public long id;

	public long timelineId;
	public String name;
	public String description;
	public long startTime;
	public long endTime;
	public String color;

	public Event() {

	}

	public Event(String name, String description, long startTime, long endTime, String color) {
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.color = color;
	}
}
