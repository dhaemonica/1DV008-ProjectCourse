package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.model.Timeline;

import java.io.IOException;
import java.time.LocalDate;


/**
 * Created by otto on 2015-04-26.
 */
public class AddEventWithoutDurationController {


    @FXML
    private TextArea addEventDescription;

    @FXML
    private DatePicker addEventStartDate;

    @FXML
    private Button addEventCreate;

    @FXML
    private Button addEventCancel;

    @FXML
    private TextField addEventTitle;

    @FXML
    private ColorPicker addEventColorPicker;

    @FXML
    private Label errorText;

    private double X;

    private double Y;

    public Timeline timeline;

    public Stage addEventStage;



    @FXML
    private void onCreate() {

        Timeline time = DB.timelines().findById(timeline.getId());

        if (!addEventTitle.getText().isEmpty() && addEventStartDate.getValue() != null) {
            setErrorTextVisible(false);
            EventWithoutDuration eventWithoutDuration = new EventWithoutDuration(addEventTitle.getText(), addEventDescription.getText(),
                    addEventStartDate.getValue().toString(), toRGBCode(addEventColorPicker.getValue()), time.getId());
            DB.eventsWithoutDuration().save(eventWithoutDuration);
            TimelineController.timeLineController.draw();
            Stage stage = (Stage) addEventCreate.getScene().getWindow();
            stage.close();

        }
        else {
            setErrorTextVisible(true);
        }
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) addEventCancel.getScene().getWindow();
        stage.close();
    }

    public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }



    private final Callback<DatePicker, DateCell> dayCellFactory =
            new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isBefore(
                                    LocalDate.parse(timeline.getStartDate()))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                            if (item.isAfter(
                                    LocalDate.parse(timeline.getEndDate())))
                            {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                        }
                    };
                }
            };


    public void setErrorTextVisible(boolean visible) {
        errorText.setVisible(visible);
    }

    @FXML
    protected void onPressed(MouseEvent event) {
        X = addEventStage.getX() - event.getScreenX();
        Y = addEventStage.getY() - event.getScreenY();
    }


    @FXML
    protected void onDragged(MouseEvent event) {
        addEventStage.setX(event.getScreenX() + X);
        addEventStage.setY(event.getScreenY() + Y);
    }

    public void setStartdate() throws IOException {
        addEventStartDate.setDayCellFactory(dayCellFactory);

    }
}