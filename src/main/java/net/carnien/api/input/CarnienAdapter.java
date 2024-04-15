package net.carnien.api.input;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import net.carnien.api.Carnien;

public class CarnienAdapter extends PacketAdapter {

    private Carnien carnien;

    public CarnienAdapter(Carnien carnien, ListenerPriority listenerPriority, PacketType type) {
        super(carnien, listenerPriority, type);
        this.carnien = carnien;
    }

    protected Carnien getCarnien() {
        return carnien;
    }

}
