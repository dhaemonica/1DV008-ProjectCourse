package se.lnu.c1dv008.timeline;
	
import se.lnu.c1dv008.timeline.view.CalendarView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(CalendarView.class.getResource("Timeline.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(CalendarView.class.getResource("calendar-view.css").toExternalForm());
			primaryStage.setScene(scene);
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
