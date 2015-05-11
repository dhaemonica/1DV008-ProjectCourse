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
	private String showVal;

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

	public void setTitle(String title) {
		this.name = title;
	}

	public long getId() { return id; }

	public String getShowVal() {
		return showVal;
	}

	public void setShowVal(String showVal) {
		this.showVal = showVal;
	}
}
