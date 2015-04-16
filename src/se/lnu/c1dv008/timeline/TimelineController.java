package se.lnu.c1dv008.timeline;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.TestString;

public class TimelineController implements Initializable {

	@FXML
	private MenuItem newTimeline;

	@FXML
	private MenuItem openTimeline;

	@FXML
	private MenuItem timelineHelp;

	@FXML
	private TextArea textView;

	@FXML
	void newTimelineCreate(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/model/NewTimeline.fxml"));
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		List<TestString> ts = DB.testStrings().findAll();
		StringBuilder sb = new StringBuilder();
		for ( TestString entity : ts) {
			sb.append(entity.toString()).append("\n");
		}

		textView.setText(sb.toString());
	}

}





