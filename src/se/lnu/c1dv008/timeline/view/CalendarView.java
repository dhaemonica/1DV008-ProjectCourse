package se.lnu.c1dv008.timeline.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.controller.*;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.model.Timeline;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarView {

	public static TimelineController timelineController;
	private PopOver popOver;

	private GridPane calendarView;

	// Method that creates the gridpane for the timeline, arguments should speak for themselfs
	public VBox createView(Timeline timeline, String title, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		calendarView  = new GridPane();
        // Set max width to maximum so it can expand with the window
        calendarView.maxWidth(Double.MAX_VALUE);
        calendarView.setPrefWidth(1190);

		// Localdates set to start and endtime of the timeline
		LocalDate start = LocalDate.of(startYear, startMonth, startDay);
		LocalDate end = LocalDate.of(endYear, endMonth, endDay);

        ColumnConstraints column = new ColumnConstraints(80, 80, Double.MAX_VALUE);
        column.setPercentWidth(100);



		if (timeline.getShowVal().equals("Days")) {

			// Create the rows and columns for the gridpane
			for (LocalDate date = start; ! date.isAfter(end); date = date.plusDays(1)) {
                calendarView.getColumnConstraints().add(column);
			}
			// Add the headers for the gridpane so this adds the day and month at the top of the gridpane
			DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");
			int pos = 0;
			for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {

				Label label = new Label(date.format(dayFormatter));
				label.setPadding(new Insets(1));
				label.setTextAlignment(TextAlignment.CENTER);
				GridPane.setHalignment(label, HPos.CENTER);
				calendarView.add(label, pos, 0);
				pos++;
			}
		}
		else if (timeline.getShowVal().equals("Months")) {

			// Create the rows and columns for the gridpane
			for (LocalDate date = start; ! date.isAfter(end); date = date.plusMonths(1)) {
				calendarView.getColumnConstraints().add(column);
			}
			// Add the headers for the gridpane so this adds the day and month at the top of the gridpane
			DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy\nMMM");
			int pos = 0;
			for (LocalDate date = start; !date.isAfter(end); date = date.plusMonths(1)) {

				Label label = new Label(date.format(dayFormatter));
				label.setPadding(new Insets(1));
				label.setTextAlignment(TextAlignment.CENTER);
				GridPane.setHalignment(label, HPos.CENTER);
				calendarView.add(label, pos, 0);
				pos++;
			}
		}
		else if (timeline.getShowVal().equals("Years")) {

            // Create the rows and columns for the gridpane
            for (LocalDate date = start; !date.isAfter(end); date = date.plusYears(1)) {
                calendarView.getColumnConstraints().add(column);
            }

            // Add the headers for the gridpane so this adds the day and month at the top of the gridpane
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy");
            int pos = 0;
            for (LocalDate date = start; !date.isAfter(end); date = date.plusYears(1)) {

                Label label = new Label(date.format(dayFormatter));
                label.setPadding(new Insets(1));
                label.setTextAlignment(TextAlignment.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                calendarView.add(label, pos, 0);
                pos++;
            }
        }

		// Create scrollpane to add the gridpane into and the title label
		ScrollPane scroller = new ScrollPane(calendarView);
		scroller.maxWidth(Double.MAX_VALUE);
        //scroller.prefWidth(1185);
        StackPane displayInfo = new StackPane();
        displayInfo.setAlignment(Pos.CENTER);
		VBox vContain = new VBox();
		Label titleLabel = new Label(title);
        Label startDateLabel = new Label(timeline.getStartDate());
        Label endDateLabel = new Label(timeline.getEndDate());
        displayInfo.getChildren().addAll(startDateLabel, titleLabel, endDateLabel);
		displayInfo.setAlignment(titleLabel, Pos.CENTER);
		displayInfo.setAlignment(startDateLabel, Pos.CENTER_LEFT);
		displayInfo.setAlignment(endDateLabel, Pos.CENTER_RIGHT);

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
					modifyTimelineController.getModifyTimelineChoiceBox().setValue(timeline.getShowVal());
					modifyTimelineController.setStartdate();
					modifyTimelineController.setEnddate();
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
                addEventController.setErrorTextVisible(false);
                addEventController.setStartdate();
                addEventController.setEnddate();

                Stage stage = new Stage();
                addEventController.addEventStage = stage;
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Add new event");
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

        Button addEventWithoutDurationBtn = new Button("Add Event Without Duration");


        // Method to add new event, loads the addevent view and sets some starting values into its controller and
        // shows it in a new stage
        addEventWithoutDurationBtn.setOnMouseClicked(event -> {

            FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("AddEventWithoutDurationView.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
                AddEventWithoutDurationController addEventWithoutDurationController = fxmlLoader.getController();
                addEventWithoutDurationController.timeline = timeline;
                addEventWithoutDurationController.setErrorTextVisible(false);
                addEventWithoutDurationController.setStartdate();

                Stage stage = new Stage();
                addEventWithoutDurationController.addEventStage = stage;
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Add new event");
                stage.initStyle(StageStyle.UNDECORATED);
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
        StackPane stackPaneForBtn = new StackPane();
        HBox hboxForButtons = new HBox();
        hboxForButtons.getChildren().addAll(addEventBtn, addEventWithoutDurationBtn);
        hboxForButtons.setMargin(addEventWithoutDurationBtn, new Insets(0, 0, 0, 5));
        hboxForButtons.setAlignment(Pos.CENTER_RIGHT);
        stackPaneForBtn.getChildren().addAll(hboxForButtons);
        stackPaneForBtn.setAlignment(hboxForButtons, Pos.CENTER_RIGHT);

        stackPaneForBtn.setPadding(new Insets(5, 0, 0, 0));
		vContain.getChildren().addAll(scroller, stackPaneForBtn);
		vContain.setPadding(new Insets(15, 5, 15, 5));
		return vContain;

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

    public void eventWithoutDuration(EventWithoutDuration eventWithoutDuration, int cIndex, int rIndex){

        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("EventNoDurationView.fxml"));
        try {
            calendarView.add(fxmlLoader.load(), cIndex, rIndex, 1, 1);
            EventNoDurationController eventNoDurationController = fxmlLoader.getController();
            eventNoDurationController.eventBox.setStyle("-fx-background-color: " + eventWithoutDuration.getColor() + ";" +
                    "-fx-border-color: black;");
            eventNoDurationController.eventBox.setMaxWidth(Double.MAX_VALUE);
            eventNoDurationController.eventName.setText(eventWithoutDuration.getName());
            eventNoDurationController.eventWithoutDuration = eventWithoutDuration;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
		
}






