package jp.toastkid.gui.jfx.noodle_timer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller.
 * @author Toast kid
 *
 */
public class Controller implements Initializable {

    /** passed parent stage. */
    private Stage stage;

    /** timer. */
    @FXML
    private Stopwatch timer;

    /** close button. */
    @FXML
    private Button closer;

    /** input 3 min. */
    @FXML
    private Button three;

    /** input 4 min. */
    @FXML
    private Button four;

    /** input 5 min. */
    @FXML
    private Button five;

    /** timer control button. */
    @FXML
    private Button ctrl;

    /** numeric input text. */
    @FXML
    private NumberTextField numberInput;

    /**
     * start timer.
     */
    @FXML
    private void play() {
        timer.start();
    }

    /**
     * stop timer.
     */
    @FXML
    private void stop() {
        timer.stop();
    }

    /**
     * reset timer.
     */
    @FXML
    private void reset() {
        timer.reset();
    }

    /**
     * close this app.
     */
    @FXML
    private void close() {
        if (stage == null) {
            return;
        }
        stage.close();
    }

    /**
     * input by textfield.
     */
    @FXML
    private void input() {
        setMinutes(Long.parseLong(numberInput.getText()));
    }

    /**
     * set timer duration and text with minutes.
     * @param minutes
     */
    private void setMinutes(final long minutes) {
        timer.setMs(TimeUnit.MINUTES.toMillis(minutes));
    }

    /**
     * set parent's stage.
     * @param stage
     */
    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        three.setOnAction(eve -> {setMinutes(3L);});
        four.setOnAction( eve -> {setMinutes(4L);});
        five.setOnAction( eve -> {setMinutes(5L);});
        timer.setOnStart( ()  -> {ctrl.setText("■");});
        timer.setOnStop(  ()  -> {ctrl.setText("▶");});
        closer.setStyle("-fx-background-color: red; ");
        ctrl.setText("▶");
    }
}
