package name.modid.mixin;

import io.netty.channel.ChannelPipeline;
import name.modid.NovaClient;
import name.modid.events.world.ServerConnectEndEvent;
import name.modid.events.packets.PacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.handler.PacketSizeLogger;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.InetSocketAddress;
import java.util.Iterator;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if (packet instanceof BundleS2CPacket bundle) {
            for (Iterator<Packet<ClientPlayPacketListener>> it = bundle.getPackets().iterator(); it.hasNext(); ) {
                if (NovaClient.EVENT_BUS.post(PacketEvent.Receive.get(it.next(), listener)).isCancelled()) it.remove();
            }
        } else if (NovaClient.EVENT_BUS.post(PacketEvent.Receive.get(packet, listener)).isCancelled()) info.cancel();
    }

    @Inject(method = "connect(Ljava/net/InetSocketAddress;ZLnet/minecraft/network/ClientConnection;)Lio/netty/channel/ChannelFuture;", at = @At("HEAD"))
    private static void onConnect(InetSocketAddress address, boolean useEpoll, ClientConnection connection, CallbackInfoReturnable<?> cir) {
        NovaClient.EVENT_BUS.post(ServerConnectEndEvent.get(address));
    }

    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    private void onSendPacketHead(Packet<?> packet, CallbackInfo info) {
        if (NovaClient.EVENT_BUS.post(PacketEvent.Send.get(packet)).isCancelled()) info.cancel();
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("TAIL"))
    private void onSendPacketTail(Packet<?> packet, CallbackInfo info) {
        NovaClient.EVENT_BUS.post(PacketEvent.Sent.get(packet));
    }

    @Inject(method = "addHandlers", at = @At("RETURN"))
    private static void onAddHandlers(ChannelPipeline pipeline, NetworkSide side, PacketSizeLogger packetSizeLogger, CallbackInfo ci) {
        if (side != NetworkSide.CLIENTBOUND) return;
    }
}
