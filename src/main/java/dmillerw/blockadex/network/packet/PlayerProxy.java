package dmillerw.blockadex.network.packet;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import dmillerw.blockadex.inventory.ContainerWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.world.WorldServer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author dmillerw
 */
public class PlayerProxy implements MethodInterceptor {

    public static <T> T createPlayerProxy(EntityPlayerMP entityPlayerMP, PlayerProxy playerProxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(playerProxy);
        enhancer.setSuperclass(entityPlayerMP.getClass());
        enhancer.setInterceptDuringConstruction(true);

        Class<?>[] argTypes = new Class[]{MinecraftServer.class, WorldServer.class, GameProfile.class, ItemInWorldManager.class};
        Object[] args = new Object[]{entityPlayerMP.mcServer, entityPlayerMP.worldObj, entityPlayerMP.getGameProfile(), entityPlayerMP.theItemInWorldManager};

        @SuppressWarnings("unchecked")
        T proxy = (T) enhancer.create(argTypes, args);
        return proxy;
    }

    private EntityPlayerMP parentPlayer;

    private int x;
    private int y;
    private int z;

    private Set<String> interceptNames = Sets.newHashSet();
    private Set<String> beaconNames = Sets.newHashSet();
    private Set<String> anvilNames = Sets.newHashSet();
    private Set<String> distanceNames = Sets.newHashSet();

    public PlayerProxy(EntityPlayerMP entityPlayerMP, int x, int y, int z) {
        this.parentPlayer = entityPlayerMP;
        this.x = x;
        this.y = y;
        this.z = z;

        interceptNames.add("openGui");
        interceptNames.add("func_146100_a");

        interceptNames.add("displayGUIBrewingStand");
        interceptNames.add("func_71017_a");
        interceptNames.add("func_146098_a");

        interceptNames.add("displayGUIChest");
        interceptNames.add("func_71007_a");

        interceptNames.add("displayGUIDispenser");
        interceptNames.add("func_71006_a");
        interceptNames.add("func_146102_a");

        interceptNames.add("displayGUIEnchantment");
        interceptNames.add("func_71002_c");

        interceptNames.add("displayGUIFurnace");
        interceptNames.add("func_71042_a");
        interceptNames.add("func_146101_a");

        interceptNames.add("displayGUIWorkbench");
        interceptNames.add("func_71058_b");

        interceptNames.add("displayGuiHopper");
        interceptNames.add("func_94064_a");

        interceptNames.add("displayGUIHopper");
        interceptNames.add("func_146093_a");

        beaconNames.add("displayGUIBeacon");
        beaconNames.add("func_82240_a");
        beaconNames.add("func_146104_a");

        anvilNames.add("displayGUIAnvil");
        anvilNames.add("func_82244_d");

        distanceNames.add("getDistanceSq");
        distanceNames.add("getDistance");
        distanceNames.add("func_70092_e");
        distanceNames.add("func_70011_f");
        distanceNames.add("func_71569_e");
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (interceptNames.contains(method.getName())) {
            Object result = method.invoke(parentPlayer, args);
            if (parentPlayer.openContainer != null) {
                parentPlayer.openContainer = new ContainerWrapper(parentPlayer.openContainer);
            }
            return result;
        } else if (beaconNames.contains(method.getName())) {
            TileEntityBeacon tileEntityBeacon = (TileEntityBeacon) args[0];
            parentPlayer.getNextWindowId();
            parentPlayer.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(parentPlayer.currentWindowId, 7, tileEntityBeacon.getInventoryName(), tileEntityBeacon.getSizeInventory(), true));
            parentPlayer.openContainer = new ContainerBeacon(parentPlayer.inventory, tileEntityBeacon) {
                @Override
                public boolean canInteractWith(EntityPlayer player) {
                    return true;
                }
            };
            parentPlayer.openContainer.windowId = parentPlayer.currentWindowId;
            parentPlayer.openContainer.addCraftingToCrafters(parentPlayer);
        } else if (anvilNames.contains(method.getName())) {
            parentPlayer.getNextWindowId();
            parentPlayer.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(parentPlayer.currentWindowId, 8, "Repairing", 9, true));
            parentPlayer.openContainer = new ContainerRepair(parentPlayer.inventory, parentPlayer.worldObj, ((Integer)args[0]).intValue(), ((Integer)args[1]).intValue(), ((Integer)args[2]).intValue(), parentPlayer) {
                @Override
                public boolean canInteractWith(EntityPlayer entityPlayer) {
                    return true;
                }
            };
            parentPlayer.openContainer.windowId = parentPlayer.currentWindowId;
            parentPlayer.openContainer.addCraftingToCrafters(parentPlayer);
        } else if (distanceNames.contains(method.getName())) {
            return 6;
        }

        method.setAccessible(true);
        return method.invoke(parentPlayer, args);
    }
}
