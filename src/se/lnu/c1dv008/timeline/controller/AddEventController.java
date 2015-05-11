package se.lnu.c1dv008.timeline.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

import java.io.IOException;
import java.time.LocalDate;


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

    @FXML
    private Label errorText;



    @FXML
    private void onCreate() {

        Timeline time = DB.timelines().findById(timeline.getId());

        if (!addEventTitle.getText().isEmpty() && addEventStartDate.getValue() != null &&
                addEventEndDate.getValue() != null) {
            setErrorTextVisible(false);
            Event event = new Event(addEventTitle.getText(), addEventDescription.getText(),
                    addEventStartDate.getValue().toString(), addEventEndDate.getValue().toString(),
                    toRGBCode(addEventColorPicker.getValue()), time.getId());
            DB.events().save(event);
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
                            if (addEventEndDate.getValue() != null) {
                                if (item.isAfter(addEventEndDate.getValue()))
                                {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #767676;");
                                }
                            }
                        }
                    };
                }
            };

    private final Callback<DatePicker, DateCell> dayCellFactory2 =
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
                            if (addEventStartDate.getValue() != null) {
                                if (item.isBefore(addEventStartDate.getValue()))
                                {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #767676;");
                                }
                            }
                            if (item.isAfter(
                                    LocalDate.parse(timeline.getEndDate()))
                                    ) {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                        }
                    };
                }
            };


    @FXML
    private void setStartdate(ActionEvent event) throws IOException {
        addEventStartDate.setDayCellFactory(dayCellFactory);

    }

    public void setStartdate() throws IOException {
        addEventStartDate.setDayCellFactory(dayCellFactory);

    }

    @FXML
    private void setEnddate(ActionEvent event) throws IOException {
        addEventEndDate.setDayCellFactory(dayCellFactory2);
    }

    public void setEnddate() throws IOException {
        addEventEndDate.setDayCellFactory(dayCellFactory2);
    }

    public DatePicker getAddEventStartDate() {
        return addEventStartDate;
    }

    public DatePicker getAddEventEndDate() {
        return addEventEndDate;
    }

    public void setErrorTextVisible(boolean visible) {
        errorText.setVisible(visible);
    }
}
