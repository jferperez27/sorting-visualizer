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
    private int oldPivot;
    private int currentPivot;


    /**
     * Constructor for ArrayVisualizer initializes variables and starts generating/visualizing a new, random array.
     * @param arrayNode - node to visualize array in
     */
    public ArrayVisualizer(HBox arrayNode) {
        this.arrayNode = arrayNode;
        this.arrayValues = new ArrayList<>();
        this.arrayObjects = new ArrayList<>();
        this.arrayNode.setAlignment(Pos.BOTTOM_LEFT);
        this.arrayNode.setSpacing(Constants.ARRAY_PADDING);
        this.currentSort = new InsertionSort(this);
        // Randomly generates the array
        this.generateArray();
        this.isPopulated = true;
    }

    /**
     * Populates the arrayList with random values
     */
    public void generateArray() {
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
            this.arrayObjects.add(i, new Rectangle(Constants.ARRAY_REC_LENGTH, currentArrayValue, Color.WHITE));
            this.arrayNode.getChildren().add(this.arrayObjects.get(i));
        }
    }

    private void removeVisuals() {
        for (int i = 0; i < Constants.ARRAY_MAX_LENGTH; i++) {
            Rectangle current = this.arrayObjects.get(i);
            this.arrayNode.getChildren().remove(current);
        }
    }

    public boolean sort() {
        return this.currentSort.sort();
    }

    public void setPivot(int currentPivot, int oldPivot) {
        this.currentPivot = this.currentSort.getPivot();
        this.arrayObjects.get(currentPivot).setFill(Color.GREEN);
        this.arrayObjects.get(oldPivot).setFill(Color.WHITE);
    }

    public void updateVisuals() {
        this.removeVisuals();
        this.visualizeArray();
    }
}
