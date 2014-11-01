package dmillerw.blockadex.client.gui;

import dmillerw.blockadex.block.BlockBlockADex;
import dmillerw.blockadex.inventory.ContainerBlockADex;
import dmillerw.blockadex.lib.BlockIndexData;
import dmillerw.blockadex.network.PacketHandler;
import dmillerw.blockadex.network.packet.PacketOpenGUI;
import dmillerw.blockadex.tile.TileBlockADex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class GuiBlockADex extends GuiContainer {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("blockadex:textures/gui/blockadex.png");

    private static final RenderItem renderItem = new RenderItem();

    private final EntityPlayer entityPlayer;
    private final TileBlockADex tileBlockADex;

    public GuiBlockADex(EntityPlayer entityPlayer, TileBlockADex tileBlockADex) {
        super(new ContainerBlockADex(entityPlayer, tileBlockADex));

        this.entityPlayer = entityPlayer;
        this.tileBlockADex = tileBlockADex;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(GUI_TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();

        mouseX -= guiLeft;
        mouseY -= guiTop;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderHelper.enableGUIStandardItemLighting();

        for (int i=0; i<8; i++) {
            BlockIndexData blockIndexData = ((ContainerBlockADex)inventorySlots).blockDataCache[i];

            int y = 17 + (18 * i);
            int x = 8;

            if (mouseX >= x && mouseX <= x + 160 && mouseY >= y && mouseY <= y + 16) {
                GL11.glColorMask(true, true, true, false);
                this.drawGradientRect(x, y, x + 160, y + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
            }

            if (blockIndexData != null) {
                renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), blockIndexData.icon, x, y);
                if (blockIndexData.dimension == entityPlayer.worldObj.provider.dimensionId) {
                    if (blockIndexData.icon != null) {
                        mc.fontRenderer.drawStringWithShadow(blockIndexData.icon.getDisplayName(), x + 17, y + 9 - mc.fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF);
                    } else {
                        mc.fontRenderer.drawStringWithShadow("INVALID", x + 17, y + 9 - mc.fontRenderer.FONT_HEIGHT / 2, 0xAA0000);
                    }
                } else {
                    mc.fontRenderer.drawStringWithShadow("IN OTHER DIMENSION", x + 17, y + 9 - mc.fontRenderer.FONT_HEIGHT / 2, 0xAA0000);
                }
            } else {
                mc.fontRenderer.drawStringWithShadow("CLICK TO ADD", x + 17, y + 9 - mc.fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF);
            }
        }

        RenderHelper.disableStandardItemLighting();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        Minecraft mc = Minecraft.getMinecraft();

        mouseX -= guiLeft;
        mouseY -= guiTop;

        for (int i=0; i<8; i++) {
            BlockIndexData blockIndexData = ((ContainerBlockADex)inventorySlots).blockDataCache[i];
            int y = 17 + (18 * i);
            int x = 8;

            if (mouseX >= x && mouseX <= x + 160 && mouseY >= y && mouseY <= y + 18) {
                if (button == 0 && !GuiScreen.isShiftKeyDown() && blockIndexData != null) {
                    BlockBlockADex.activateBlock(mc.thePlayer, blockIndexData);
                } else {
                    PacketOpenGUI packetOpenGUI = new PacketOpenGUI();
                    packetOpenGUI.x = tileBlockADex.xCoord;
                    packetOpenGUI.y = tileBlockADex.yCoord;
                    packetOpenGUI.z = tileBlockADex.zCoord;
                    packetOpenGUI.id = 10 + i;
                    PacketHandler.INSTANCE.sendToServer(packetOpenGUI);
                }
                return;
            }
        }
    }
}