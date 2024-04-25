package name.modid.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin extends ClientCommonNetworkHandlerMixin {

    public ClientPlayNetworkHandlerMixin(MinecraftClient client, ClientConnection clientConnection, ClientConnectionState clientConnectionState) {
        super(client, clientConnection, clientConnectionState);
    }

    public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        PlayerEntity playerEntity = this.client.player;
        Vec3d vec3d = playerEntity.getVelocity();
        boolean bl = packet.getFlags().contains(PositionFlag.X);
        boolean bl2 = packet.getFlags().contains(PositionFlag.Y);
        boolean bl3 = packet.getFlags().contains(PositionFlag.Z);
        double d;
        double e;
        if (bl) {
            d = vec3d.getX();
            e = playerEntity.getX() + packet.getX();
            playerEntity.lastRenderX += packet.getX();
            playerEntity.prevX += packet.getX();
        } else {
            d = 0.0;
            e = packet.getX();
            playerEntity.lastRenderX = e;
            playerEntity.prevX = e;
        }

        double f;
        double g;
        if (bl2) {
            f = vec3d.getY();
            g = playerEntity.getY() + packet.getY();
            playerEntity.lastRenderY += packet.getY();
            playerEntity.prevY += packet.getY();
        } else {
            f = 0.0;
            g = packet.getY();
            playerEntity.lastRenderY = g;
            playerEntity.prevY = g;
        }

        double h;
        double i;
        if (bl3) {
            h = vec3d.getZ();
            i = playerEntity.getZ() + packet.getZ();
            playerEntity.lastRenderZ += packet.getZ();
            playerEntity.prevZ += packet.getZ();
        } else {
            h = 0.0;
            i = packet.getZ();
            playerEntity.lastRenderZ = i;
            playerEntity.prevZ = i;
        }

        playerEntity.setPosition(e, g, i);
        playerEntity.setVelocity(d, f, h);
        float j = packet.getYaw();
        float k = packet.getPitch();
        if (packet.getFlags().contains(PositionFlag.X_ROT)) {
            playerEntity.setPitch(playerEntity.getPitch() + k);
            playerEntity.prevPitch += k;
        } else {
            playerEntity.setPitch(k);
            playerEntity.prevPitch = k;
        }

        if (packet.getFlags().contains(PositionFlag.Y_ROT)) {
            playerEntity.setYaw(playerEntity.getYaw() + j);
            playerEntity.prevYaw += j;
        } else {
            playerEntity.setYaw(j);
            playerEntity.prevYaw = j;
        }

        this.connection.send(new TeleportConfirmC2SPacket(packet.getTeleportId()));
        this.connection.send(new PlayerMoveC2SPacket.Full(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch(), false));
    }
}
