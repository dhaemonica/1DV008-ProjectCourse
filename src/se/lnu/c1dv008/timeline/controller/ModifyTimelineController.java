package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.PopOver;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

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

    public Timeline time;

    public PopOver popOver;

    @FXML
    private void updateTimeline() {

        if (!modifyTimelineTitle.getText().isEmpty() || modifyTimelineStartDate.getValue() != null ||
                modifyTimelineEndDate.getValue() != null) {
            time.setTimeBounds(modifyTimelineStartDate.getValue().toString(),
                    modifyTimelineEndDate.getValue().toString());
            time.setTitle(modifyTimelineTitle.getText());
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

}