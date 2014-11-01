package dmillerw.blockadex.tile;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * @author dmillerw
 */
public class TileBlockADex extends TileCore {

    public InventoryBasic internalInventory = new InventoryBasic("", false, 8);

    @Override
    public void writeCustomNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i=0; i<8; i++) {
            ItemStack itemStack = internalInventory.getStackInSlot(i);

            if (itemStack != null) {
                NBTTagCompound nbtTagCompound1 = new NBTTagCompound();
                itemStack.writeToNBT(nbtTagCompound1);
                nbtTagCompound1.setInteger("slot", i);
                nbtTagList.appendTag(nbtTagCompound1);
            }
        }
        nbtTagCompound.setTag("inventory", nbtTagList);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbtTagCompound) {
        internalInventory = new InventoryBasic("", false, 8);
        NBTTagList nbtTagList = nbtTagCompound.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
        for (int i=0; i<nbtTagList.tagCount(); i++) {
            NBTTagCompound nbtTagCompound1 = nbtTagList.getCompoundTagAt(i);
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbtTagCompound1);
            internalInventory.setInventorySlotContents(nbtTagCompound1.getInteger("slot"), itemStack);
        }
    }
}
