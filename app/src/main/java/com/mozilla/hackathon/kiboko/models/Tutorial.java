package com.mozilla.hackathon.kiboko.models;

import java.util.Random;

/**
 * Created by Audrey on 25/06/2016.
 */
public class Tutorial {
    public String id;
    public String tag;
    public String header;
    public String photoUrl;
    public Step[] steps;
    public int groupingOrder;

    public class Step {
        public String id;
        public String title;
        public String description;
        public String gifUrl;

        @Override
        public String toString() {
            return "Step{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", gifUrl='" + gifUrl + '\'' +
                    '}';
        }
    }

    public String getImportHashCode() {
        return (new Random()).nextLong() + "";
    }
}
