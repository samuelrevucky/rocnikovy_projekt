public class Option {

    private double strike;
    private char right;
    private double ask;
    private double bid;
    private double impliedVol;

    //get
    public double getStrike() {
        return strike;
    }
    public char getRight() {
        return right;
    }
    public double getAsk() {
        return ask;
    }
    public double getBid() {
        return bid;
    }
    public double getImpliedVol() {
        return impliedVol;
    }

    //set
    public void setStrike(double strike) {
        this.strike = strike;
    }
    public void setRight(char right) {
        this.right = right;
    }
    public void setAsk(double ask) {
        this.ask = ask;
    }
    public void setBid(double bid) {
        this.bid = bid;
    }
    public void setImpliedVol(double impliedVol) {
        this.impliedVol = impliedVol;
    }

    public Option(double strike, char right){
        this.strike = strike;
        this.right = right;
    }

    public Option(double strike, char right, double ask, double bid) {
        this.strike = strike;
        this.right = right;
        this.ask = ask;
        this.bid = bid;
    }
}
