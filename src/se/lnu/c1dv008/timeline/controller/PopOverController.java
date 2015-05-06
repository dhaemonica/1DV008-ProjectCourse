package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
}
