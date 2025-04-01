import java.util.ArrayList;

public class BogoSort implements Sort{

    private ArrayVisualizer myVisualizer;
    public BogoSort(ArrayVisualizer visualizer) {
        myVisualizer = visualizer;
    }
    @Override
    public boolean sort() {
        return false;
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

    }

    @Override
    public int getAccesses() {
        return 0;
    }
}
