package dmillerw.blockadex.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.blockadex.client.gui.GuiBlockADex;
import dmillerw.blockadex.inventory.ContainerNull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_BLOCK_A_DEX = 0;
    public static final int GUI_MARKER = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUI_BLOCK_A_DEX: return new ContainerNull();
            case GUI_MARKER: return new ContainerNull();
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUI_BLOCK_A_DEX: return new GuiBlockADex();
            default: return null;
        }
    }
}
