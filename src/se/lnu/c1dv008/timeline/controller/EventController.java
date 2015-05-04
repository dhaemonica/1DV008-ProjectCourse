package se.lnu.c1dv008.timeline.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import org.controlsfx.control.PopOver;

import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.view.CalendarView;

/**
 * Created by otto on 2015-04-25.
 */
public class EventController {

    private PopOver popOver;

    @FXML
    public VBox eventBox;

    @FXML
    public Label eventName;

    public static Event event;


    @FXML
    public void popupOnLeftClick() {

        VBox box = new VBox();
        popOver = new PopOver(box);
        popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("EventViewForPopOver.fxml"));
        try {
            Event getEvent = DB.events().findById(event.getId());
            box.getChildren().add(fxmlLoader.load());
            PopOverController popOverController = fxmlLoader.getController();
            popOverController.event = getEvent;
            popOverController.popOver = popOver;
            popOverController.popOverEventColor.setValue(Color.web(getEvent.getColor()));
            popOverController.popOverEventEndDate.setValue(LocalDate.parse(getEvent.getEndTime(), dtf));
            popOverController.popOverEventStartDate.setValue(LocalDate.parse(getEvent.getStartTime(), dtf));
            popOverController.popOverEventDescription.setText(getEvent.getDescription());
            popOverController.popOverEventTitle.setText(getEvent.getName());
            popOver.show(eventBox);
            popOver.getContentNode().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    popOver.hide();
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
            popOver.focusedProperty().addListener((ov, t, t1) -> {

                if (t1 == false) {
                    popOver.hide();
                }
            });
            */

    }

    public Label getName() {
        return eventName;
    }


}
