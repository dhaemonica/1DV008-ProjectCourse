package se.lnu.c1dv008.timeline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import se.lnu.c1dv008.timeline.view.CalendarView;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(CalendarView.class.getResource("Timeline.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(CalendarView.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			root.maxHeight(visualBounds.getHeight());
			root.maxWidth(visualBounds.getWidth());
			primaryStage.setTitle("TimeLine Manager");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
