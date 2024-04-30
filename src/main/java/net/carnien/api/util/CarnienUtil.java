package net.carnien.api.util;

import net.carnien.api.Carnien;

public class CarnienUtil {

    private final Carnien carnien;

    /**
     * A class contained by a Carnien field
     * with a getter. Created in order to save
     * the Carnien getter in classes which extends
     * this util class.
     *
     * @param carnien Carnien plugin instance.
     */
    public CarnienUtil(Carnien carnien) {
        this.carnien = carnien;
    }

    /**
     * Returns the current Carnien plugin instance.
     *
     * @return Carnien plugin instance.
     */
    protected Carnien getCarnien() {
        return carnien;
    }

}
