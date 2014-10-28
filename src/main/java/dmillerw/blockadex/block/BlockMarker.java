package dmillerw.blockadex.block;

import dmillerw.blockadex.tile.TileMarker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static net.minecraftforge.common.util.ForgeDirection.*;

/**
 * @author dmillerw
 */
public class BlockMarker extends BlockContainer {

    public BlockMarker() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMarker();
    }
}
