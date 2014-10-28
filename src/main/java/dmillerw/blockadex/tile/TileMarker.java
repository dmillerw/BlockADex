package dmillerw.blockadex.tile;

import com.google.common.collect.Sets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Set;

/**
 * @author dmillerw
 */
public class TileMarker extends TileCore {

    public static Set<TileMarker> tileRegistry = Sets.newHashSet();

    public ForgeDirection forgeDirection = ForgeDirection.UNKNOWN;

    private boolean registered = false;

    private void register() {
        if (registered)
            return;

        if (tileRegistry.contains(this))
            return;

        tileRegistry.add(this);
        registered = true;
    }

    public void unregister() {
        if (!registered)
            return;

        if (!tileRegistry.contains(this))
            return;

        tileRegistry.remove(this);
        registered = false;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (!registered)
                register();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        unregister();
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        unregister();
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbtTagCompound) {
        forgeDirection = ForgeDirection.getOrientation(nbtTagCompound.getByte("orientation"));
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("orientation", (byte) forgeDirection.ordinal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileMarker that = (TileMarker) o;

        if (xCoord != that.xCoord) return false;
        if (yCoord != that.yCoord) return false;
        if (zCoord != that.zCoord) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = xCoord;
        result = 31 * result + yCoord;
        result = 31 * result + zCoord;
        return result;
    }
}
