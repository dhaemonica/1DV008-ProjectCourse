package se.lnu.c1dv008.timeline.view;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class CalendarView {

	private final LocalTime firstSlotStart = LocalTime.of(0, 0);
	private final Duration slotLength = Duration.ofMinutes(30);
	private final LocalTime lastSlotStart = LocalTime.of(23, 59);

	

	private final List<TimeSlot> timeSlots = new ArrayList<>();
	private GridPane calendarView;

	public VBox createView(String title, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
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
		vContain.getChildren().add(scroller);
		vContain.setPadding(new Insets(10, 5, 10, 5));
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
	
	
		
		public void event(String name, int cIndex, int rIndex, int cSpan, int rSpan){
			
			VBox vb = new VBox();
			vb.setStyle("-fx-background-color: lightblue;");
			Label lb = new Label(name);
			lb.setFont(new Font(12));
			vb.getChildren().add(lb);
			vb.setPrefWidth(40);
			VBox vb2 = new VBox();
			vb2.setPrefWidth(40);
			vb2.getChildren().add(vb);
			VBox.setMargin(vb, new Insets(2, 2, 2, 2));
			//vb2.setPadding(new Insets(5, 5, 5, 5));
			calendarView.add(vb, cIndex, rIndex, cSpan, rSpan);
			
		}
		
	}






