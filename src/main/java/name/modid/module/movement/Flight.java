package name.modid.module.movement;

import name.modid.events.packets.PacketEvent;
import name.modid.mixin.ClientPlayerEntityAccessor;
import name.modid.mixin.PlayerMoveC2SPacketAccessor;
import name.modid.module.Mod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {

    private final int delayTime = 20; // Delay in ticks to fly down&up
    private int delayLeft = delayTime;
    private final int offTime = 1; // Delay in ms to fly down
    private int offLeft = offTime;
    private boolean swap;
    private float lastYaw;
    private double lastPacketY = Double.MAX_VALUE;

    public Flight() {
        super("Flight", "Flight", "Allows you to fly", Category.MOVEMENT);
        this.setKey(GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        super.onTick();
        
        float currentYaw = mc.player.getYaw();
        if (mc.player.fallDistance >= 3F && currentYaw == lastYaw && mc.player.getVelocity().length() < 0.003D) {
            mc.player.setYaw(currentYaw + (swap ? 1 : -1));
            swap = !swap;
        }
        lastYaw = currentYaw;

        if (delayLeft > 0) delayLeft--;

        if (offLeft <= 0 && delayLeft <= 0) {
            delayLeft = delayTime;
            offLeft = offTime;

            ((ClientPlayerEntityAccessor) mc.player).setTicksSinceLastPositionPacketSent(20);
        } else if (delayLeft <= 0) {

             if (offLeft == offTime) {
                ((ClientPlayerEntityAccessor) mc.player).setTicksSinceLastPositionPacketSent(20);
            }

            offLeft--;

        }

        if (mc.player.getYaw() != lastYaw) mc.player.setYaw(lastYaw);

        if (mc.player.isSpectator()) return;
        mc.player.getAbilities().setFlySpeed(0.1F);
        mc.player.getAbilities().flying = true;
        if (mc.player.getAbilities().creativeMode) return;
        mc.player.getAbilities().allowFlying = true;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.player.getAbilities().flying = true;
        if (mc.player.getAbilities().creativeMode) return;
        mc.player.getAbilities().allowFlying = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().setFlySpeed(0.05F);
        if (mc.player.getAbilities().creativeMode) return;
        mc.player.getAbilities().allowFlying = false;
    }

    private void antiKickPacket(PlayerMoveC2SPacket packet, double currentY) {
        if (this.delayLeft <= 0 && this.lastPacketY != Double.MAX_VALUE &&
                shouldFlyDown(currentY, this.lastPacketY) && isEntityOnAir(mc.player)) {
            // actual check is for >= -0.03125D, but we have to do a bit more than that
            // due to the fact that it's a bigger or *equal* to, and not just a bigger than
            ((PlayerMoveC2SPacketAccessor) packet).setY(lastPacketY - 0.03130D);
        } else {
            lastPacketY = currentY;
        }
    }

    // ServerPlayNetworkHandler#onPlayerMove(PlayerMoveC2SPacket)
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof PlayerMoveC2SPacket packet)) return;

        double currentY = packet.getY(Double.MAX_VALUE);
        if (currentY != Double.MAX_VALUE) {
            antiKickPacket(packet, currentY);
        } else {
            // when packet is a LookAndOnGround or an OnGroundOnly, it must make it a Full packet or a PositionAndOnGround packet, so it has a y value
            PlayerMoveC2SPacket fullPacket;
            if (packet.changesLook()) {
                fullPacket = new PlayerMoveC2SPacket.Full(
                        mc.player.getX(),
                        mc.player.getY(),
                        mc.player.getZ(),
                        packet.getYaw(0),
                        packet.getPitch(0),
                        packet.isOnGround()
                );
            } else {
                fullPacket = new PlayerMoveC2SPacket.PositionAndOnGround(
                        mc.player.getX(),
                        mc.player.getY(),
                        mc.player.getZ(),
                        packet.isOnGround()
                );
            }
            event.cancel();
            antiKickPacket(fullPacket, mc.player.getY());
            mc.getNetworkHandler().sendPacket(fullPacket);
        }
    }

    private boolean shouldFlyDown(double currentY, double lastY) {
        if (currentY >= lastY) {
            return true;
        } else return lastY - currentY < 0.03130D;
    }

    private boolean isEntityOnAir(Entity entity) {
        return entity.getWorld().getStatesInBox(entity.getBoundingBox().expand(0.0625).stretch(0.0, -0.55, 0.0)).allMatch(AbstractBlock.AbstractBlockState::isAir);
    }
}
