import javafx.animation.Timeline;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import java.util.ArrayList;

public class ArrayVisualizer {
    private HBox arrayNode;
    private ArrayList<Integer> arrayValues;
    private ArrayList<Rectangle> arrayObjects;
    private boolean isPopulated;
    private Sort currentSort;
    public Timeline timeline;
    private Color barColor;
    private boolean isSorted;

    /**
     * Enum to specify changes in currentSort
     */
    public enum sortChoose {
        LEFT, RIGHT
    }

    /**
     * Constructor for ArrayVisualizer initializes variables and starts generating/visualizing a new, random array.
     * @param arrayNode - node to visualize array in
     */
    public ArrayVisualizer(HBox arrayNode, Timeline time) {
        this.arrayNode = arrayNode;
        this.timeline = time;
        this.arrayValues = new ArrayList<>();
        this.arrayObjects = new ArrayList<>();
        this.isSorted = false;
        this.arrayNode.setAlignment(Pos.BOTTOM_LEFT);
        this.arrayNode.setSpacing(Constants.ARRAY_PADDING);
        this.barColor = Color.WHITE;
        this.currentSort = new InsertionSort(this);

        // Randomly generates the array
        this.generateArray();
        this.isPopulated = true;
    }

    /**
     * Populates the arrayList with random values
     */
    public void generateArray() {
        this.isSorted = false;
        if (this.isPopulated) {
            this.removeVisuals();
            this.isPopulated = false;
            this.generateArray();
        } else {
            for (int i = 0; i < Constants.ARRAY_MAX_LENGTH; i++) {
                this.arrayValues.add(i, this.getRandomArrayValue());
            }
            this.visualizeArray();
            this.currentSort.resetArray(this.arrayValues);
            this.isPopulated = true;
        }
    }

    /**
     * Helper for generateArray outputs random integers within a set range
     * @return random int in range
     */
    private int getRandomArrayValue() {
        // Changing ARRAY_MAX_VALUE / ARRAY_MIN_VALUE will alter the integers that this method outputs
        return (int) (Math.random() *
                (Constants.ARRAY_MAX_VALUE - Constants.ARRAY_MIN_VALUE + 1) + Constants.ARRAY_MIN_VALUE);
    }

    /**
     * Creates the current arrayValues into arrayObjects, able to be visualized on the pane.
     */
    private void visualizeArray() {
        for (int i = 0; i < Constants.ARRAY_MAX_LENGTH; i++) {
            int currentArrayValue = this.arrayValues.get(i);
            this.arrayObjects.add(i, new Rectangle(Constants.ARRAY_REC_LENGTH, currentArrayValue, this.barColor));
            this.arrayNode.getChildren().add(this.arrayObjects.get(i));
        }
    }

    /**
     * Removes the objects from the arrayNode
     */
    private void removeVisuals() {
        for (int i = 0; i < Constants.ARRAY_MAX_LENGTH; i++) {
            Rectangle current = this.arrayObjects.get(i);
            this.arrayNode.getChildren().remove(current);
        }
    }

    /**
     * Calls on sort to currentSort
     * @return - currentSort boolean
     */
    public boolean sort() {
        return this.currentSort.sort();
    }

    /**
     * Colors the current objects as the pivot and the currentAccess
     * @param currentAccess - index of object to paint green
     * @param pivot - index of object to paint red
     */
    public void setPivot(int currentAccess, int pivot) {
        this.arrayObjects.get(currentAccess).setFill(Color.GREEN);
        this.arrayObjects.get(pivot).setFill(Color.RED);
    }

    /**
     * Updates the objects visually.
     */
    public void updateVisuals() {
        for (int i = 0; i < Constants.ARRAY_MAX_LENGTH; i++) {
            int height = this.arrayValues.get(i);
            Rectangle rect = this.arrayObjects.get(i);
            rect.setHeight(height);
            rect.setFill(this.barColor); // Reset color
        }
    }

    /**
     * Sets all objects green, signaling a fully sorted array.
     */
    public void setSorted() {
        for (int i = 0; i < Constants.ARRAY_MAX_LENGTH; i++) {
            this.arrayObjects.get(i).setFill(Color.GREEN);
        }
        this.isSorted = true;
    }

    /**
     * Getter for the currentSort accesses
     * @return - int of current accesses
     */
    public int getAccesses() {
        return this.currentSort.getAccesses();
    }

    /**
     * Changes object colors to correspond depending on current background color.
     * @param color - current color of background
     */
    public void changeBarColor(PaneOrganizer.BackgroundColor color) {
        if (!this.isSorted) {
            if (color == PaneOrganizer.BackgroundColor.BLACK_MODE) {
                this.barColor = Color.BLACK;
                this.updateVisuals();
            } else {
                this.barColor = Color.WHITE;
                this.updateVisuals();
            }
        } else if (color == PaneOrganizer.BackgroundColor.BLACK_MODE) {
            this.barColor = Color.BLACK;
        } else {
            this.barColor = Color.WHITE;
        }
    }
}
