package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by otto on 2015-05-15.
 */
public class PopOverForNoDurationEventsController {

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

    public EventWithoutDuration eventWithoutDuration;



    @FXML
    private void onUpdateClick() {


        if (!popOverEventTitle.getText().isEmpty() || popOverEventStartDate.getValue() != null
                || !popOverEventDescription.getText().isEmpty()) {
            eventWithoutDuration.setName(popOverEventTitle.getText());
            eventWithoutDuration.setDescription(popOverEventDescription.getText());
            eventWithoutDuration.setStartTime(popOverEventStartDate.getValue().toString());
            eventWithoutDuration.setColor(toRGBCode(popOverEventColor.getValue()));
            DB.eventsWithoutDuration().update(eventWithoutDuration);
            TimelineController.timeLineController.draw();
            popOver.hide();
        }


    }

    @FXML
    private void onDeleteClick() {
        DB.eventsWithoutDuration().delete(eventWithoutDuration);
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
                                    LocalDate.parse(DB.timelines().findById(eventWithoutDuration.getTimelineId()).getStartDate()))) {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                            if (item.isAfter(
                                    LocalDate.parse(DB.timelines().findById(eventWithoutDuration.getTimelineId()).getEndDate())))
                            {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                        }
                    };
                }
            };



    public void setStartdate() throws IOException {
        popOverEventStartDate.setDayCellFactory(dayCellFactory);
    }
}

