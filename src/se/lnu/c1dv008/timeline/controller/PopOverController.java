package se.lnu.c1dv008.timeline.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;

import java.io.IOException;
import java.time.LocalDate;


/**
 * Created by otto on 2015-04-28.
 */
public class PopOverController {

    @FXML
    public TextField popOverEventTitle;

    @FXML
    public DatePicker popOverEventStartDate;

    @FXML
    public DatePicker popOverEventEndDate;

    @FXML
    public TextArea popOverEventDescription;

    @FXML
    public Button popOverUpdateEvent;

    @FXML
    public Button popOverDeleteEvent;

    @FXML
    public ColorPicker popOverEventColor;

    public PopOver popOver;

    public Event event;



    @FXML
    private void onUpdateClick() {


        if (!popOverEventTitle.getText().isEmpty() || popOverEventStartDate.getValue() != null ||
                popOverEventEndDate.getValue() != null || !popOverEventDescription.getText().isEmpty()) {
            event.setName(popOverEventTitle.getText());
            event.setDescription(popOverEventDescription.getText());
            event.setStartTime(popOverEventStartDate.getValue().toString());
            event.setEndTime(popOverEventEndDate.getValue().toString());
            event.setColor(toRGBCode(popOverEventColor.getValue()));
            DB.events().update(event);
            TimelineController.timeLineController.draw();
            popOver.hide();
        }


    }

    @FXML
    private void onDeleteClick() {
        DB.events().delete(event);
        TimelineController.timeLineController.draw();
        popOver.hide();
    }

    public static String toRGBCode(Color color) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    private final Callback<DatePicker, DateCell> dayCellFactory =
            new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isBefore(
                                    LocalDate.parse(DB.timelines().findById(event.getTimelineId()).getStartDate()))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                            if (item.isAfter(
                                    LocalDate.parse(DB.timelines().findById(event.getTimelineId()).getEndDate()))
                                    || item.isAfter(LocalDate.parse(event.getEndTime())))
                            {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
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
                                    LocalDate.parse(DB.timelines().findById(event.getTimelineId()).getStartDate()))
                                    || item.isBefore(LocalDate.parse(event.getStartTime()))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                            if (item.isAfter(
                                    LocalDate.parse(DB.timelines().findById(event.getTimelineId()).getEndDate()))
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
        popOverEventStartDate.setDayCellFactory(dayCellFactory);

    }

    @FXML
    private void setEnddate(ActionEvent event) throws IOException {
        popOverEventEndDate.setDayCellFactory(dayCellFactory2);

    }


    public void setTimelineDates() throws IOException {
        popOverEventStartDate.setDayCellFactory(dayCellFactory);
        popOverEventEndDate.setDayCellFactory(dayCellFactory2);

    }
}
