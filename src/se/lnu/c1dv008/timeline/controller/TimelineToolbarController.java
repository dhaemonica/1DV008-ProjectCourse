package se.lnu.c1dv008.timeline.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.view.CalendarView;

import java.io.IOException;

/**
 * Created by otto on 2015-05-14.
 */
public class TimelineToolbarController {

    @FXML
    private AnchorPane anchorPaneForMainWindow;

    private Stage mainStage;

    private double X;

    private double Y;


    @FXML
    private void closeProgram() {
        DB.closeSessionFactory();
        Platform.exit();
    }

    @FXML
    protected void onMenuPressed(MouseEvent event) {
        X = mainStage.getX() - event.getScreenX();
        Y = mainStage.getY() - event.getScreenY();
    }


    @FXML
    protected void onMenuDragged(MouseEvent event) {
        mainStage.setX(event.getScreenX() + X);
        mainStage.setY(event.getScreenY() + Y);
    }

    @FXML
    void newTimelineCreate() {
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("NewTimeline.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            NewTimelineController newTimelineController = fxmlLoader.getController();
            newTimelineController.setErrorTextVisible(false);
            newTimelineController.getTimelineChoiceBox().setValue("Days");
            Stage stage = new Stage();
            newTimelineController.newTimelineStage = stage;
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Create new timeline");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  AnchorPane getAnchorPaneForMainWindow() {
        return anchorPaneForMainWindow;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
