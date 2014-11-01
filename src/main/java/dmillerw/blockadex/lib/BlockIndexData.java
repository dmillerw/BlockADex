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
        String name = packetBuffer.readStringFromBuffer(255);
        ItemStack icon = packetBuffer.readItemStackFromBuffer();
        return new BlockIndexData(x, y, z, dimension, name, icon);
    }

    public static BlockIndexData fromNBT(NBTTagCompound nbtTagCompound) {
        int x = nbtTagCompound.getInteger("x");
        int y = nbtTagCompound.getInteger("y");
        int z = nbtTagCompound.getInteger("z");
        int dimension = nbtTagCompound.getInteger("dimension");
        String name = nbtTagCompound.getString("name");
        ItemStack icon = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("icon"));
        return new BlockIndexData(x, y, z, dimension, name, icon);
    }

    public final int x;
    public final int y;
    public final int z;
    public final int dimension;

    public final String name;

    public final ItemStack icon;

    public BlockIndexData(World world, int x, int y, int z, String name, ItemStack icon) {
        this(x, y, z, world.provider.dimensionId, name, icon);
    }

    public BlockIndexData(int x, int y, int z, int dimension, String name, ItemStack icon) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.name = name;
        this.icon = icon;
    }

    public void toBytes(ByteBuf byteBuf) throws IOException {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeInt(x);
        packetBuffer.writeInt(y);
        packetBuffer.writeInt(z);
        packetBuffer.writeInt(dimension);
        packetBuffer.writeStringToBuffer(name);
        packetBuffer.writeItemStackToBuffer(icon);
    }

    public void toNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("x", x);
        nbtTagCompound.setInteger("y", y);
        nbtTagCompound.setInteger("z", z);
        nbtTagCompound.setInteger("dimension", dimension);
        nbtTagCompound.setString("name", name);
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
        toStringHelper.add("name", name);
        toStringHelper.add("icon", icon);
        return toStringHelper.toString();
    }
}
