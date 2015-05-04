package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Timeline;


/**
 * Created by otto on 2015-04-26.
 */
public class NewTimelineController {

    @FXML
    private Button createTimelineBtn;

    @FXML
    private Button cancelCreateTimelineBtn;

    @FXML
    private TextField timelineTitle;

    @FXML
    private DatePicker timelineStartDate;

    @FXML
    private DatePicker timelineEndDate;

    @FXML
    private Label errorText;

    public TimelineController timelineController;

    @FXML
    private void onCreateClick() {
        if (!timelineTitle.getText().isEmpty() || timelineStartDate.getValue() != null ||
                timelineEndDate.getValue() != null) {
            Timeline timeline = new Timeline(timelineTitle.getText());
            timeline.setTimeBounds(timelineStartDate.getValue().toString(), timelineEndDate.getValue().toString());
            DB.timelines().save(timeline);
            timelineController.draw();
            Stage stage = (Stage) createTimelineBtn.getScene().getWindow();
            stage.close();



        }
        else {
            errorText.setOpacity(1);

        }
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) cancelCreateTimelineBtn.getScene().getWindow();
        stage.close();
    }
}
