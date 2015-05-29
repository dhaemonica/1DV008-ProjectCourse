package se.lnu.c1dv008.timeline.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.MasterDetailPane;
import se.lnu.c1dv008.timeline.Main;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.AllEvents;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.model.Timeline;
import se.lnu.c1dv008.timeline.view.CalendarView;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TimelineController implements Initializable {


    @FXML
    private MenuItem newTimeline;

    @FXML
    private VBox vboxForGridpane;

    @FXML
    private MenuItem timelineHelp;

    @FXML
    private StackPane mainMenuBar;

    @FXML
    private ImageView programCloseBtn;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private MasterDetailPane timelineMasterDetailPane;

    @FXML
    private Pane paneForOpeningSidePane;


    public MasterDetailPane masterDetailPane;

    public Stage mainStage;


    public static TimelineController timeLineController;


    @FXML
    void newTimelineCreate() {
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("NewTimeline.fxml"));
        Parent root;
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

        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("TimelineSelectView.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            Main.masterDetailPane.setDetailNode(root);
            Main.masterDetailPane.setDetailSide(Side.LEFT);
            Main.masterDetailPane.setDividerPosition(0.17);
            Main.masterDetailPane.setShowDetailNode(false);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw() {

        this.vboxForGridpane.getChildren().clear();
        TreeSet<Timeline> timelines = TimelineSelectController.getTimelinesSelected();
        List<AllEvents> allEvents = new ArrayList<>();
        allEvents.addAll(DB.events().findAll());
        allEvents.addAll(DB.eventsWithoutDuration().findAll());
        Collections.sort(allEvents);

        this.vboxForGridpane.setMaxWidth(Double.MAX_VALUE);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Timeline timeline : timelines) {

            int counter = 1;
            CalendarView cv = new CalendarView();

            LocalDate tlstartDate = LocalDate.parse(timeline.getStartDate(), dtf);
            LocalDate tlendDate = LocalDate.parse(timeline.getEndDate(), dtf);
            this.vboxForGridpane.getChildren().add(cv.createView(timeline, timeline.getTitle(), tlstartDate.getYear(),
                    tlstartDate.getMonthValue(), tlstartDate.getDayOfMonth(),
                    tlendDate.getYear(), tlendDate.getMonthValue(), tlendDate.getDayOfMonth()));

            for (int j = 0; j < allEvents.size(); j++) {

                    AllEvents event = allEvents.get(j);
                    if (event.getTimelineId() == timeline.getId()) {
                        LocalDate eventStartDate = LocalDate.parse(event.getStartTime(), dtf);


                        if (timeline.getShowVal().equals("Days")) {
                            if (event.getEndTime() != null) {
                                LocalDate eventEndDate = LocalDate.parse(event.getEndTime(), dtf);
                                cv.event((Event) event, (int) ChronoUnit.DAYS.between(tlstartDate, eventStartDate), counter,
                                        (int) ChronoUnit.DAYS.between(eventStartDate, eventEndDate) + 1);
                                counter++;
                            } else {
                                cv.eventWithoutDuration((EventWithoutDuration) event,
                                        (int) ChronoUnit.DAYS.between(tlstartDate, eventStartDate), counter);
                                counter++;
                            }
                        } else if (timeline.getShowVal().equals("Months")) {
                            if (event.getEndTime() != null) {
                                LocalDate eventEndDate = LocalDate.parse(event.getEndTime(), dtf);
                                int diffYearEvent = eventEndDate.getYear() - eventStartDate.getYear();
                                int diffMonthEvent = diffYearEvent * 12 + eventEndDate.getMonthValue() - eventStartDate.getMonthValue();
                                int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();
                                int diffMonthTimeline = diffYearTimeline * 12 + eventStartDate.getMonthValue() - tlstartDate.getMonthValue();

                                cv.event((Event) event, diffMonthTimeline, counter, diffMonthEvent + 1);
                                counter++;
                            } else {
                                int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();
                                int diffMonthTimeline = diffYearTimeline * 12 + eventStartDate.getMonthValue() - tlstartDate.getMonthValue();

                                cv.eventWithoutDuration((EventWithoutDuration) event, diffMonthTimeline, counter);
                                counter++;
                            }
                        } else if (timeline.getShowVal().equals("Years")) {
                            if (event.getEndTime() != null) {
                                LocalDate eventEndDate = LocalDate.parse(event.getEndTime(), dtf);
                                int diffYearEvent = eventEndDate.getYear() - eventStartDate.getYear();
                                int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();

                                cv.event((Event) event, diffYearTimeline, counter, diffYearEvent + 1);
                                counter++;
                            }
                                else {
                                int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();

                                cv.eventWithoutDuration((EventWithoutDuration) event, diffYearTimeline, counter);
                                counter++;
                                }
                        }

                    }
            }
        }
    }
}






