package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;


/**
 * Created by otto on 2015-04-26.
 */
public class AddEventController {

    @FXML
    private TextArea addEventDescription;

    @FXML
    private DatePicker addEventStartDate;

    @FXML
    private DatePicker addEventEndDate;

    @FXML
    private Button addEventCreate;

    @FXML
    private Button addEventCancel;

    @FXML
    private TextField addEventTitle;

    @FXML
    private ColorPicker addEventColorPicker;

    public Timeline timeline;

    public TimelineController timelineController;


    @FXML
    private void onCreate() {

        Timeline time = DB.timelines().findById(timeline.getId());

        if (!addEventTitle.getText().isEmpty() || addEventStartDate.getValue() != null ||
                addEventEndDate.getValue() != null || !addEventDescription.getText().isEmpty()) {
            Event event = new Event(addEventTitle.getText(), addEventDescription.getText(),
                    addEventStartDate.getValue().toString(), addEventEndDate.getValue().toString(),
                    toRGBCode(addEventColorPicker.getValue()), time.getId());
            DB.events().save(event);
            TimelineController.timeLineController.draw();
            Stage stage = (Stage) addEventCreate.getScene().getWindow();
            stage.close();

        }
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) addEventCancel.getScene().getWindow();
        stage.close();
    }

    public static String toRGBCode(Color color) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
}
