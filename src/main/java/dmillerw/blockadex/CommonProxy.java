package dmillerw.blockadex;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.blockadex.block.BlockBlockADex;
import dmillerw.blockadex.handler.ContainerHandler;
import dmillerw.blockadex.handler.GuiHandler;
import dmillerw.blockadex.item.ItemChip;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        BlockADex.blockADex = new BlockBlockADex().setBlockName("blockADex");
        GameRegistry.registerBlock(BlockADex.blockADex, "blockADex");
        GameRegistry.registerTileEntity(TileBlockADex.class, "BlockADex:blockADex");

        BlockADex.chip = new ItemChip().setUnlocalizedName("chip");
        GameRegistry.registerItem(BlockADex.chip, "chip");

        PacketHandler.initialize();

        NetworkRegistry.INSTANCE.registerGuiHandler(BlockADex.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(ContainerHandler.INSTANCE);
    }
}
