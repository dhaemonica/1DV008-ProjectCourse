package se.lnu.c1dv008.timeline.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "timelines")
public class Timeline {

	@DatabaseField(generatedId = true, columnName = "timelineId")
	public long id;

	@DatabaseField
	public String name;

	@DatabaseField
	public String description;

	public Timeline() {

	}

	public Timeline(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
