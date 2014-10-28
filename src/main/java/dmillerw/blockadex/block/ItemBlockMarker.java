package dmillerw.blockadex.block;

import dmillerw.blockadex.tile.TileMarker;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class ItemBlockMarker extends ItemBlock {

    public ItemBlockMarker(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (result) {
            TileMarker tileMarker = (TileMarker) world.getTileEntity(x, y, z);
            if (tileMarker != null) {
                tileMarker.forgeDirection = ForgeDirection.getOrientation(side).getOpposite();
            }
        }
        return result;
    }
}
