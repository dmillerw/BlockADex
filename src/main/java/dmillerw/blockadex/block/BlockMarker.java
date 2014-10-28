package dmillerw.blockadex.block;

import dmillerw.blockadex.tile.TileMarker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockMarker extends BlockContainer {

    public BlockMarker() {
        super(Material.iron);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileMarker tileMarker = (TileMarker) world.getTileEntity(x, y, z);
        tileMarker.unregister();
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMarker();
    }
}
