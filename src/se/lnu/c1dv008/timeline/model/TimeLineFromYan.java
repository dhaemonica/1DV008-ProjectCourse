package se.lnu.c1dv008.timeline.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timelines")
public class TimeLineFromYan {
	
	
	@Id
	public long id;

	public String name;
	public String description;
	private int counterEvent;
	private LocalDate startDate;
	private LocalDate endDate;

	public TimeLineFromYan(){
		
	}

	public TimeLineFromYan(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	
	public void setTime(LocalDate startDate, LocalDate endDate){
		
		this.startDate=startDate;
		this.endDate=endDate;
		
		
	}
	
	
	
	
	

}
