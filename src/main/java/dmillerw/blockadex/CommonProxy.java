package dmillerw.blockadex;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.blockadex.block.BlockBlockADex;
import dmillerw.blockadex.block.BlockMarker;
import dmillerw.blockadex.block.ItemBlockMarker;
import dmillerw.blockadex.handler.ContainerHandler;
import dmillerw.blockadex.handler.GuiHandler;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.tile.TileBlockADex;
import dmillerw.blockadex.tile.TileMarker;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        BlockADex.blockADex = new BlockBlockADex().setBlockName("blockADex");
        BlockADex.marker = new BlockMarker().setBlockName("marker");
        GameRegistry.registerBlock(BlockADex.blockADex, "blockADex");
        GameRegistry.registerBlock(BlockADex.marker, ItemBlockMarker.class, "marker");
        GameRegistry.registerTileEntity(TileBlockADex.class, "BlockADex:blockADex");
        GameRegistry.registerTileEntity(TileMarker.class, "BlockADex:marker");

        PacketHandler.initialize();

        NetworkRegistry.INSTANCE.registerGuiHandler(BlockADex.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(ContainerHandler.INSTANCE);
    }
}
