package se.lnu.c1dv008.test;


import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.model.Timeline;

public class IntegrationTest extends Assert {

	@Test
	public void testEventDAO() {
		DB.events().deleteAll();
		
		assertEquals(0, DB.events().findAll().size());

		Event event = new Event("name", "description", LocalDate.parse("2015-05-03").toString(), LocalDate.parse("2015-05-04").toString(), "#FFFFFF", 1);
		DB.events().save(event);

		assertEquals(1, DB.events().findAll().size());

		Event event2 = DB.events().findById(event.id);

		assertEquals(event2, event);
		assertEquals("name", event2.getName());
		assertEquals("description", event2.getDescription());
		assertEquals("#FFFFFF", event2.getColor());
		assertEquals(LocalDate.parse("2015-05-03").toString(), event2.getStartTime());
		assertEquals(LocalDate.parse("2015-05-04").toString(), event2.getEndTime());
		assertEquals(1, event2.getTimelineId());

		event.setName("otherName");

		DB.events().save(event);
		event2 = DB.events().findById(event.id);

		assertEquals(event.getName(), event2.getName());

		DB.events().delete(event);

		assertEquals(0, DB.events().findAll().size());
	}

	@Test
	public void testEventWithoutDurationDAO() {
		DB.eventsWithoutDuration().deleteAll();

		assertEquals(0, DB.eventsWithoutDuration().findAll().size());

		EventWithoutDuration event = new EventWithoutDuration("name", "description", LocalDate.parse("2015-05-03").toString(), "#FFFFFF", 1);
		DB.eventsWithoutDuration().save(event);

		assertEquals(1, DB.eventsWithoutDuration().findAll().size());

		EventWithoutDuration event2 = DB.eventsWithoutDuration().findById(event.id);

		assertEquals(event2, event);
		assertEquals("name", event2.getName());
		assertEquals("description", event2.getDescription());
		assertEquals("#FFFFFF", event2.getColor());
		assertEquals(LocalDate.parse("2015-05-03").toString(), event2.getStartTime());
		assertEquals(1, event2.getTimelineId());

		event.setName("otherName");

		DB.eventsWithoutDuration().save(event);
		event2 = DB.eventsWithoutDuration().findById(event.id);

		assertEquals(event.getName(), event2.getName());

		DB.eventsWithoutDuration().delete(event);

		assertEquals(0, DB.eventsWithoutDuration().findAll().size());
	}

	@Test
	public void testTimelineDAO() {
		DB.timelines().deleteAll();

		assertEquals(0, DB.timelines().findAll().size());

		Timeline timeline = new Timeline("name");
		timeline.setTimeBounds(LocalDate.parse("2015-05-03").toString(), LocalDate.parse("2015-05-04").toString());
		timeline.setTitle("title");
		timeline.setShowVal("showVal");

		DB.timelines().save(timeline);

		assertEquals(1, DB.timelines().findAll().size());

		Timeline timeline2 = DB.timelines().findById(timeline.id);

		assertEquals(timeline2, timeline);
		assertEquals("title", timeline2.getTitle());
		assertEquals("showVal", timeline2.getShowVal());
		assertEquals(LocalDate.parse("2015-05-03").toString(), timeline2.getStartDate().toString());
		assertEquals(LocalDate.parse("2015-05-04").toString(), timeline2.getEndDate().toString());

		timeline.setTitle("otherName");

		DB.timelines().save(timeline);
		timeline2 = DB.timelines().findById(timeline.id);

		assertEquals(timeline.getTitle(), timeline2.getTitle());

		DB.timelines().delete(timeline);

		assertEquals(0, DB.timelines().findAll().size());
	}
}
