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
            if (tileMarker.forgeDirection == ForgeDirection.UNKNOWN)
                continue;

            Block block = tileMarker.getWorldObj().getBlock(tileMarker.xCoord + tileMarker.forgeDirection.offsetX,
                                                            tileMarker.yCoord + tileMarker.forgeDirection.offsetY,
                                                            tileMarker.zCoord + tileMarker.forgeDirection.offsetZ);

            int meta = tileMarker.getWorldObj().getBlockMetadata(tileMarker.xCoord + tileMarker.forgeDirection.offsetX,
                                                                 tileMarker.yCoord + tileMarker.forgeDirection.offsetY,
                                                                 tileMarker.zCoord + tileMarker.forgeDirection.offsetZ);

            if (block == null)
                continue;

            // TODO Better icon handling
            ItemStack icon = new ItemStack(block, 1, meta);

            packetUpdateMarkerRegistry.blockIndexDataList.add(
                    new BlockIndexData(tileMarker.xCoord + tileMarker.forgeDirection.offsetX,
                    tileMarker.yCoord + tileMarker.forgeDirection.offsetY,
                    tileMarker.zCoord + tileMarker.forgeDirection.offsetZ,
                    "",
                    icon)
            );
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
            } catch (IOException e) {}
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        for (int i=0; i<size; i++) {
            try {
                blockIndexDataList.add(BlockIndexData.fromBuffer(buf));
            } catch (IOException e) {}
        }
    }

    @Override
    public IMessage onMessage(PacketUpdateMarkerRegistry message, MessageContext ctx) {
        GuiBlockADex.clientIndexData.clear();
        GuiBlockADex.clientIndexData.addAll(message.blockIndexDataList);
        return null;
    }
}
