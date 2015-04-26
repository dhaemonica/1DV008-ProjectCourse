package se.lnu.c1dv008.timeline.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import se.lnu.c1dv008.timeline.view.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TimelineController implements Initializable {

	@FXML
	private MenuItem newTimeline;
	
	@FXML
	private VBox vboxForGridpane;

	@FXML
	private MenuItem openTimeline;

	@FXML
	private MenuItem timelineHelp;

	@FXML
	private TextArea textView;

	@FXML
	void newTimelineCreate(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("NewTimeline.fxml"));
		Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOpacity(1);
		stage.setTitle("Create new timeline");
		stage.setScene(new Scene(root));
		stage.showAndWait();

	}

	@Override @PostConstruct
	public void initialize(URL location, ResourceBundle resources) {
		
		vboxForGridpane.prefWidth(Double.MAX_VALUE);
		CalendarView cv = new CalendarView();
		vboxForGridpane.getChildren().add(cv.createView("Otto's Timeline", 2015, 5, 1, 2015, 5, 31));
		cv.event("Testing!", 2, 2, 7, 1);
		cv.event("Meeting", 7, 4, 4, 1);
		CalendarView cv2 = new CalendarView();
		vboxForGridpane.getChildren().add(cv2.createView("Olof's Timeline", 2014, 1, 1, 2014, 2, 15));
		cv2.event("Gruppmöte", 5, 3, 10, 1);
//		EventService es = new EventService();
//
//		List<TestString> ts = es.findAll();
//		StringBuilder sb = new StringBuilder();
//		for ( TestString entity : ts) {
//			sb.append(entity.toString() + "\n");
//		}
//
//		textView.setText(sb.toString());
	}

}





