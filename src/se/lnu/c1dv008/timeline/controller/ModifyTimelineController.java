package se.lnu.c1dv008.timeline.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by otto on 2015-05-01.
 */
public class ModifyTimelineController {

    @FXML
    public TextField modifyTimelineTitle;

    @FXML
    public DatePicker modifyTimelineStartDate;

    @FXML
    public DatePicker modifyTimelineEndDate;

    @FXML
    public Button modifyTimelineUpdateBtn;

    @FXML
    public Button modifyTimelineDeleteBtn;

    @FXML
    private ChoiceBox modifyTimelineChoiceBox;

    public Timeline time;

    public PopOver popOver;

    @FXML
    void initialize() {
        modifyTimelineChoiceBox.getItems().addAll("Days", "Months", "Years");
    }

    @FXML
    private void updateTimeline() {

        if (!modifyTimelineTitle.getText().isEmpty() || modifyTimelineStartDate.getValue() != null ||
                modifyTimelineEndDate.getValue() != null) {
            time.setTimeBounds(modifyTimelineStartDate.getValue().toString(),
                    modifyTimelineEndDate.getValue().toString());
            time.setTitle(modifyTimelineTitle.getText());
            time.setShowVal(modifyTimelineChoiceBox.getSelectionModel().getSelectedItem().toString());
            DB.timelines().update(time);
            TimelineController.timeLineController.draw();
            popOver.hide();
        }
    }
    @FXML
    private void deleteTimeline() {
        List<Event> events = DB.events().findAll();
        for (Event e : events) {
            if (e.getTimelineId() == time.getId()) {
                DB.events().delete(e);
            }
        }
        DB.timelines().delete(time);
        TimelineController.timeLineController.draw();
        popOver.hide();
    }

    public ChoiceBox getModifyTimelineChoiceBox() {
        return modifyTimelineChoiceBox;
    }

    public void setModifyTimelineChoiceBox(ChoiceBox modifyTimelineChoiceBox) {
        this.modifyTimelineChoiceBox = modifyTimelineChoiceBox;
    }

    final Callback<DatePicker, DateCell> dayCellFactory =
            new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isBefore(
                                    modifyTimelineStartDate.getValue().plusDays(1))
                                    ) {
                                setDisable(true);
                                setStyle("-fx-background-color: #767676;");
                            }
                        }
                    };
                }
            };

    final Callback<DatePicker, DateCell> dayCellFactory2 =
            new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isAfter(
                                    modifyTimelineEndDate.getValue())
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
        modifyTimelineEndDate.setDayCellFactory(dayCellFactory);

    }

    public void setStartdate() throws IOException {
        modifyTimelineEndDate.setDayCellFactory(dayCellFactory);

    }

    @FXML
    private void setEnddate(ActionEvent event) throws IOException {
        modifyTimelineStartDate.setDayCellFactory(dayCellFactory2);

    }

    public void setEnddate() throws IOException {
        modifyTimelineStartDate.setDayCellFactory(dayCellFactory2);
    }
}