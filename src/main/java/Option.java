public class Option {

    public enum Right {Call, Put}

    private double strike;
    private Right right;
    private double ask;
    private double bid;
    private double impliedVol;

    //get
    public double getStrike() {
        return strike;
    }
    public Right getRight() {
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
    public void setRight(Right right) {
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

    public Option(double strike, Right right){
        this.strike = strike;
        this.right = right;
    }

    public Option(double strike, Right right, double ask, double bid) {
        this.strike = strike;
        this.right = right;
        this.ask = ask;
        this.bid = bid;
    }

    @Override
    public String toString() {
        return right + " Ask: " + ask + " Bid: " + bid + " ";
    }
}
