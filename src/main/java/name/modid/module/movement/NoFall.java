package name.modid.module.movement;

import name.modid.events.EventHandler;
import name.modid.events.packets.PacketEvent;
import name.modid.mixin.PlayerMoveC2SPacketAccessor;
import net.minecraft.client.network.ClientPlayerEntity;
import name.modid.module.ModuleManager;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import name.modid.module.Mod;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Mod {

    public NoFall() {
        super("NoFall", "NoFall", "Attempts to cancel your fall damage", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_N);
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        ClientPlayerEntity player = mc.player;

        if (mc.player.getAbilities().creativeMode || !(event.packet instanceof PlayerMoveC2SPacket)) return;

        if (!ModuleManager.INSTANCE.getModule(Flight.class).isEnabled()) {
            if (mc.player.isFallFlying()) return;
            if (mc.player.getVelocity().y > -0.5) return;
            ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(true);
        } else {
            ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(true);
        }
    }
}
