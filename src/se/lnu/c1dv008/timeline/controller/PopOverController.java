package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import org.controlsfx.control.PopOver;

import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;


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
    public Button popOverEventUpdate;

    @FXML
    public Button popOverEventDelete;

    @FXML
    public ColorPicker popOverEventColor;

    public Event event;

    public TimelineController timelineController;

    public PopOver popOver;

    @FXML
    private void onUpdateClick() {


        Event getEvent = DB.events().findById(event.getId());
        if (!popOverEventTitle.getText().isEmpty() || popOverEventStartDate.getValue() != null ||
                popOverEventEndDate.getValue() != null || !popOverEventDescription.getText().isEmpty()) {
            Event event = new Event(popOverEventTitle.getText(), popOverEventDescription.getText(),
                    popOverEventStartDate.getValue().toString(), popOverEventEndDate.getValue().toString(),
            toRGBCode(popOverEventColor.getValue()), getEvent.getTimelineId());
            DB.events().update(getEvent);
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
}
