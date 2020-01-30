package com.example.ilovezappos;

public class AskGetters {

    private String val;
    private String amt;
    private String ask;

    public AskGetters(String val, String amt, String ask) {
        this.val = val;
        this.amt = amt;
        this.ask = ask;
    }

    public String getVal() {
        return val;
    }

    public String getAmt() {
        return amt;
    }

    public String getAsk() {
        return ask;
    }
}
