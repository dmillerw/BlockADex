package dmillerw.blockadex.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * @author dmillerw
 */
public class ContainerNull extends Container {

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
