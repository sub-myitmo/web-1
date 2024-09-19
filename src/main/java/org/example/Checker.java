package org.example;

import java.util.HashMap;

public class Checker {
    private HashMap<String, String> data;
    private int x;
    private float y;
    private int r;

    public Checker(HashMap<String, String> data) {
        this.data = data;
    }

    public boolean validate() {
        try {
            x = Integer.parseInt(data.get("x"));
            y = Float.parseFloat(data.get("y"));
            r = Integer.parseInt(data.get("r"));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isHit() {
        return quarter1() || quarter3() || quarter4();
    }

    private boolean quarter1() {
        if (x >= 0 && y >= 0) {
            return Math.sqrt(this.x * this.x + this.y * this.y) <= (double) this.r / 2;
        }
        return false;
    }


    private boolean quarter3() {
        if (x < 0 && y < 0) {
            return (this.x >= -this.r) && (this.y >= -this.r);
        }
        return false;
    }

    private boolean quarter4() {
        if (x < 0 && y >= 0) {
            return (this.y <= (double) this.r / 2 + 0.5 * this.x);
        }
        return false;
    }
}
