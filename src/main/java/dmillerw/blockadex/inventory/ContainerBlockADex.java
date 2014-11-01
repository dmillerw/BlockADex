package dmillerw.blockadex.inventory;

import dmillerw.blockadex.BlockADex;
import dmillerw.blockadex.lib.BlockIndexData;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class ContainerBlockADex extends Container {

    public BlockIndexData[] blockDataCache = new BlockIndexData[8];

    private final EntityPlayer entityPlayer;
    private final TileBlockADex tileBlockADex;

    public ContainerBlockADex(EntityPlayer entityPlayer, TileBlockADex tileBlockADex) {
        this.entityPlayer = entityPlayer;
        this.tileBlockADex = tileBlockADex;

        for (int i=0; i<8; i++) {
            this.addSlotToContainer(new Slot(tileBlockADex.internalInventory, i, Integer.MIN_VALUE, Integer.MIN_VALUE) {
                @Override
                public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
                    super.onSlotChange(p_75220_1_, p_75220_2_);
                    ContainerBlockADex.this.recalculateCache();
                }

                @Override
                public boolean canTakeStack(EntityPlayer p_82869_1_) {
                    return false;
                }
            });
        }

        recalculateCache();
    }

    protected void recalculateCache() {
        blockDataCache = new BlockIndexData[8];
        for (int i=0; i<8; i++) {
            ItemStack itemStack = tileBlockADex.internalInventory.getStackInSlot(i);

            if (itemStack != null && itemStack.getItem() == BlockADex.chip) {
                blockDataCache[i] = BlockIndexData.fromNBT(itemStack.getTagCompound());
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
