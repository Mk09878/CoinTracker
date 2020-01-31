package com.example.ilovezappos.getters;

public class BidGetters {

    private String val;
    private String amt;
    private String bids;

    public BidGetters(String bids, String amt, String val) {
        this.val = val;
        this.amt = amt;
        this.bids = bids;
    }

    public String getVal() {
        return val;
    }

    public String getAmt() {
        return amt;
    }

    public String getBids() {
        return bids;
    }
}
