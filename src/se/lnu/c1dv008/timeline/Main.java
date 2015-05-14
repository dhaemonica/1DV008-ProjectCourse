package se.lnu.c1dv008.timeline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import se.lnu.c1dv008.timeline.controller.TimelineController;
import se.lnu.c1dv008.timeline.view.CalendarView;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		Parent root = null;
		FXMLLoader loader = new FXMLLoader(CalendarView.class.getResource("Timeline.fxml"));
		try {

			root = loader.load();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			TimelineController timelineController = loader.getController();
			timelineController.mainStage = primaryStage;
			Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			root.maxHeight(visualBounds.getHeight());
			root.maxWidth(visualBounds.getWidth());
			primaryStage.setTitle("TimeLine Manager");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
