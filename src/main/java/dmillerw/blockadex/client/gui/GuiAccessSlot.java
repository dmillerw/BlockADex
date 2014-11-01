package dmillerw.blockadex.client.gui;

import dmillerw.blockadex.handler.GuiHandler;
import dmillerw.blockadex.inventory.ContainerAccessSlot;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.network.packet.PacketOpenGUI;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class GuiAccessSlot extends GuiContainer {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");

    private final TileBlockADex tileBlockADex;

    public GuiAccessSlot(EntityPlayer entityPlayer, TileBlockADex tileBlockADex, int slot) {
        super(new ContainerAccessSlot(entityPlayer, tileBlockADex, slot));
        this.tileBlockADex = tileBlockADex;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void keyTyped(char character, int key) {
        if (key == 1 || key == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            PacketOpenGUI packetOpenGUI = new PacketOpenGUI();
            packetOpenGUI.x = tileBlockADex.xCoord;
            packetOpenGUI.y = tileBlockADex.yCoord;
            packetOpenGUI.z = tileBlockADex.zCoord;
            packetOpenGUI.id = GuiHandler.GUI_BLOCK_A_DEX;
            PacketHandler.INSTANCE.sendToServer(packetOpenGUI);
        } else {
            super.keyTyped(character, key);
        }
    }
}
