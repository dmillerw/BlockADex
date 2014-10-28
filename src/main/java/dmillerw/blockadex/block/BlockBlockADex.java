package dmillerw.blockadex.block;

import dmillerw.blockadex.client.gui.GuiBlockADex;
import dmillerw.blockadex.lib.BlockIndexData;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.network.packet.PacketOpenRemoteGui;
import dmillerw.blockadex.network.packet.PacketUpdateMarkerRegistry;
import dmillerw.blockadex.tile.TileBlockADex;
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

    public BlockBlockADex() {
        super(Material.iron);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float fx, float fy, float fz) {
        if (!world.isRemote) {
            PacketUpdateMarkerRegistry.sendRegistry((EntityPlayerMP) entityPlayer);
        }
//        entityPlayer.openGui(BlockADex.instance, GuiHandler.GUI_BLOCK_A_DEX, world, x, y, z);

        if (GuiBlockADex.clientIndexData.isEmpty())
            return true;

        BlockIndexData blockIndexData = GuiBlockADex.clientIndexData.get(0);
        PacketOpenRemoteGui packetOpenRemoteGui = new PacketOpenRemoteGui();
        packetOpenRemoteGui.x = blockIndexData.x;
        packetOpenRemoteGui.y = blockIndexData.y;
        packetOpenRemoteGui.z = blockIndexData.z;
        PacketHandler.INSTANCE.sendToServer(packetOpenRemoteGui);

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBlockADex();
    }
}
