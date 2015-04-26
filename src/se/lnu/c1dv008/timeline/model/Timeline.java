package se.lnu.c1dv008.timeline.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timelines")
public class Timeline {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	public String name;
	public String description;
	private Date startDate;
	private Date endDate;

	public Timeline(){
		
	}

	public Timeline(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	
	public void setTimeBounds(Date startDate, Date endDate){
		this.startDate=startDate;
		this.endDate=endDate;
	}

}
