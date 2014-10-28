package dmillerw.blockadex;

/**
 * @author dmillerw
 */

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;

@Mod(modid = "BlockADex", name = "BlockADex", version="%MOD_VERSION%")
public class BlockADex {

    @Mod.Instance("BlockADex")
    public static BlockADex instance;

    @SidedProxy(serverSide = "dmillerw.blockadex.CommonProxy", clientSide = "dmillerw.blockadex.ClientProxy")
    public static CommonProxy proxy;

    public static Block blockADex;
    public static Block marker;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}
