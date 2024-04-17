package name.modid.module.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.client.network.ClientPlayerEntity;
import name.modid.module.Mod;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Mod {

    public NoFall() {
        super("NoFall", "NoFall", "Attempts to cancel your fall damage", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_N);
    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = mc.player;

        // do nothing in creative mode
        if (player.isCreative()) {
            return;
        }

        // pause when flying with elytra
        boolean fallFlying = player.isFallFlying();
        if (fallFlying) {
            return;
        }

        // ignore small falls that can't cause damage
        if (player.fallDistance <= (fallFlying ? 1 : 2))
            return;

        player.fallDistance = 0;
        player.networkHandler.sendPacket(new OnGroundOnly(true));

        super.onTick();
    }

}
