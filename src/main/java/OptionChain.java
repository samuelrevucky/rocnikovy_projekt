import java.util.ArrayList;
import java.util.HashMap;

public class OptionChain {

    private final ArrayList<Double> strikes = new ArrayList<>();
    private final HashMap<Double, Option> calls = new HashMap<>();
    private final HashMap<Double, Option> puts = new HashMap<>();
    private final Double priceOfUnderl;

    public OptionChain(Double priceOfUnderl) {
        this.priceOfUnderl = priceOfUnderl;
    }

    public Double getPriceOfUnderl() {
        return priceOfUnderl;
    }

    public ArrayList<Double> getStrikes() {
        return strikes;
    }

    public HashMap<Double, Option> getCalls() {
        return calls;
    }

    public HashMap<Double, Option> getPuts() {
        return puts;
    }

    public void addOption(Option option){
        double strike = option.getStrike();
        if (!strikes.contains(strike)) strikes.add(strike);
        if (option.getRight() == 'C') calls.put(strike, option);
        else puts.put(strike, option);
    }

}
