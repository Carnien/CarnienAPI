package net.carnien.api.input.module.essential.permissionmanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienAdapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PermissionManagerAdapter extends CarnienAdapter {

    public PermissionManagerAdapter(Carnien carnien) {
        super(carnien, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        final PacketContainer packet = event.getPacket();
        final StructureModifier<List<WrappedWatchableObject>> structureModifier = packet.getWatchableCollectionModifier();

        try {
            final List<WrappedWatchableObject> watchableObjects = structureModifier.read(0);

            for (WrappedWatchableObject object : watchableObjects) {
                if (object.getIndex() != 17) continue;

                final String value = object.getValue().toString();
                final UUID uuid = UUID.randomUUID();
                final Optional<UUID> optional = Optional.of(uuid);

                if (value.startsWith("Optional")) object.setValue(optional);
            }
        } catch (IllegalArgumentException ignored) { }
    }

}
