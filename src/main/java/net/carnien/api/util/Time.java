package net.carnien.api.util;

import org.bukkit.GameMode;

public enum Time {

    DAY(6000),
    NIGHT(18000);

    private final int value;

    Time(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
