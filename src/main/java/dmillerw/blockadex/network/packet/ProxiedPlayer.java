package dmillerw.blockadex.network.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

/**
 * @author dmillerw
 */
public class ProxiedPlayer extends EntityPlayerMP {

    private EntityPlayerMP parentPlayer;

    public ProxiedPlayer(EntityPlayerMP parentPlayer) {
        super(parentPlayer.mcServer, (WorldServer) parentPlayer.worldObj, parentPlayer.getGameProfile(), parentPlayer.theItemInWorldManager);
        this.parentPlayer = parentPlayer;
    }

    @Override
    public float getDistanceToEntity(Entity p_70032_1_) {
        return 6;
    }

    @Override
    public double getDistanceSq(double p_70092_1_, double p_70092_3_, double p_70092_5_) {
        return 6;
    }

    @Override
    public double getDistance(double p_70011_1_, double p_70011_3_, double p_70011_5_) {
        return 6;
    }

    @Override
    public double getDistanceSqToEntity(Entity p_70068_1_) {
        return 6;
    }
}
