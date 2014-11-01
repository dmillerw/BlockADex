package dmillerw.blockadex.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.blockadex.network.packet.PacketOpenGUI;
import dmillerw.blockadex.network.packet.PacketAllowContainer;
import dmillerw.blockadex.network.packet.PacketOpenRemoteGUI;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("BlockADex");

    public static void initialize() {
        INSTANCE.registerMessage(PacketOpenRemoteGUI.class, PacketOpenRemoteGUI.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketAllowContainer.class, PacketAllowContainer.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(PacketOpenGUI.class, PacketOpenGUI.class, 2, Side.SERVER);
    }
}
