package name.modid.module.movement;

import name.modid.module.Mod;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {

    public Flight() {
        super("Flight", "Flight", "Allows you to fly", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
    }

//    @Override
//    public void onTick() {
//        mc.player.getAbilities().flying = true;
//        super.onTick();
//    }
//
//    @Override
//    public void onEnable() {
//        mc.player.getAbilities().setFlySpeed(0.1F);
//        super.onEnable();
//    }
//
//    @Override
//    public void onDisable() {
//        mc.player.getAbilities().setFlySpeed(0.05F);
//        mc.player.getAbilities().flying = false;
//        //fly up one tick to prevent fall damage
//        //mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z);
//        super.onDisable();
//    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = mc.player;

        player.getAbilities().flying = false;

        player.setVelocity(0, 0, 0);
        Vec3d velocity = player.getVelocity();

        if(mc.options.jumpKey.isPressed())
            player.setVelocity(velocity.x, 10, velocity.z);

        if(mc.options.sneakKey.isPressed())
            player.setVelocity(velocity.x, -10, velocity.z);
        super.onTick();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
