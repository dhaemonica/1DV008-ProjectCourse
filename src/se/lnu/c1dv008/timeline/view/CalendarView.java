package se.lnu.c1dv008.timeline.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.lnu.c1dv008.timeline.controller.AddEventController;
import se.lnu.c1dv008.timeline.controller.EventController;
import se.lnu.c1dv008.timeline.controller.TimelineController;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarView {

	private final LocalTime firstSlotStart = LocalTime.of(0, 0);
	private final Duration slotLength = Duration.ofMinutes(60);
	private final LocalTime lastSlotStart = LocalTime.of(23, 59);
	public static TimelineController timelineController;


	private final List<TimeSlot> timeSlots = new ArrayList<>();
	private GridPane calendarView;

	public VBox createView(Timeline timeline, String title, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		calendarView  = new GridPane();

		LocalDate start = LocalDate.of(startYear, startMonth, startDay);
		LocalDate end = LocalDate.of(endYear, endMonth, endDay);
		LocalDate today = LocalDate.now();

		int rowIndex = 1;
		for (LocalDate date = start; ! date.isAfter(end); date = date.plusDays(1)) {
			int slotIndex = 1 ;

			for (LocalDateTime startTime = date.atTime(firstSlotStart);
					! startTime.isAfter(date.atTime(lastSlotStart));
					startTime = startTime.plus(slotLength)) {


				TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
				timeSlots.add(timeSlot);



				calendarView.add(timeSlot.getView(), rowIndex, slotIndex);
				slotIndex++ ;
			}
			rowIndex++;
		}

		//headers:
		

		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");
		int pos = 1;
		for (LocalDate date = start; ! date.isAfter(end); date = date.plusDays(1)) {

			Label label = new Label(date.format(dayFormatter));
			label.setPadding(new Insets(1));
			label.setTextAlignment(TextAlignment.CENTER);
			GridPane.setHalignment(label, HPos.CENTER);
			calendarView.add(label, pos, 0);
			pos++;
		}

//		int slotIndex = 1 ;
//		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//		for (LocalDateTime startTime = today.atTime(firstSlotStart);
//				! startTime.isAfter(today.atTime(lastSlotStart));
//				startTime = startTime.plus(slotLength)) {
//			Label label = new Label(startTime.format(timeFormatter));
//			label.setPadding(new Insets(2));
//			label.setTextAlignment(TextAlignment.RIGHT);
//			GridPane.setHalignment(label, HPos.RIGHT);
//			calendarView.add(label, 0, slotIndex);
//			slotIndex++;
//			
//		}

		calendarView.maxWidth(Double.MAX_VALUE);
		ScrollPane scroller = new ScrollPane(calendarView);
		scroller.maxWidth(Double.MAX_VALUE);
		VBox vContain = new VBox();
		Label titleLabel = new Label(title);
		titleLabel.fontProperty().setValue(new Font(20));
		vContain.getChildren().add(titleLabel);
		vContain.setAlignment(Pos.TOP_CENTER);
		Button addEventBtn = new Button("Add Event");

		addEventBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("AddEventView.fxml"));
				Parent root = null;
				try {
					root = fxmlLoader.load();
					AddEventController addEventController = fxmlLoader.getController();
					addEventController.timeline = timeline;
					addEventController.timelineController = timelineController;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setOpacity(1);
				stage.setTitle("Add new event");
				stage.setScene(new Scene(root));
				stage.show();
			}
		});





		scroller.setMinHeight(300);
        scroller.setPrefHeight(300);
		vContain.getChildren().addAll(scroller, addEventBtn);
		vContain.setPadding(new Insets(15, 5, 15, 5));
		return vContain;

	}


	public static class TimeSlot {

		private final LocalDateTime start ;
		private final Duration duration ;
		private final Region view ;


		public TimeSlot(LocalDateTime start, Duration duration) {
			this.start = start ;
			this.duration = duration ;

			view = new Region();
			view.setMinSize(80, 20);
			view.getStyleClass().add("time-slot");


		}


		public LocalDateTime getStart() {
			return start ;
		}

		public LocalTime getTime() {
			return start.toLocalTime() ;
		}

		public DayOfWeek getDayOfWeek() {
			return start.getDayOfWeek() ;
		}

		public Duration getDuration() {
			return duration ;
		}

		public Node getView() {
			return view;
		}

	}



	public void event(Event event, int cIndex, int rIndex, int cSpan){

			FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("EventView.fxml"));
			try {
                Event getEvent = DB.events().findById(event.getId());
                calendarView.add(fxmlLoader.load(), cIndex, rIndex, cSpan, 1);
				EventController eventController = fxmlLoader.getController();
				eventController.event = getEvent;
				eventController.eventBox.setStyle("-fx-background-color: " + event.getColor() + ";");
                eventController.eventBox.setMaxWidth(Double.MAX_VALUE);
				eventController.eventName.setText(event.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
}






