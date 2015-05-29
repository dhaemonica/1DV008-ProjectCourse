package se.lnu.c1dv008.timeline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.MasterDetailPane;
import se.lnu.c1dv008.timeline.controller.TimelineController;
import se.lnu.c1dv008.timeline.controller.TimelineToolbarController;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.view.CalendarView;


public class Main extends Application {

    public static MasterDetailPane masterDetailPane;

	@Override
	public void start(Stage primaryStage) {

        primaryStage.setFullScreenExitHint("");

        CalendarView.setMainStage(primaryStage);
		AnchorPane root;
		Parent mainDisplay;
        masterDetailPane = new MasterDetailPane();
		masterDetailPane.maxHeight(Double.MAX_VALUE);
		masterDetailPane.maxWidth(Double.MAX_VALUE);
		FXMLLoader loader = new FXMLLoader(CalendarView.class.getResource("Timeline.fxml"));
		FXMLLoader loader2 = new FXMLLoader(CalendarView.class.getResource("TimelineToolBar.fxml"));

		try {

			root = loader2.load();
			mainDisplay = loader.load();
            mainDisplay.maxHeight(Double.MAX_VALUE);
            mainDisplay.maxWidth(Double.MAX_VALUE);
            masterDetailPane.setMasterNode(mainDisplay);
			TimelineToolbarController timelineToolbarController = loader2.getController();
			timelineToolbarController.getAnchorPaneForMainWindow().getChildren().add(masterDetailPane);
			AnchorPane.setRightAnchor(masterDetailPane, 0.0);
			AnchorPane.setLeftAnchor(masterDetailPane, 0.0);
			AnchorPane.setTopAnchor(masterDetailPane, 0.0);
			AnchorPane.setBottomAnchor(masterDetailPane, 0.0);
			timelineToolbarController.setMainStage(primaryStage);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			TimelineController timelineController = loader.getController();
			timelineController.mainStage = primaryStage;
			//Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			//root.maxHeight(visualBounds.getHeight());
			//root.maxWidth(visualBounds.getWidth());
			primaryStage.setTitle("TimeLine Manager");
			if (!System.getProperties().getProperty("os.name").toLowerCase().equals("mac os x")) {
				primaryStage.initStyle(StageStyle.UNDECORATED);
			}
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public void stop() {
        DB.closeSessionFactory();
    }


	
	public static void main(String[] args) {
		launch(args);
	}
}
