package org.shaglund;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shaglund on 2016-10-23.
 */
public class Rates {
    private float sek;

    @JsonCreator
    public Rates(@JsonProperty("SEK") float sek) {
        this.sek = sek;
    }

    public float getSek() {
        return sek;
    }

    public void setSek(float sek) {
        this.sek = sek;
    }
}
