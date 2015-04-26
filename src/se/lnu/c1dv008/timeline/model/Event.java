package se.lnu.c1dv008.timeline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
