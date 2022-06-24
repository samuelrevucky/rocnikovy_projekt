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
        if (option.getRight() == Option.Right.Call) calls.put(strike, option);
        else puts.put(strike, option);
    }

    public HashMap<Option, Integer> longIronBtfly() {
        HashMap<Option, Integer> map = new HashMap<>();
        double middle = strikes.stream().reduce((p1, p2) -> Math.abs(p1 - priceOfUnderl) < Math.abs(p2 - priceOfUnderl) ? p1 : p2).orElseThrow();
        map.put(calls.get(middle), 2);
        map.put(puts.get(middle), 2);

        map.put(calls.get(strikes.stream().reduce((p1, p2) -> Math.abs(p1 - priceOfUnderl * 1.1) < Math.abs(p2 - priceOfUnderl * 1.1) ? p1 : p2).orElseThrow()), -1);
        map.put(calls.get(strikes.stream().reduce((p1, p2) -> Math.abs(p1 - priceOfUnderl * 1.2) < Math.abs(p2 - priceOfUnderl * 1.2) ? p1 : p2).orElseThrow()), -1);

        map.put(puts.get(strikes.stream().reduce((p1, p2) -> Math.abs(p1 - priceOfUnderl * 0.9) < Math.abs(p2 - priceOfUnderl * 0.9) ? p1 : p2).orElseThrow()), -1);
        map.put(puts.get(strikes.stream().reduce((p1, p2) -> Math.abs(p1 - priceOfUnderl * 0.8) < Math.abs(p2 - priceOfUnderl * 0.8) ? p1 : p2).orElseThrow()), -1);

        return map;
    }

    @Override
    public String toString() {
        StringBuilder SB = new StringBuilder();
        for (double x : strikes) {
            SB.append(calls.get(x)).append(" || Strike: ").append(x).append(" || ").append(puts.get(x)).append("\n");
        }
        return SB.toString();
    }
}
