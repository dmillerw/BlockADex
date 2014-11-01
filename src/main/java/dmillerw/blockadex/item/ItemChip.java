package dmillerw.blockadex.item;

import dmillerw.blockadex.lib.BlockIndexData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemChip extends Item {

    public ItemChip() {
        super();

        setCreativeTab(CreativeTabs.tabRedstone);
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }

        if (player.isSneaking()) {
            BlockIndexData blockIndexData = new BlockIndexData(world, x, y, z, new ItemStack(Item.getItemFromBlock(world.getBlock(x, y, z)), 1, world.getBlockMetadata(x, y, z)));
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            blockIndexData.toNBT(nbtTagCompound);
            stack.setTagCompound(nbtTagCompound);

            return true;
        } else {
            return false;
        }
    }
}
