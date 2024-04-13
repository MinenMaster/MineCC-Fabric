package name.modid.module.movement;

import name.modid.module.Mod;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        super.onTick();
    }

    @Override
    public void onEnable() {
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().setFlySpeed(0.1F);
        mc.player.getAbilities().flying = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.player.getAbilities().allowFlying = false;
        mc.player.getAbilities().setFlySpeed(0.05F);
        mc.player.getAbilities().flying = false;
        super.onDisable();
    }
}