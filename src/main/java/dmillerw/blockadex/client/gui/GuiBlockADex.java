package dmillerw.blockadex.client.gui;

import com.google.common.collect.Lists;
import dmillerw.blockadex.block.BlockBlockADex;
import dmillerw.blockadex.inventory.ContainerNull;
import dmillerw.blockadex.lib.BlockIndexData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * @author dmillerw
 */
public class GuiBlockADex extends GuiContainer {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("blockadex:textures/gui/blockadex.png");

    private static final RenderItem renderItem = new RenderItem();

    public static List<BlockIndexData> clientIndexData = Lists.newArrayList();

    public GuiBlockADex() {
        super(new ContainerNull());
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
            if (i >= clientIndexData.size())
                break;

            BlockIndexData blockIndexData = clientIndexData.get(i);

            int y = 17 + (18 * i);
            int x = 8;

            if (mouseX >= x && mouseX <= x + 160 && mouseY >= y && mouseY <= y + 16) {
                GL11.glColorMask(true, true, true, false);
                this.drawGradientRect(x, y, x + 160, y + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
            }

            renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), blockIndexData.icon, x, y);
            mc.fontRenderer.drawStringWithShadow(blockIndexData.icon.getDisplayName(), x + 17, y + 9 - mc.fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF);
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
            if (i >= clientIndexData.size())
                break;

            int y = 17 + (18 * i);
            int x = 8;

            if (mouseX >= x && mouseX <= x + 160 && mouseY >= y && mouseY <= y + 18) {
                BlockBlockADex.activateBlock(mc.thePlayer, i);
                return;
            }
        }
    }
}