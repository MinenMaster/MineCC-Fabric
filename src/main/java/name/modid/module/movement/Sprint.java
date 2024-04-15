package name.modid.module.movement;

import name.modid.module.Mod;
import org.lwjgl.glfw.GLFW;

public class Sprint extends Mod {

    public Sprint() {
        super("Sprint", "Sprint", "Automatically sprints", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_LEFT_CONTROL);
    }

    @Override
    public void onTick() {
        mc.player.setSprinting(true);
        super.onTick();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.player.setSprinting(false);
        super.onDisable();
    }
}
