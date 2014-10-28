package dmillerw.blockadex.network.packet;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
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
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) ctx.getServerHandler().playerEntity;
        Container container = entityPlayerMP.openContainer;
        ProxiedPlayer proxyPlayer = new ProxiedPlayer(entityPlayerMP);

        proxyPlayer.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
        proxyPlayer.inventory = entityPlayerMP.inventory;
        proxyPlayer.currentWindowId = entityPlayerMP.currentWindowId;
        proxyPlayer.inventoryContainer = entityPlayerMP.inventoryContainer;
        proxyPlayer.openContainer = entityPlayerMP.openContainer;
        proxyPlayer.worldObj = entityPlayerMP.worldObj;

        entityPlayerMP.theItemInWorldManager.activateBlockOrUseItem(proxyPlayer, entityPlayerMP.worldObj, null, message.x, message.y, message.z, 0, 0, 0, 0);
        entityPlayerMP.theItemInWorldManager.thisPlayerMP = entityPlayerMP;
        if (container != proxyPlayer.openContainer) {
            entityPlayerMP.openContainer = proxyPlayer.openContainer;
        }

        return null;
    }
}
