package dmillerw.blockadex.lib;

import com.google.common.base.Objects;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

/**
 * @author dmillerw
 */
public class BlockIndexData {

    public static BlockIndexData fromBuffer(ByteBuf byteBuf) throws IOException {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        int x = packetBuffer.readInt();
        int y = packetBuffer.readInt();
        int z = packetBuffer.readInt();
        int dimension = packetBuffer.readInt();
        ItemStack icon = packetBuffer.readItemStackFromBuffer();
        return new BlockIndexData(x, y, z, dimension, icon);
    }

    public static BlockIndexData fromNBT(NBTTagCompound nbtTagCompound) {
        int x = nbtTagCompound.getInteger("x");
        int y = nbtTagCompound.getInteger("y");
        int z = nbtTagCompound.getInteger("z");
        int dimension = nbtTagCompound.getInteger("dimension");
        ItemStack icon = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("icon"));
        return new BlockIndexData(x, y, z, dimension, icon);
    }

    public final int x;
    public final int y;
    public final int z;
    public final int dimension;

    public String name;

    public final ItemStack icon;

    public BlockIndexData(World world, int x, int y, int z, ItemStack icon) {
        this(x, y, z, world.provider.dimensionId, icon);
    }

    public BlockIndexData(int x, int y, int z, int dimension, ItemStack icon) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.icon = icon;
    }

    public void toBytes(ByteBuf byteBuf) throws IOException {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeInt(x);
        packetBuffer.writeInt(y);
        packetBuffer.writeInt(z);
        packetBuffer.writeInt(dimension);
        packetBuffer.writeItemStackToBuffer(icon);
    }

    public void toNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("x", x);
        nbtTagCompound.setInteger("y", y);
        nbtTagCompound.setInteger("z", z);
        nbtTagCompound.setInteger("dimension", dimension);
        NBTTagCompound iconNBT = new NBTTagCompound();
        icon.writeToNBT(iconNBT);
        nbtTagCompound.setTag("icon", iconNBT);
    }

    @Override
    public String toString() {
        Objects.ToStringHelper toStringHelper = Objects.toStringHelper(this);
        toStringHelper.add("x", x);
        toStringHelper.add("y", y);
        toStringHelper.add("z", z);
        toStringHelper.add("dimension", dimension);
        toStringHelper.add("icon", icon);
        return toStringHelper.toString();
    }
}
