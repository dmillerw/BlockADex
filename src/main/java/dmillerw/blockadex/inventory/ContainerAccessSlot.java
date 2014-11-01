package dmillerw.blockadex.inventory;

import dmillerw.blockadex.BlockADex;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class ContainerAccessSlot extends Container {

    public ContainerAccessSlot(EntityPlayer entityPlayer, TileBlockADex tileBlockADex, int slot) {
        this.addSlotToContainer(new Slot(tileBlockADex.internalInventory, slot, 80, 35) {
            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() == BlockADex.chip;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
