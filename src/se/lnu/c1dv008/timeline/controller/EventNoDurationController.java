package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.view.CalendarView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by otto on 2015-05-15.
 */
public class EventNoDurationController {

    @FXML
    public VBox eventBox;

    @FXML
    public Label eventName;

    public EventWithoutDuration eventWithoutDuration;

    public PopOver popOver;

    @FXML
    public void popupOnLeftClick() {



        if (popOver == null || !popOver.isShowing()) {
            popOver = new PopOver();

            FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("EventNoDurationForPopOver.fxml"));

            try {
                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
                popOver.setContentNode(fxmlLoader.load());
                PopOverForNoDurationEventsController popOverController = fxmlLoader.getController();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                popOverController.popOverEventTitle.setText(eventWithoutDuration.getName());
                popOverController.popOverEventDescription.setText(eventWithoutDuration.getDescription());
                popOverController.popOverEventStartDate.setValue(LocalDate.parse(eventWithoutDuration.getStartTime(), dtf));
                popOverController.popOverEventColor.setValue(Color.web(eventWithoutDuration.getColor()));
                popOverController.eventWithoutDuration = eventWithoutDuration;
                popOverController.setStartdate();
                popOverController.popOver = popOver;


                popOver.show(eventBox);

                final PopOver finalPopOver = popOver;
                popOver.getContentNode().setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        finalPopOver.hide();
                    }
                });


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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

