package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;
import se.lnu.c1dv008.timeline.view.CalendarView;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {


    @FXML
    private MenuItem newTimeline;

    @FXML
    private  VBox vboxForGridpane;

    @FXML
    private MenuItem timelineHelp;

    @FXML
    private MenuBar mainMenuBar;

    public Stage mainStage;

    private double X;

    private double Y;


    public static TimelineController timeLineController;



    @FXML
    void newTimelineCreate() {
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("NewTimeline.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            NewTimelineController newTimelineController = fxmlLoader.getController();
            newTimelineController.setErrorTextVisible(false);
            newTimelineController.getTimelineChoiceBox().setValue("Days");

            CalendarView.timelineController = this;
            Stage stage = new Stage();
            newTimelineController.newTimelineStage = stage;
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Create new timeline");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            int counter = 1;
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
                    if (time.getShowVal().equals("Days")) {

                        cv.event(event, (int) ChronoUnit.DAYS.between(tlstartDate, eventStartDate), counter,
                                (int) ChronoUnit.DAYS.between(eventStartDate, eventEndDate) + 1);
                        counter++;
                    }
                    else if (time.getShowVal().equals("Months")) {
                        int diffYearEvent = eventEndDate.getYear()-eventStartDate.getYear();
                        int diffMonthEvent = diffYearEvent*12+eventEndDate.getMonthValue()-eventStartDate.getMonthValue();
                        int diffYearTimeline = eventStartDate.getYear()-tlstartDate.getYear();
                        int diffMonthTimeline = diffYearTimeline*12+eventStartDate.getMonthValue()-tlstartDate.getMonthValue();

                        cv.event(event, diffMonthTimeline, counter,diffMonthEvent+1);
                        counter++;
                    }
                    else if (time.getShowVal().equals("Years")) {
                        int diffYearEvent = eventEndDate.getYear()-eventStartDate.getYear();
                        int diffYearTimeline = eventStartDate.getYear()-tlstartDate.getYear();

                        cv.event(event, diffYearTimeline, counter,diffYearEvent+1);
                        counter++;
                    }

                }
            }
        }
    }

    @FXML
    protected void onMenuPressed(MouseEvent event) {
      X = mainStage.getX() - event.getScreenX();
      Y = mainStage.getY() - event.getScreenY();
    }


    @FXML
    protected void onMenuDragged(MouseEvent event) {
        mainStage.setX(event.getScreenX() + X);
        mainStage.setY(event.getScreenY() + Y);
    }

}







