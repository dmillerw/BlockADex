package dmillerw.blockadex.network.packet;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.blockadex.client.gui.GuiBlockADex;
import dmillerw.blockadex.lib.BlockIndexData;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.tile.TileMarker;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.io.IOException;
import java.util.List;

/**
 * @author dmillerw
 */
public class PacketUpdateMarkerRegistry implements IMessage, IMessageHandler<PacketUpdateMarkerRegistry, IMessage> {

    public static void sendRegistry(EntityPlayerMP entityPlayerMP) {
        PacketUpdateMarkerRegistry packetUpdateMarkerRegistry = new PacketUpdateMarkerRegistry();
        for (TileMarker tileMarker : TileMarker.tileRegistry) {
            if (tileMarker == null)
                continue;

            if (tileMarker.forgeDirection == ForgeDirection.UNKNOWN)
                continue;

            int dx = tileMarker.xCoord + tileMarker.forgeDirection.offsetX;
            int dy = tileMarker.yCoord + tileMarker.forgeDirection.offsetY;
            int dz = tileMarker.zCoord + tileMarker.forgeDirection.offsetZ;

            Block block = tileMarker.getWorldObj().getBlock(dx, dy, dz);
            int meta = tileMarker.getWorldObj().getBlockMetadata(dx, dy, dz);

            if (block == null)
                continue;

            // TODO Better icon handling
            ItemStack icon = new ItemStack(block, 1, meta);

            if (icon.getItem() == null)
                continue;

            packetUpdateMarkerRegistry.blockIndexDataList.add(new BlockIndexData(dx, dy, dz, "", icon));
        }
        PacketHandler.INSTANCE.sendTo(packetUpdateMarkerRegistry, entityPlayerMP);
    }

    public List<BlockIndexData> blockIndexDataList = Lists.newArrayList();

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(blockIndexDataList.size());
        for (BlockIndexData blockIndexData : blockIndexDataList) {
            try {
                blockIndexData.toBytes(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        for (int i=0; i<size; i++) {
            try {
                blockIndexDataList.add(BlockIndexData.fromBuffer(buf));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IMessage onMessage(PacketUpdateMarkerRegistry message, MessageContext ctx) {
        GuiBlockADex.clientIndexData.clear();
        GuiBlockADex.clientIndexData.addAll(message.blockIndexDataList);
        return null;
    }
}
