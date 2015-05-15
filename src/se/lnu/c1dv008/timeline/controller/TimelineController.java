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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.MasterDetailPane;
import se.lnu.c1dv008.timeline.Main;
import se.lnu.c1dv008.timeline.dao.DB;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;
import se.lnu.c1dv008.timeline.model.Timeline;
import se.lnu.c1dv008.timeline.view.CalendarView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class TimelineController implements Initializable {


    @FXML
    private MenuItem newTimeline;

    @FXML
    private  VBox vboxForGridpane;

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
    //@PostConstruct
    public void initialize(URL location, ResourceBundle resources) {

        timeLineController = this;

        FXMLLoader fxmlLoader = new FXMLLoader(CalendarView.class.getResource("TimelineSelectView.fxml"));
        AnchorPane root;
        try {
            root = fxmlLoader.load();
            TimelineSelectController timelineSelectController = fxmlLoader.getController();
            Main.masterDetailPane.setDetailNode(root);
            Main.masterDetailPane.setDetailSide(Side.LEFT);
            Main.masterDetailPane.setDividerPosition(0.15);
            //Main.masterDetailPane.setAnimated(false);
            vboxForGridpane.setOnMouseClicked(event -> {

                if (Main.masterDetailPane.isShowDetailNode()) {
                    Main.masterDetailPane.setShowDetailNode(false);
                } else {
                    Main.masterDetailPane.setShowDetailNode(true);
                }

            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void draw() {

        this.vboxForGridpane.getChildren().clear();
        TreeSet<Timeline> timelines = TimelineSelectController.getTimelinesSelected();
        List<Event> events = DB.events().findAll();
        List<EventWithoutDuration> eventWithoutDurations = DB.eventsWithoutDuration().findAll();

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

            for (int j = 0; j < events.size(); j++) {
                Event event = events.get(j);
                if (event.getTimelineId() == timeline.getId()) {
                    LocalDate eventStartDate = LocalDate.parse(event.getStartTime(), dtf);
                    LocalDate eventEndDate = LocalDate.parse(event.getEndTime(), dtf);
                    if (timeline.getShowVal().equals("Days")) {

                        cv.event(event, (int) ChronoUnit.DAYS.between(tlstartDate, eventStartDate), counter,
                                (int) ChronoUnit.DAYS.between(eventStartDate, eventEndDate) + 1);
                        counter++;
                    } else if (timeline.getShowVal().equals("Months")) {
                        int diffYearEvent = eventEndDate.getYear() - eventStartDate.getYear();
                        int diffMonthEvent = diffYearEvent * 12 + eventEndDate.getMonthValue() - eventStartDate.getMonthValue();
                        int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();
                        int diffMonthTimeline = diffYearTimeline * 12 + eventStartDate.getMonthValue() - tlstartDate.getMonthValue();

                        cv.event(event, diffMonthTimeline, counter, diffMonthEvent + 1);
                        counter++;
                    } else if (timeline.getShowVal().equals("Years")) {
                        int diffYearEvent = eventEndDate.getYear() - eventStartDate.getYear();
                        int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();

                        cv.event(event, diffYearTimeline, counter, diffYearEvent + 1);
                        counter++;
                    }

                }
            }
            for (int j = 0; j < eventWithoutDurations.size(); j++) {
                EventWithoutDuration eventWithoutDuration = eventWithoutDurations.get(j);
                if (eventWithoutDuration.getTimelineId() == timeline.getId()) {
                    LocalDate eventStartDate = LocalDate.parse(eventWithoutDuration.getStartTime(), dtf);
                    if (timeline.getShowVal().equals("Days")) {

                        cv.eventWithoutDuration(eventWithoutDuration, (int) ChronoUnit.DAYS.between(tlstartDate, eventStartDate), counter);
                        counter++;
                    } else if (timeline.getShowVal().equals("Months")) {
                        int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();
                        int diffMonthTimeline = diffYearTimeline * 12 + eventStartDate.getMonthValue() - tlstartDate.getMonthValue();

                        cv.eventWithoutDuration(eventWithoutDuration, diffMonthTimeline, counter);
                        counter++;
                    } else if (timeline.getShowVal().equals("Years")) {
                        int diffYearTimeline = eventStartDate.getYear() - tlstartDate.getYear();

                        cv.eventWithoutDuration(eventWithoutDuration, diffYearTimeline, counter);
                        counter++;
                    }

                }
            }
        }
    }
}







