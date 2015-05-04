package se.lnu.c1dv008.timeline.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;
import se.lnu.c1dv008.timeline.view.CalendarView;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML
    private MenuItem newTimeline;

    @FXML
    private  VBox vboxForGridpane;

    @FXML
    private MenuItem openTimeline;

    @FXML
    private MenuItem timelineHelp;

    @FXML
    private TextArea textView;

    public static TimelineController timeLineController;


    @FXML
    void newTimelineCreate(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("NewTimeline.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            NewTimelineController newTimelineController = fxmlLoader.getController();
            newTimelineController.timelineController = this;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CalendarView.timelineController = this;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Create new timeline");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @Override
    @PostConstruct
    public void initialize(URL location, ResourceBundle resources) {

        timeLineController = this;
        draw();
    }

    public void draw() {

        this.vboxForGridpane.getChildren().clear();
        List<Timeline> timelines = DB.timelines().findAll();
        List<Event> events = DB.events().findAll();

        this.vboxForGridpane.setMaxWidth(Double.MAX_VALUE);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < timelines.size(); i++) {
            CalendarView cv = new CalendarView();
            Timeline time = timelines.get(i);
            LocalDate tlstartDate = LocalDate.parse(time.getStartDate(), dtf);
            LocalDate tlendDate = LocalDate.parse(time.getEndDate(), dtf);
            this.vboxForGridpane.getChildren().add(cv.createView(time, time.getTitle(), tlstartDate.getYear(),
                    tlstartDate.getMonthValue(), tlstartDate.getDayOfMonth(),
                    tlendDate.getYear(), tlendDate.getMonthValue(), tlendDate.getDayOfMonth()));

            for (int j = 1; j < events.size(); j++) {
                Event event = events.get(j);
                if (event.getTimelineId() == time.getId()) {
                    LocalDate eventStartDate = LocalDate.parse(event.getStartTime(), dtf);
                    LocalDate eventEndDate = LocalDate.parse(event.getEndTime(), dtf);
                    cv.event(event, Period.between(tlstartDate, eventStartDate).plusDays(1).getDays(), j,
                            Period.between(eventStartDate, eventEndDate).plusDays(1).getDays());
                }
            }
        }
    }

}







