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

        player.networkHandler.sendPacket(new OnGroundOnly(true));

        super.onTick();
    }

}
