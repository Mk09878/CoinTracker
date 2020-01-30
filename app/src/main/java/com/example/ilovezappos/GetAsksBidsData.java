package com.example.ilovezappos;

import java.util.List;

public class GetAsksBidsData {

    private String timestamp;
    private List<List<String>> bids,asks;

    public String getTimestamp() {
        return timestamp;
    }

    public List<List<String>> getBids() {
        return bids;
    }

    public List<List<String>> getAsks() {
        return asks;
    }
}
