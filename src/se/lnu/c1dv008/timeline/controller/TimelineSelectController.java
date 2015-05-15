package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Timeline;
import se.lnu.c1dv008.timeline.view.CalendarView;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by otto on 2015-05-12.
 */
public class TimelineSelectController {

    @FXML
    private VBox vboxForSelectingTimelines;

    @FXML
    private HBox hboxForSelectingTimelinesTitle;

    @FXML
    private Label selectingTimelinesTitle;

    @FXML
    private Button updateSelectedTimelinesBtn;


    private static TreeSet<Timeline> timelinesSelected = new TreeSet<>();


    public static TimelineSelectController timelineSelectController;


    @FXML
    void initialize() {
        drawTimelineList();
        timelineSelectController = this;
    }


    public void drawTimelineList() {

        vboxForSelectingTimelines.getChildren().clear();

        //vboxForSelectingTimelines.setPadding(new Insets(5, 0, 0, 0));
        List<Timeline> timelines = DB.timelines().findAll();

        for (Timeline time : timelines) {
                RadioButton btn = new RadioButton(time.getTitle());
                btn.setFont(Font.font("Ubuntu", 16));
                btn.setUserData(time);
                btn.setPadding(new Insets(15));
                btn.setWrapText(true);
                btn.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        Timeline t = (Timeline) btn.getUserData();
                        timelinesSelected.add(t);
                    } else {
                        Timeline t = (Timeline) btn.getUserData();
                        timelinesSelected.remove(t);
                    }
                });

                if (timelinesSelected.contains(time)) {
                    btn.setSelected(true);
                }
                vboxForSelectingTimelines.getChildren().add(btn);
            }
        }


    public static TreeSet<Timeline> getTimelinesSelected() {
        return timelinesSelected;
    }

    @FXML
    private void onUpdateClicked() {
        TimelineController.timeLineController.draw();
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

    public void addTimelineToTimelinesSelected(Timeline timeline) {
        timelinesSelected.add(timeline);
        drawTimelineList();
    }
}
