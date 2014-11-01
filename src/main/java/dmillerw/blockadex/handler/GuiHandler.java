package dmillerw.blockadex.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.blockadex.client.gui.GuiAccessSlot;
import dmillerw.blockadex.client.gui.GuiBlockADex;
import dmillerw.blockadex.inventory.ContainerAccessSlot;
import dmillerw.blockadex.inventory.ContainerBlockADex;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_BLOCK_A_DEX = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {
            return new ContainerBlockADex(player, (TileBlockADex) world.getTileEntity(x, y, z));
        } else if (id >= 10) {
            int slot = Math.min(7, id - 10);
            return new ContainerAccessSlot(player, (TileBlockADex) world.getTileEntity(x, y, z), slot);
        } else {
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) {
            return new GuiBlockADex(player, (TileBlockADex) world.getTileEntity(x, y, z));
        } else if (id >= 10) {
            int slot = Math.min(7, id - 10);
            return new GuiAccessSlot(player, (TileBlockADex) world.getTileEntity(x, y, z), slot);
        } else {
            return null;
        }
    }
}
