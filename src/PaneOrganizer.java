import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PaneOrganizer {

    private BorderPane root;
    private HBox arrayNode;
    private ArrayVisualizer visualizer;
    private double speed;
    private boolean timelineActive;
    private boolean sorting;
    private boolean sortingFinished;
    private Timeline sortingTimeline;
    private int totalAccesses;
    private VBox programInfoTextBox;
    private VBox aboutInfoTextBox;
    private Label currentAlgorithm;
    private Label numOfAccesses;
    private Label currentSpeed;
    private Label currentProgramStatus;
    private Label timeComplexity;
    private Label aboutLabel;
    private BackgroundColor background;

    public enum Increment {
        INCREASE, DECREASE
    }

    public enum BackgroundColor {
        BLACK_MODE, WHITE_MODE
    }

    /**
     * Constructor for PaneOrganizer
     */
    public PaneOrganizer() {
        this.root = new BorderPane();
        this.arrayNode = new HBox();
        this.root.setBottom(this.arrayNode);
        this.sorting = false;
        this.sortingFinished = true;
        this.totalAccesses = 0;
        this.speed = 1;
        this.setBackground();
        this.setTimeline();
        this.setTextBoxes();
        this.visualizer = new ArrayVisualizer(this.arrayNode, this.sortingTimeline);
        this.timelineActive = false;
    }

    /**
     * Sets background to Black.
     */
    private void setBackground() {
        BackgroundFill fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        this.root.setBackground(background);
        this.background = BackgroundColor.BLACK_MODE;
    }

    private void toggleBackground() {
        BackgroundFill black = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill white = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);

        if (this.background == BackgroundColor.BLACK_MODE) { // if background is black
            this.background = BackgroundColor.WHITE_MODE;
            this.root.setBackground(new Background(white)); // change background to white
            this.visualizer.changeBarColor(BackgroundColor.BLACK_MODE); // change bars to black
            this.toggleLabels(BackgroundColor.BLACK_MODE);
        } else { // if background is white
            this.background = BackgroundColor.BLACK_MODE;
            this.root.setBackground(new Background(black)); // change background to black
            this.visualizer.changeBarColor(BackgroundColor.WHITE_MODE); // change bars to white
            this.toggleLabels(BackgroundColor.WHITE_MODE);
        }
    }

    private void toggleLabels(BackgroundColor color) {
        if (color == BackgroundColor.BLACK_MODE) {
            this.currentAlgorithm.setTextFill(Color.BLACK);
            this.timeComplexity.setTextFill(Color.BLACK);
            this.numOfAccesses.setTextFill(Color.BLACK);
            this.currentSpeed.setTextFill(Color.BLACK);
            this.currentProgramStatus.setTextFill(Color.BLACK);
            this.aboutLabel.setTextFill(Color.BLACK);
        } else {
            this.currentAlgorithm.setTextFill(Color.WHITE);
            this.timeComplexity.setTextFill(Color.WHITE);
            this.numOfAccesses.setTextFill(Color.WHITE);
            this.currentSpeed.setTextFill(Color.WHITE);
            this.currentProgramStatus.setTextFill(Color.WHITE);
            this.aboutLabel.setTextFill(Color.WHITE);
        }
    }

    private void setTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.SORT_SPEED), (ActionEvent e) -> this.startSort());
        this.sortingTimeline = new Timeline(kf);
        this.sortingTimeline.setCycleCount(Animation.INDEFINITE);

        this.arrayNode.setFocusTraversable(true);
        this.arrayNode.setOnKeyPressed(this::handleKeyInput);
    }

    private void startSort() {
        boolean sortStatus = this.visualizer.sort();
        if (!sortStatus) {
            this.sortingTimeline.stop();
            this.sorting = false;
            this.sortingFinished = true; // might not be useful
            this.visualizer.setSorted();
            this.updateStatus("Sorting Complete!");
        }
        this.sortingFinished = false; //might not be useful
        this.sorting = true;
        this.updateAccesses();
    }

    private void updateAccesses() {
        this.numOfAccesses.setText(" Num of Accesses: " + this.getAccesses());
    }

    private void updateStatus(String status) {
        this.currentProgramStatus.setText(" Current Visualizer Status: " + status);
    }


    private void toggleTimeline() {
        if (this.timelineActive) {
            this.sortingTimeline.pause();
            this.timelineActive = false;
            this.updateStatus("Paused");
        } else {
            this.sortingTimeline.play();
            this.timelineActive = true;
            this.updateStatus("Sorting...");
        }
    }


    private void handleKeyInput(KeyEvent e) {
        KeyCode code = e.getCode();
        switch (code) {
            case R:
                this.visualizer.generateArray();
                this.sorting = false;
                this.sortingFinished = true;
                break;
            case SPACE:
                if (this.sortingFinished && !this.sorting) {
                    this.toggleTimeline();
                }
                break;
            case T:
                this.toggleBackground();
                System.out.println("toggle background");
                break;
            default:
                break;

        }
    }

    private String getAccesses() {
        int accesses = this.visualizer.getAccesses();
        return String.valueOf(accesses);
    }

    private void setTextBoxes() {
        Font fira = Font.loadFont(getClass().getResourceAsStream("/fonts/FiraCode-Bold.ttf"), 10);
        VBox leftText = new VBox();
        VBox rightText = new VBox();
        HBox textHolder = new HBox();
        Region spacer = new Region();

        this.setUpInfoBox(leftText);

        this.aboutLabel = new Label("Sorting Algorithm Visualizer  \n By: Juan Fernández-Pérez" +
                "\n \nControls:\nR - Randomize Array\nSPACE - Toggle Sorting\nT - Dark/Light Mode");
        this.aboutLabel.setTextFill(Color.WHITE);
        this.aboutLabel.setFont(fira);

        rightText.getChildren().add(this.aboutLabel);

        HBox.setHgrow(spacer, Priority.ALWAYS);
        textHolder.getChildren().addAll(leftText, spacer, rightText);

        this.root.setTop(textHolder);
    }

    private void setUpInfoBox(VBox leftText) {
        Font firaDesc = Font.loadFont(getClass().getResourceAsStream("/fonts/FiraCode-Bold.ttf"), 15);

        this.currentAlgorithm = new Label(" Current Algorithm: " + "Insertion Sort");
        this.timeComplexity = new Label(" Current Algorithmic Time Complexity: " + "O(n^2)");
        this.numOfAccesses = new Label(" Num of Accesses: " + "0");
        this.currentSpeed = new Label(" Current Speed: " + "1");
        this.currentProgramStatus = new Label(" Current Visualizer Status: " + "Paused");
        this.currentAlgorithm.setTextFill(Color.WHITE);
        this.timeComplexity.setTextFill(Color.WHITE);
        this.numOfAccesses.setTextFill(Color.WHITE);
        this.currentSpeed.setTextFill(Color.WHITE);
        this.currentProgramStatus.setTextFill(Color.WHITE);
        this.currentAlgorithm.setFont(firaDesc);
        this.timeComplexity.setFont(firaDesc);
        this.numOfAccesses.setFont(firaDesc);
        this.currentSpeed.setFont(firaDesc);
        this.currentProgramStatus.setFont(firaDesc);

        leftText.getChildren().addAll(this.currentAlgorithm, this.timeComplexity, this.numOfAccesses,
                this.currentSpeed, this.currentProgramStatus);
    }


    /**
     * Returns the root node of the application.
     * @return - BorderPane (root node).
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
