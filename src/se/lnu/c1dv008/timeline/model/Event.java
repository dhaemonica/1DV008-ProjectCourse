package se.lnu.c1dv008.timeline.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "events")
public class Event {

	@DatabaseField(generatedId = true, columnName = "eventId")
	public long id;

	@DatabaseField
	public long timelineId;

	@DatabaseField
	public String name, description;

	@DatabaseField
	public long startTime, endTime;

	@DatabaseField
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
