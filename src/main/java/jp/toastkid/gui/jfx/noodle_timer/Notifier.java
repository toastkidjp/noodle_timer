package jp.toastkid.gui.jfx.noodle_timer;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Notifier.
 * @author Toast kid
 *
 */
public class Notifier {

    /** this use when passed value is empty. */
    private static final String DEFAULT_TEXT = "Time up";

    /** media player. */
    private MediaPlayer media;

    /** Notification dialog. */
    private final Alert alert;

    /**
     * {@link Notifier}'s builder.
     * @author Toast kid
     *
     */
    public static class Builder {

        /** path/to/nav. */
        private String pathToWav;

        /** dialog's title. */
        private String title;

        /** dialog's message. */
        private String message;

        /**
         * set param and return oneself.
         * @param pathToWav
         * @return
         */
        public Builder setPathToWav(final String pathToWav) {
            this.pathToWav = pathToWav;
            return this;
        }

        /**
         * set param and return oneself.
         * @param title
         * @return
         */
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * set param and return oneself.
         * @param message
         * @return
         */
        public Builder setMessage(final String message) {
            this.message = message;
            return this;
        }

        /**
         * build {@link Notifier}'s object.
         * @return
         */
        public Notifier build() {
            return new Notifier(this);
        }
    }


    /**
     * initialize with media file.
     * @param pathToWav
     */
    private Notifier(final Builder b) {
        try {
            final File wav = new File(b.pathToWav);
            // ファイルが見つからない時は初期化をスキップした方が高速.
            if (wav.exists()) {
                media = new MediaPlayer(new Media(wav.toURI().toString()));
            }
        } catch (final MediaException e) {
            e.printStackTrace();
        }
        alert = new Alert(AlertType.INFORMATION);
        final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        final Image image = new Image(getClass().getClassLoader().getResourceAsStream("ramen.png"));
        stage.getIcons().add(image);
        alert.setGraphic(new ImageView(image));
        alert.setTitle(StringUtils.isEmpty(b.title) ? DEFAULT_TEXT : b.title);
        alert.setHeaderText(StringUtils.isEmpty(b.title) ? DEFAULT_TEXT : b.message);
    }

    /**
     * attempt to notification.
     */
    public void play() {
        if (media != null) {
            media.play();
            try {
                Thread.sleep(2000L);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            media.stop();
        }
        alert.show();
    }
}
