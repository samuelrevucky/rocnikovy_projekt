import java.util.ArrayList;
import java.util.HashMap;

public class OptionChain {

    private final ArrayList<Double> strikes;
    private final HashMap<Double, Option> calls;
    private final HashMap<Double, Option> puts;
    private int numberOfContracts;
    private int numberOfQuotes;

    public OptionChain(){
        strikes = new ArrayList<>();
        calls = new HashMap<>();
        puts = new HashMap<>();
        numberOfContracts = 0;
        numberOfQuotes = 0;
    }

    public ArrayList<Double> strikes() {
        return strikes;
    }

    public HashMap<Double, Option> calls() {
        return calls;
    }

    public HashMap<Double, Option> puts() {
        return puts;
    }

    public int numberOfContracts() {
        return numberOfContracts;
    }

    public int numberOfQuotes() {
        return numberOfQuotes;
    }

    public void incNumberOfContracts() {
        ++numberOfContracts;
    }

    public void incNumberOfQuotes() {
        ++numberOfQuotes;
    }
}
