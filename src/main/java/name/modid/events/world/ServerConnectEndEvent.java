package name.modid.events.world;

import java.net.InetSocketAddress;

// Gets the server address and sets it to INSTANCE.address
public class ServerConnectEndEvent {

    private static final ServerConnectEndEvent INSTANCE = new ServerConnectEndEvent();
    public InetSocketAddress address;

    public static ServerConnectEndEvent get(InetSocketAddress address) {
        INSTANCE.address = address;
        return INSTANCE;
    }
}
