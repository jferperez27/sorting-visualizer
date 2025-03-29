import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MergeSort implements Sort {

    private ArrayList<Integer> toSort;
    private int currentIteration;
    private int currentIndex;
    private ArrayVisualizer myVisualizer;
    private Timeline mergeTimeline;
    private boolean mergeTimelineActive;
    private Queue<int[]> mergeQueue;
    private int accesses;
    public MergeSort(ArrayVisualizer visualizer) {
        this.setTimeline();
        this.mergeTimelineActive = false;
        this.currentIteration = 0;
        this.accesses = 0;
        this.myVisualizer = visualizer;
    }

    @Override
    public boolean sort() {
        if (!this.mergeTimelineActive) {
            this.myVisualizer.timeline.pause();
            this.prepMergeSort();
            this.mergeTimeline.play();
            this.mergeTimelineActive = true;
            return true;
        }
        return false;
    }

    private void prepMergeSort() {
        this.mergeQueue = new LinkedList<>();

        int size = toSort.size();
        for (int i = 1; i < size; i *= 2) {
            for (int left = 0; left < size - i; left += 2 * i) {
                int middle = left + i - 1;
                int right = Math.min(left + 2 * i - 1, size - 1);
                this.mergeQueue.add(new int[]{left, middle, right});
            }
        }
    }

    private void mergeStep() {
        if (!this.mergeQueue.isEmpty()) {
            int[] toMerge = this.mergeQueue.poll();
            int left = toMerge[0];
            int middle = toMerge[1];
            int right = toMerge[2];

            this.merge(left, middle, right);
            this.myVisualizer.updateVisuals();
        } else {
            this.mergeTimeline.stop();
            this.mergeTimelineActive = false;
        }
    }

    private void merge(int left, int middle, int right) {
        ArrayList<Integer> current = new ArrayList<>(this.toSort.subList(left, right + 1));
        int i = 0, j = middle - left + 1, k = left;

        while (i <= middle - left && j < current.size()) {
            this.accesses += 2;
            if (current.get(i) < current.get(j)) {
                this.toSort.set(k++, current.get(i++));
            } else {
                this.toSort.set(k++, current.get(j++));
            }
        }
        while (i <= middle - left) {
            this.toSort.set(k++, current.get(i++));
        }
        while (j < current.size()) {
            this.toSort.set(k++, current.get(j++));
        }
        accesses += current.size();

    }

    private void setTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.WHILE_LOOP_SPEED), (ActionEvent e) -> this.mergeStep());
        this.mergeTimeline = new Timeline(kf);
        this.mergeTimeline.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public ArrayList<Integer> getArray() {
        return null;
    }

    @Override
    public int getPivot() {
        return 0;
    }

    @Override
    public void resetArray(ArrayList<Integer> newArray) {
        if (!this.mergeTimelineActive) {
            this.toSort = newArray;
            this.currentIteration = 0;
            this.accesses = 0;
            this.mergeTimeline.stop();
            this.mergeTimelineActive = false;
            this.sort();
        }
        this.toSort = newArray;
        this.currentIteration = 0;
        this.accesses = 0;
        this.mergeTimeline.stop();
        this.mergeTimelineActive = false;
        this.sort();
    }

    @Override
    public int getAccesses() {
        return 0;
    }
}
