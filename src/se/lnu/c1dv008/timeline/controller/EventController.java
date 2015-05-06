package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.view.CalendarView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by otto on 2015-04-25.
 */
public class EventController {

    @FXML
    public VBox eventBox;

    @FXML
    public Label eventName;

    public Event event;

    public PopOver popOver;

    @FXML
    public void popupOnLeftClick() {



        if (popOver == null || !popOver.isShowing()) {
            popOver = new PopOver();

            FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("EventViewForPopOver.fxml"));

            try {
                popOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
                popOver.setContentNode(fxmlLoader.load());
                PopOverController popOverController = fxmlLoader.getController();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                popOverController.popOverEventTitle.setText(event.getName());
                popOverController.popOverEventDescription.setText(event.getDescription());
                popOverController.popOverEventStartDate.setValue(LocalDate.parse(event.getStartTime(), dtf));
                popOverController.popOverEventEndDate.setValue(LocalDate.parse(event.getEndTime(), dtf));
                popOverController.popOverEventColor.setValue(Color.web(event.getColor()));
                popOverController.event = event;
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
