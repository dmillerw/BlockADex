package dmillerw.blockadex.block;

import dmillerw.blockadex.BlockADex;
import dmillerw.blockadex.client.gui.GuiBlockADex;
import dmillerw.blockadex.handler.GuiHandler;
import dmillerw.blockadex.lib.BlockIndexData;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.network.packet.PacketOpenRemoteGui;
import dmillerw.blockadex.network.packet.PacketUpdateMarkerRegistry;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockBlockADex extends BlockContainer {

    public static void activateBlock(EntityPlayer entityPlayer, int index) {
        if (GuiBlockADex.clientIndexData.isEmpty())
            return;

        if (index >= GuiBlockADex.clientIndexData.size())
            return;

        BlockIndexData blockIndexData = GuiBlockADex.clientIndexData.get(index);
        PacketOpenRemoteGui packetOpenRemoteGui = new PacketOpenRemoteGui();
        packetOpenRemoteGui.x = blockIndexData.x;
        packetOpenRemoteGui.y = blockIndexData.y;
        packetOpenRemoteGui.z = blockIndexData.z;
        PacketHandler.INSTANCE.sendToServer(packetOpenRemoteGui);

        Block block = entityPlayer.worldObj.getBlock(packetOpenRemoteGui.x, packetOpenRemoteGui.y, packetOpenRemoteGui.z);
        if (block != null)
            block.onBlockActivated(entityPlayer.worldObj, packetOpenRemoteGui.x, packetOpenRemoteGui.y, packetOpenRemoteGui.z, entityPlayer, 0, 0, 0, 0);
    }

    public BlockBlockADex() {
        super(Material.iron);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float fx, float fy, float fz) {
        if (!world.isRemote) {
            PacketUpdateMarkerRegistry.sendRegistry((EntityPlayerMP) entityPlayer);
        }

        entityPlayer.openGui(BlockADex.instance, GuiHandler.GUI_BLOCK_A_DEX, world, x, y, z);

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBlockADex();
    }
}
