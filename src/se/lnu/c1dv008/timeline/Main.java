package se.lnu.c1dv008.timeline;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Timeline work = new Timeline("Work", "Do some job here!");
		Timeline other = new Timeline("Other", "Do some another job here!");

		DB.timelines().create(work);
		DB.timelines().create(other);

		for (Timeline timeline : Arrays.asList(work, other)) {
			for (int i = 0; i < 10; i++) {
				Event event = new Event("Event " + i, "Event #" + i, i, i * 2, "#bacbac");
				event.timelineId = timeline.id;

				DB.events().create(event);
			}
		}

		//out

		List<Timeline> stored = DB.timelines().queryForAll();
		for (Timeline timeline : stored) {
			System.out.println(String.format("%s: %s .. %s", timeline.id, timeline.name, timeline.description));
			List<Event> storedEvents = DB.events().queryForEq("timelineId", timeline.id);
			for (Event event : storedEvents) {
				System.out.println(String.format("\t%s: %s .. %s", event.id, event.name, event.description));
			}
		}
	}
}
