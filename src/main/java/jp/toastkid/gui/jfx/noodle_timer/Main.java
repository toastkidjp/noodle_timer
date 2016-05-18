package jp.toastkid.gui.jfx.noodle_timer;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Noodle timer.
 * @author Toast kid
 *
 */
public class Main extends Application {

    /** Application title. */
    private static final String TITLE = "Noodle Timer";

    /** fxml file. */
    private static final String FXML_PATH = "timer.fxml";

    /** point of starting drag(x). */
    private double dragStartX;

    /** point of starting drag(y). */
    private double dragStartY;

    /** Stage. */
    private Stage stage;

    @Override
    public void start(final Stage arg0) throws Exception {
        stage = new Stage(StageStyle.TRANSPARENT);
        final Image image = new Image(
                getClass().getClassLoader().getResourceAsStream(Resource.PATH_TO_ICON));

        try {
            final FXMLLoader loader
                = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH));
            final Parent load = loader.load();
            final Scene scene = new Scene(load);
            // implement drag event.
            scene.setOnMousePressed((event) -> {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
            });
            scene.setOnMouseDragged((event) -> {
                stage.setX(event.getScreenX() - dragStartX);
                stage.setY(event.getScreenY() - dragStartY);
            });

            stage.setScene(scene);
            ((Controller) loader.getController()).setStage(stage);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        stage.getIcons().add(
                image);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        Application.launch(Main.class);
    }
}
