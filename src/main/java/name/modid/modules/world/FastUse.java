package name.modid.modules.world;

import name.modid.modules.Mod;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class FastUse extends Mod {

    public FastUse() {
        super("FastUse", "FastUse", "Removes the itemUseCooldown", Category.WORLD);
        this.setKey(GLFW.GLFW_KEY_LEFT_CONTROL);
    }

    public int getItemUseCooldown(ItemStack itemStack) {
        if (isEnabled()) {
            return 0;
        }
        return 4; //default cooldown
    }

}
