package se.lnu.c1dv008.test;

import javafx.scene.paint.Color;

import org.junit.Assert;
import org.junit.Test;

import se.lnu.c1dv008.timeline.controller.AddEventController;
import se.lnu.c1dv008.timeline.controller.AddEventWithoutDurationController;
import se.lnu.c1dv008.timeline.controller.PopOverController;
import se.lnu.c1dv008.timeline.controller.PopOverForNoDurationEventsController;

public class ColorTest extends Assert {

	@Test
	public void testColor() {
		String hex = AddEventWithoutDurationController.toRGBCode(new Color(1, 0, 0, 1));
		assertEquals("#FF0000", hex);

		hex = AddEventController.toRGBCode(new Color(0, 1, 0, 1));
		assertEquals("#00FF00", hex);

		hex = PopOverController.toRGBCode(new Color(0, 0, 1, 1));
		assertEquals("#0000FF", hex);

		hex = PopOverForNoDurationEventsController.toRGBCode(new Color(1, 0, 0, 0.5));
		assertEquals("#FF0000", hex);
	}
}
