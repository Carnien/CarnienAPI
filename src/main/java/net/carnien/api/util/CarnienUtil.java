package net.carnien.api.util;

import net.carnien.api.Carnien;

public class CarnienUtil {

    private final Carnien carnien;

    public CarnienUtil(Carnien carnien) {
        this.carnien = carnien;
    }

    protected Carnien getCarnien() {
        return  carnien;
    }

}
