public class Option {

    private int conid;
    private double strike;
    private char right;
    private double ask;
    private double bid;

    //get
    public int conid() {
        return conid;
    }
    public double strike() {
        return strike;
    }
    public char right() {
        return right;
    }
    public double ask() {
        return ask;
    }
    public double bid() {
        return bid;
    }

    //set
    public void conid(int conid) {
        this.conid = conid;
    }
    public void strike(double strike) {
        this.strike = strike;
    }
    public void right(char right) {
        this.right = right;
    }
    public void ask(double ask) {
        this.ask = ask;
    }
    public void bid(double bid) {
        this.bid = bid;
    }

    public Option(int conid, double strike, char right){
        this.conid = conid;
        this.strike = strike;
        this.right = right;
    }
}
