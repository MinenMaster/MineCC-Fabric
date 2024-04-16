package name.modid.module.movement;

import name.modid.module.Mod;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {

    public Flight() {
        super("Flight", "Flight", "Allows you to fly", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        mc.player.getAbilities().flying = true;
        super.onTick();
    }

    @Override
    public void onEnable() {
        mc.player.getAbilities().setFlySpeed(0.1F);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (!mc.player.isSpectator()) {
            mc.player.getAbilities().setFlySpeed(0.05F);
            mc.player.getAbilities().flying = false;
        }
        super.onDisable();
    }
}
