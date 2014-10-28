package dmillerw.blockadex.client.gui;

import com.google.common.collect.Lists;
import dmillerw.blockadex.block.BlockBlockADex;
import dmillerw.blockadex.inventory.ContainerNull;
import dmillerw.blockadex.lib.BlockIndexData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;

import java.util.List;

/**
 * @author dmillerw
 */
public class GuiBlockADex extends GuiContainer {

    public static List<BlockIndexData> clientIndexData = Lists.newArrayList();

    private static final RenderItem renderItem = new RenderItem();

    public GuiBlockADex() {
        super(new ContainerNull());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();

        RenderHelper.enableGUIStandardItemLighting();

        for (int i=0; i<clientIndexData.size(); i++) {
            renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), clientIndexData.get(i).icon, 5 + (20 * i), 5);
        }

        RenderHelper.disableStandardItemLighting();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        Minecraft mc = Minecraft.getMinecraft();

        mouseX -= guiLeft;
        mouseY -= guiTop;

        for (int i=0; i<clientIndexData.size(); i++) {
            int x1 = 5 + (20 * i);
            int y1 = 5;
            int x2 = x1 + 20;
            int y2 = y1 + 20;

            if (mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
                BlockBlockADex.activateBlock(mc.thePlayer, i);
                return;
            }
        }
    }
}
