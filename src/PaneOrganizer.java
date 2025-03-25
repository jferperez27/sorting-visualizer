import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PaneOrganizer {

    private BorderPane root;
    private HBox arrayNode;
    private ArrayVisualizer visualizer;
    private boolean timelineActive;
    private boolean sorting;
    private Timeline sortingTimeline;

    /**
     * Constructor for PaneOrganizer
     */
    public PaneOrganizer() {
        this.root = new BorderPane();
        this.arrayNode = new HBox();
        this.root.setBottom(this.arrayNode);
        this.sorting = false;
        this.setBackground();
        this.visualizer = new ArrayVisualizer(this.arrayNode);
        this.setTimeline();
        this.timelineActive = false;
    }

    /**
     * Sets background to Black.
     */
    private void setBackground() {
        BackgroundFill fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        this.root.setBackground(background);
    }

    private void setTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(3), (ActionEvent e) -> this.startSort());
        this.sortingTimeline = new Timeline(kf);
        this.sortingTimeline.setCycleCount(Animation.INDEFINITE);

        this.arrayNode.setFocusTraversable(true);
        this.arrayNode.setOnKeyPressed(this::handleKeyInput);
    }

    private void startSort() {
        //boolean sortStatus = this.visualizer.sort();
        this.visualizer.sort();
//        if (!sortStatus) {
//            this.sortingTimeline.stop();
//        }
    }

    private void toggleTimeline() {
        if (this.timelineActive) {
            this.sortingTimeline.pause();
            this.timelineActive = false;
        } else {
            this.sortingTimeline.play();
            this.timelineActive = true;
        }
    }

    private void handleKeyInput(KeyEvent e) {
        KeyCode code = e.getCode();
        switch (code) {
            case R:
                if (!this.sorting) {
                    this.visualizer.generateArray();
                    System.out.println("RANDOM");
                }
                break;
            case SPACE:
                this.toggleTimeline();
                break;
            default:
                break;

        }
    }

    /**
     * Returns the root node of the application.
     * @return - BorderPane (root node).
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
