package dmillerw.blockadex.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.blockadex.network.packet.PacketAllowContainer;
import dmillerw.blockadex.network.packet.PacketOpenRemoteGui;
import dmillerw.blockadex.network.packet.PacketUpdateMarkerRegistry;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("BlockADex");

    public static void initialize() {
        INSTANCE.registerMessage(PacketOpenRemoteGui.class, PacketOpenRemoteGui.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketUpdateMarkerRegistry.class, PacketUpdateMarkerRegistry.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(PacketAllowContainer.class, PacketAllowContainer.class, 2, Side.CLIENT);
    }
}
