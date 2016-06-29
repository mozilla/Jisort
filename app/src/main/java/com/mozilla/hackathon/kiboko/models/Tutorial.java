package com.mozilla.hackathon.kiboko.models;

import java.util.Random;

public class Tutorial {
    public String id;
    public String tag;
    public String header;
    public String photoUrl;
    public Step[] steps;
    public int groupingOrder;

    public String getImportHashCode() {
        return (new Random()).nextLong() + "";
    }
}
