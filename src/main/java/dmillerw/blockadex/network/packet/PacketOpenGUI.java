package dmillerw.blockadex.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.blockadex.BlockADex;
import io.netty.buffer.ByteBuf;

/**
 * @author dmillerw
 */
public class PacketOpenGUI implements IMessage, IMessageHandler<PacketOpenGUI, IMessage> {

    public int x;
    public int y;
    public int z;
    public int id;

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(id);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        id = buf.readInt();
    }

    @Override
    public IMessage onMessage(PacketOpenGUI message, MessageContext ctx) {
        ctx.getServerHandler().playerEntity.openGui(BlockADex.instance, message.id, ctx.getServerHandler().playerEntity.worldObj, message.x, message.y, message.z);
        return null;
    }
}
