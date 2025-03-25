
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /*
     * Starts the application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.WINDOW_LENGTH, Constants.WINDOW_WIDTH);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sorting Algorithm Visualizer");
        stage.show();
    }

    /*
     * Java mainline.
     */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}