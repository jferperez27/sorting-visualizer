import java.util.ArrayList;

public interface Sort {
    public boolean sort();
    public ArrayList<Integer> getArray();
    public int getPivot();
    public void resetArray(ArrayList<Integer> newArray);

}
