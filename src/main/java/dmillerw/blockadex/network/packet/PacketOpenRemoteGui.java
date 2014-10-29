package dmillerw.blockadex.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.blockadex.handler.ContainerHandler;
import dmillerw.blockadex.network.ServerProxyPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

/**
 * @author dmillerw
 */
public class PacketOpenRemoteGui implements IMessage, IMessageHandler<PacketOpenRemoteGui, IMessage> {

    public int x;
    public int y;
    public int z;

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public IMessage onMessage(PacketOpenRemoteGui message, MessageContext ctx) {
        EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
        Container container = entityPlayerMP.openContainer;
        ServerProxyPlayer proxyPlayer = new ServerProxyPlayer(entityPlayerMP);

        proxyPlayer.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
        proxyPlayer.inventory = entityPlayerMP.inventory;
        proxyPlayer.currentWindowId = entityPlayerMP.currentWindowId;
        proxyPlayer.inventoryContainer = entityPlayerMP.inventoryContainer;
        proxyPlayer.openContainer = entityPlayerMP.openContainer;
        proxyPlayer.worldObj = entityPlayerMP.worldObj;

        Block block = proxyPlayer.worldObj.getBlock(message.x, message.y, message.z);
        if (block != null)
            block.onBlockActivated(proxyPlayer.worldObj, message.x, message.y, message.z, proxyPlayer, 0, 0, 0, 0);

        entityPlayerMP.theItemInWorldManager.thisPlayerMP = entityPlayerMP;
        if (container != proxyPlayer.openContainer) {
            entityPlayerMP.openContainer = proxyPlayer.openContainer;
        }

        ContainerHandler.INSTANCE.containerWhitelist.put(entityPlayerMP.getCommandSenderName(), entityPlayerMP.openContainer);

        return null;
    }
}
