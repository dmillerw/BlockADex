package dmillerw.blockadex.lib;

import com.google.common.base.Objects;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

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
//        String name = packetBuffer.readStringFromBuffer(255);
        ItemStack icon = packetBuffer.readItemStackFromBuffer();
        return new BlockIndexData(x, y, z, "", icon);
    }

    public final int x;
    public final int y;
    public final int z;

    public final String name;

    public final ItemStack icon;

    public BlockIndexData(int x, int y, int z, String name, ItemStack icon) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.icon = icon;
    }

    public void toBytes(ByteBuf byteBuf) throws IOException {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeInt(x);
        packetBuffer.writeInt(y);
        packetBuffer.writeInt(z);
//        packetBuffer.writeStringToBuffer(name);
        packetBuffer.writeItemStackToBuffer(icon);
    }

    @Override
    public String toString() {
        Objects.ToStringHelper toStringHelper = Objects.toStringHelper(this);
        toStringHelper.add("x", x);
        toStringHelper.add("y", y);
        toStringHelper.add("z", z);
        toStringHelper.add("name", name);
        toStringHelper.add("icon", icon);
        return toStringHelper.toString();
    }
}
