package se.lnu.c1dv008.test;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.model.Timeline;

public class ModelTest extends Assert {

	@Test
	public void testEvent() {
		Event event = new Event("name", "description", LocalDate.parse("2015-05-03").toString(), LocalDate.parse("2015-05-04").toString(), "#FFFFFF", 1);
		Event event2 = new Event("name", "description", LocalDate.parse("2015-05-05").toString(), LocalDate.parse("2015-05-06").toString(), "#FFFFFF", 1);
		
		assertEquals(-2, event.compareTo(event2));

		assertEquals(event.getName(), event2.getName());
		assertEquals(event.getDescription(), event2.getDescription());
		assertEquals(event.getColor(), event2.getColor());
		assertEquals(event.getTimelineId(), event2.getTimelineId());
	}

	@Test
	public void testEventWithoutDuration() {
		EventWithoutDuration event = new EventWithoutDuration("name", "description", LocalDate.parse("2015-05-03").toString(), "#FFFFFF", 1);
		EventWithoutDuration event2 = new EventWithoutDuration("name", "description", LocalDate.parse("2015-05-05").toString(), "#FFFFFF", 1);

		assertEquals(-2, event.compareTo(event2));

		assertEquals(event.getName(), event2.getName());
		assertEquals(event.getDescription(), event2.getDescription());
		assertEquals(event.getColor(), event2.getColor());
		assertEquals(event.getTimelineId(), event2.getTimelineId());
	}

	@Test
	public void testTimeline() {
		Timeline timeline = new Timeline("title");
		timeline.setTimeBounds(LocalDate.parse("2015-05-03").toString(), LocalDate.parse("2015-05-04").toString());
		timeline.setShowVal("showVal");
		timeline.id = 1;

		Timeline timeline2 = new Timeline("title");
		timeline2.setTimeBounds(LocalDate.parse("2015-05-05").toString(), LocalDate.parse("2015-05-06").toString());
		timeline2.setShowVal("showVal");
		timeline2.id = 2;

		assertEquals(-1, timeline.compareTo(timeline2));

		assertEquals(timeline.getTitle(), timeline2.getTitle());
		assertEquals(timeline.getShowVal(), timeline2.getShowVal());
		assertEquals(-2, timeline.getStartDate().compareTo(timeline2.getStartDate()));
		assertEquals(-2, timeline.getEndDate().compareTo(timeline2.getEndDate()));
	}
}
