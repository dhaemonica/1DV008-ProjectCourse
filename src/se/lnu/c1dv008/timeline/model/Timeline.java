package se.lnu.c1dv008.timeline.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timelines")
public class Timeline {

	@Id
	public long id;

	public String name;
	public String description;

	public Timeline() {

	}

	public Timeline(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
