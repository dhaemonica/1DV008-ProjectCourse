package se.lnu.c1dv008.timeline.view;

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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.controller.AddEventController;
import se.lnu.c1dv008.timeline.controller.EventController;
import se.lnu.c1dv008.timeline.controller.ModifyTimelineController;
import se.lnu.c1dv008.timeline.controller.TimelineController;
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
	private PopOver popOver;


	private final List<TimeSlot> timeSlots = new ArrayList<>();
	private GridPane calendarView;

	// Method that creates the gridpane for the timeline, arguments should speak for themselfs
	public VBox createView(Timeline timeline, String title, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		calendarView  = new GridPane();

		// Localdates set to start and endtime of the timeline
		LocalDate start = LocalDate.of(startYear, startMonth, startDay);
		LocalDate end = LocalDate.of(endYear, endMonth, endDay);

		// Create the rows and columns for the gridpane
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


		
		// Add the headers for the gridpane so this adds the day and month at the top of the gridpane
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
		// Set max width to maximum so it can expand with the window
		calendarView.maxWidth(Double.MAX_VALUE);

		// Create scrollpane to add the gridpane into and the title label
		ScrollPane scroller = new ScrollPane(calendarView);
		scroller.maxWidth(Double.MAX_VALUE);
        HBox displayInfo = new HBox();
        displayInfo.setAlignment(Pos.CENTER);
		VBox vContain = new VBox();
		Label titleLabel = new Label(title);
        Label startDateLabel = new Label(timeline.getStartDate());
        Label endDateLabel = new Label(timeline.getEndDate());
        displayInfo.getChildren().addAll(startDateLabel, titleLabel, endDateLabel);

		// Method to create popover when the timeline label is clicked on and load the fxml file into it
		titleLabel.setOnMouseClicked(event -> {

            if (popOver == null || !popOver.isShowing()) {
                popOver = new PopOver();
                FXMLLoader loader = new FXMLLoader(CalendarView.class.getResource("ModifyTimeline.fxml"));

                try {
                    popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
                    popOver.setContentNode(loader.load());
                    ModifyTimelineController modifyTimelineController = loader.getController();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    modifyTimelineController.modifyTimelineTitle.setText(timeline.getTitle());
                    modifyTimelineController.modifyTimelineStartDate.setValue(LocalDate.parse(timeline.getStartDate(), dtf));
                    modifyTimelineController.modifyTimelineEndDate.setValue(LocalDate.parse(timeline.getEndDate(), dtf));
                    modifyTimelineController.time = timeline;
                    modifyTimelineController.popOver = popOver;

                    popOver.show(titleLabel);

					// If popover is rightclicked hide it again
                    final PopOver finalPopOver = popOver;
                    popOver.getContentNode().setOnMouseClicked(ev -> {
                        if (ev.getButton() == MouseButton.SECONDARY) {
                            finalPopOver.hide();
                        }
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



		titleLabel.fontProperty().setValue(new Font(20));
        startDateLabel.fontProperty().setValue(new Font(20));
        endDateLabel.fontProperty().setValue(new Font(20));
		vContain.getChildren().add(displayInfo);
		vContain.setAlignment(Pos.TOP_CENTER);
		Button addEventBtn = new Button("Add Event");

		// Method to add new event, loads the addevent view and sets some starting values into its controller and
		// shows it in a new stage
		addEventBtn.setOnMouseClicked(event -> {

            FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("AddEventView.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
                AddEventController addEventController = fxmlLoader.getController();
                addEventController.timeline = timeline;
				addEventController.getAddEventStartDate().setDayCellFactory(addEventController.dayCellFactory);
				addEventController.getAddEventEndDate().setDayCellFactory(addEventController.dayCellFactory);
				addEventController.setErrorTextVisible(false);

				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setOpacity(1);
				stage.setTitle("Add new event");
				stage.setScene(new Scene(root));
				stage.show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });




		// Set size of the scrollpane and add it to the vbox and then returns the vbox
		scroller.setMinHeight(300);
        scroller.setPrefHeight(300);
		vContain.getChildren().addAll(scroller, addEventBtn);
		vContain.setPadding(new Insets(15, 5, 15, 5));
		return vContain;

	}

	// Class to create the timeslots into the gridpane
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

		// Some getters and setters
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


	// Method to add events into the timeline, loads the eventview fxml file and sets some css properties
	// and sets starting values into its controller
	public void event(Event event, int cIndex, int rIndex, int cSpan){

			FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("EventView.fxml"));
			try {
                calendarView.add(fxmlLoader.load(), cIndex, rIndex, cSpan, 1);
				EventController eventController = fxmlLoader.getController();
				eventController.eventBox.setStyle("-fx-background-color: " + event.getColor() + ";" +
						"-fx-border-color: black;" + "-fx-background-radius: 10;" +
						"-fx-border-radius: 10;");
                eventController.eventBox.setMaxWidth(Double.MAX_VALUE);
				eventController.eventName.setText(event.getName());
                eventController.event = event;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
}






