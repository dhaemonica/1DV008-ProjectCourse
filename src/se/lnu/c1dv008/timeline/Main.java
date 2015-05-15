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
import se.lnu.c1dv008.timeline.view.CalendarView;


public class Main extends Application {

    public static MasterDetailPane masterDetailPane;

	@Override
	public void start(Stage primaryStage) {

		//Parent root = null;
		AnchorPane root;
		Parent mainDisplay;
        masterDetailPane = new MasterDetailPane();
		FXMLLoader loader = new FXMLLoader(CalendarView.class.getResource("Timeline.fxml"));
		FXMLLoader loader2 = new FXMLLoader(CalendarView.class.getResource("TimelineToolBar.fxml"));
		//FXMLLoader loaderHiddenPane = new FXMLLoader(CalendarView.class.getResource("TimelineSelectView.fxml"));
		try {

			root = loader2.load();
			mainDisplay = loader.load();
            masterDetailPane.setMasterNode(mainDisplay);
			TimelineToolbarController timelineToolbarController = loader2.getController();
			timelineToolbarController.getAnchorPaneForMainWindow().getChildren().add(masterDetailPane);
			timelineToolbarController.setMainStage(primaryStage);
			//TimelineSelectController timelineSelectController = loaderHiddenPane.getController();
			//timelineSelectController.pane = hiddenSidesPane;
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			TimelineController timelineController = loader.getController();
			timelineController.mainStage = primaryStage;
			//Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
			//root.maxHeight(visualBounds.getHeight());
			//root.maxWidth(visualBounds.getWidth());
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
