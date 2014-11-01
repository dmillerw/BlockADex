package dmillerw.blockadex.block;

import dmillerw.blockadex.BlockADex;
import dmillerw.blockadex.handler.GuiHandler;
import dmillerw.blockadex.lib.BlockIndexData;
import dmillerw.blockadex.network.ClientProxyPlayer;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.network.packet.PacketOpenRemoteGUI;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockBlockADex extends BlockContainer {

    public static void activateBlock(EntityPlayer entityPlayer, BlockIndexData blockIndexData) {
        PacketOpenRemoteGUI packetOpenRemoteGUI = new PacketOpenRemoteGUI();
        packetOpenRemoteGUI.x = blockIndexData.x;
        packetOpenRemoteGUI.y = blockIndexData.y;
        packetOpenRemoteGUI.z = blockIndexData.z;
        PacketHandler.INSTANCE.sendToServer(packetOpenRemoteGUI);

        ClientProxyPlayer proxyPlayer = new ClientProxyPlayer((EntityClientPlayerMP) entityPlayer);
        proxyPlayer.inventory = entityPlayer.inventory;
        proxyPlayer.inventoryContainer = entityPlayer.inventoryContainer;
        proxyPlayer.openContainer = entityPlayer.openContainer;

        Block block = entityPlayer.worldObj.getBlock(packetOpenRemoteGUI.x, packetOpenRemoteGUI.y, packetOpenRemoteGUI.z);
        if (block != null)
            block.onBlockActivated(entityPlayer.worldObj, packetOpenRemoteGUI.x, packetOpenRemoteGUI.y, packetOpenRemoteGUI.z, new ClientProxyPlayer((EntityClientPlayerMP) entityPlayer), 0, 0, 0, 0);

        if (entityPlayer.openContainer != proxyPlayer.openContainer) {
            entityPlayer.openContainer = proxyPlayer.openContainer;
        }
    }

    public BlockBlockADex() {
        super(Material.iron);
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float fx, float fy, float fz) {
        entityPlayer.openGui(BlockADex.instance, GuiHandler.GUI_BLOCK_A_DEX, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBlockADex();
    }
}
