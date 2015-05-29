package se.lnu.c1dv008.timeline.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.MasterDetailPane;
import se.lnu.c1dv008.timeline.Main;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.view.CalendarView;

import java.io.IOException;

/**
 * Created by otto on 2015-05-14.
 */
public class TimelineToolbarController {

    @FXML
    private AnchorPane anchorPaneForMainWindow;

    @FXML
    private Button timelineListBtn;

    @FXML
    private ImageView programCloseBtn;

    @FXML
    private ImageView programMaximizeBtn;

    @FXML
    private ImageView programMinimizeBtn;

    private Stage mainStage;

    public MasterDetailPane masterDetailPane;

    private double X;

    private double Y;

    private double standardStageWidth;

    private double standardStageHeight;

    private double standardStageX;

    private double standardStageY;

    private boolean maximized;


    @FXML
    public void initialize() {

        maximized = false;

        setStandardStageWidth(1200);
        setStandardStageHeight(800);

        timelineListBtn.setOnMouseEntered(event -> timelineListBtn.setStyle("-fx-background-color: #606060;"));
        timelineListBtn.setOnMouseExited(event -> timelineListBtn.setStyle("-fx-background-color: #404040;"));

        programCloseBtn.setOnMouseEntered(event -> programCloseBtn.setStyle("-fx-opacity: 0.8;"));
        programCloseBtn.setOnMouseExited(event -> programCloseBtn.setStyle("-fx-opacity: 1.0;"));

        programMaximizeBtn.setOnMouseEntered(event -> programMaximizeBtn.setStyle("-fx-opacity: 0.8;"));
        programMaximizeBtn.setOnMouseExited(event -> programMaximizeBtn.setStyle("-fx-opacity: 1.0;"));

        programMinimizeBtn.setOnMouseEntered(event -> programMinimizeBtn.setStyle("-fx-opacity: 0.8;"));
        programMinimizeBtn.setOnMouseExited(event -> programMinimizeBtn.setStyle("-fx-opacity: 1.0;"));

    }


    @FXML
    private void closeProgram() {
        DB.closeSessionFactory();
        Platform.exit();
    }

    @FXML
    private void onMaximize() {
        if (mainStage.isFullScreen()) {
            mainStage.setFullScreen(false);
        } else {
            mainStage.setFullScreen(true);
        }
    }

    @FXML
    private void onMinimize() {
        if (mainStage.isIconified()) {
            mainStage.setIconified(false);
        } else {
            mainStage.setIconified(true);
        }
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

    @FXML
    private void showDetailPane() {

        if (Main.masterDetailPane.isShowDetailNode()) {
            Main.masterDetailPane.setShowDetailNode(false);
        }
        else {
            Main.masterDetailPane.setShowDetailNode(true);
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

    public double getStandardStageWidth() {
        return standardStageWidth;
    }

    public void setStandardStageWidth(double standardStageWidth) {
        this.standardStageWidth = standardStageWidth;
    }

    public double getStandardStageHeight() {
        return standardStageHeight;
    }

    public void setStandardStageHeight(double standardStageHeight) {
        this.standardStageHeight = standardStageHeight;
    }
}
