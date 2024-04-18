package name.modid.events.packets;

import name.modid.events.Cancellable;
import net.minecraft.network.packet.Packet;

public class PacketEvent {

    public static class Send extends Cancellable {

        private static final Send INSTANCE = new Send();

        public Packet<?> packet;

        public static Send get(Packet<?> packet) {
            INSTANCE.setCancelled(false);
            INSTANCE.packet = packet;
            return INSTANCE;
        }
    }
}
