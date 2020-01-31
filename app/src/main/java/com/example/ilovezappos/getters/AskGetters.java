package com.example.ilovezappos.getters;

public class AskGetters {

    private String val;
    private String amt;
    private String ask;

    public AskGetters(String ask, String amt, String val) {
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
