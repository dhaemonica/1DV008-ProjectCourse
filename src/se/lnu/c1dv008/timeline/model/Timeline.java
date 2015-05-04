package se.lnu.c1dv008.timeline.model;

import javax.persistence.*;

@Entity
@Table(name = "timelines")
public class Timeline {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	public String name;
	private String startDate;
	private String endDate;

	public Timeline(){
		
	}

	public Timeline(String name) {
		this.name = name;
	}
	
	
	public void setTimeBounds(String startDate, String endDate){
		this.startDate=startDate;
		this.endDate=endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getTitle() {
		return name;
	}

	public long getId() { return id; }

}
