
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class InsertionSort implements Sort {

    private ArrayList<Integer> toSort;
    private int currentIteration;
    private int currentIndex;
    private ArrayVisualizer myVisualizer;
    private Timeline whileTimeline;
    private boolean whileTimelineActive;
    public InsertionSort(ArrayVisualizer visualizer) {
        this.setTimeline();
        this.whileTimelineActive = false;
        this.currentIteration = 0;
        this.myVisualizer = visualizer;
    }

    @Override
    public boolean sort() {
        if (this.currentIteration < Constants.ARRAY_MAX_LENGTH) {
            this.currentIndex = this.currentIteration;
            this.whileTimeline.play();
            return true;
        }
        if (this.whileTimelineActive) {
            return true;
        }
        return false;
    }

    private void whileLoopSort() {
        if (this.currentIndex > 0 && this.toSort.get(this.currentIndex - 1) > this.toSort.get(this.currentIndex)) {
            this.whileTimelineActive = true;
            this.swap(this.currentIndex, this.currentIndex - 1);
            this.currentIndex--;
            this.myVisualizer.updateVisuals();
            this.myVisualizer.setPivot(this.currentIndex, this.currentIteration);
        } else {
            this.whileTimeline.stop();
            this.whileTimelineActive = false;
            this.currentIteration++;
        }
    }

    private void setTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(0.05), (ActionEvent e) -> this.whileLoopSort());
        this.whileTimeline = new Timeline(kf);
        this.whileTimeline.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public ArrayList<Integer> getArray() {
        return this.toSort;
    }

    @Override
    public int getPivot() {
        return this.currentIteration;
    }

    @Override
    public void resetArray(ArrayList<Integer> newArray) {
        this.toSort = newArray;
        this.currentIteration = 0;
    }

    private void swap(int currentRightIndex, int currentLeftIndex) {
        int newLeft = this.toSort.get(currentRightIndex);
        int newRight = this.toSort.get(currentLeftIndex);

        this.toSort.set(currentLeftIndex, newLeft);
        this.toSort.set(currentRightIndex, newRight);
    }

}
