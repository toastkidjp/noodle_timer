package jp.toastkid.gui.jfx.noodle_timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Stop watch powered by JavaFX.
 *
 * <pre>
 * final Stopwatch stopwatch = new Stopwatch();
 * stopwatch.setTextFill(Color.NAVY);
 * stopwatch.setStyle("-fx-font-size: 2em;");
 * final Button reset = new JFXButton("Reset");
 * reset.setOnAction(eve -> {stopwatch.reset();});
 * new HBox().getChildren().addAll(stopwatch, reset);
 * </pre>
 *
 * @author Toast kid
 *
 */
public class Stopwatch extends Label {

    /** timeline. */
    private Timeline timeline;

    /** String property. */
    private final StringProperty timerText = new SimpleStringProperty();

    /** contanis duration. */
    private Duration time = Duration.ZERO;

    /** this timer is active. */
    private boolean active;

    /** true, countdown. */
    @FXML
    public final BooleanProperty isCountDownProperty = new SimpleBooleanProperty();

    /** time up notifier. */
    private final Notifier notifier;

    /**
     * for set callback.
     * @author Toast kid
     *
     */
    public interface Action {
        public void action();
    }

    /** received call back fire on start. */
    private Action onStart;

    /** received call back fire on stop. */
    private Action onStop;

    /**
     * initialize this component.
     */
    public Stopwatch() {
        this(false);
    }

    /**
     * initialize this component.
     * @param isCountDown
     */
    public Stopwatch(@NamedArg("isCountDown") final boolean isCountDown) {
        this.isCountDownProperty.set(isCountDown);
        this.textProperty().bind(timerText);
        reset();
        this.setStyle("-fx-font-size: 15em;");
        notifier = new Notifier.Builder().setPathToWav(Resource.PATH_TO_WAV)
                .setTitle("時間です！")
                .setMessage("ラーメンができました！！！！！")
                .build();
    }

    /**
     * start count up.
     */
    public void start() {

        if (time.equals(Duration.ZERO)) {
            return;
        }

        if (active) {
            stop();
            return;
        }

        active = true;
        if (timeline == null) {
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(100),
                    e2 -> {
                        if (!active) {
                            return;
                        }
                        final Duration duration = ((KeyFrame) e2.getSource()).getTime();
                        if (isCountDownProperty.get()) {
                            if (time.greaterThan(Duration.ONE)) {
                                time = time.subtract(duration);
                            } else if (isActive()) {
                                stop();
                                notifier.play();
                            }
                        } else {
                            time = time.add(duration);
                        }
                        timerText.set(makeText(time));
                    }
                )
            );
        }
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        if (onStart != null) {
            onStart.action();
        }
    }

    /**
     * stop.
     */
    public void stop() {
        if (timeline == null) {
            return;
        }
        timeline.stop();
        active = false;
        timerText.set(makeText(time));
        if (onStop != null) {
            onStop.action();
        }
    }

    /**
     * reset timer.
     */
    public void reset() {
        time = Duration.ZERO;
        timerText.set(makeText(time));
    }

    /**
     * set duration and modify text.
     * @param ms
     */
    public void setMs(final long ms) {
        time = Duration.millis(ms);
        final String makeText = makeText(time);
        timerText.set(makeText);
    }

    /**
     * duration to text.
     * @param duration
     * @return
     */
    private String makeText(final Duration duration) {
        return String.format("%02d:%02d",
                (long) (duration.toMinutes() % 60.0),
                (long) (Math.max(0.0d, duration.toSeconds()) % 60.0)
                );
    }

    /**
     * get is active.
     * @return active(boolean)
     */
    public boolean isActive() {
        return active;
    }

    /**
     * set callback on start.
     * @param onStart
     */
    public void setOnStart(final Action onStart) {
        this.onStart = onStart;
    }

    /**
     * set callback on stop.
     * @param onStart
     */
    public void setOnStop(final Action onStop) {
        this.onStop = onStop;
    }

}
