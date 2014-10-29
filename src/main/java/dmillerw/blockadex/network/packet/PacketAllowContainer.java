package dmillerw.blockadex.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.blockadex.handler.ContainerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

/**
 * @author dmillerw
 */
public class PacketAllowContainer implements IMessage, IMessageHandler<PacketAllowContainer, IMessage> {

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketAllowContainer message, MessageContext ctx) {
        ContainerHandler.INSTANCE.containerWhitelist.put(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), Minecraft.getMinecraft().thePlayer.openContainer);
        return null;
    }
}
