package se.lnu.c1dv008.test;

import org.junit.Assert;
import org.junit.Test;

import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;

public class MainTest extends Assert {

	@Test
	public void addEvent() {
		Event event = new Event();
		event.setName("okay");
		DB.events().save(event);

		assert DB.events().findAll().size() == 1;

		if (DB.events().findAll().size() != 0)
			throw new RuntimeException();
	}
}
