package dmillerw.blockadex.client.gui;

import com.google.common.collect.Lists;
import dmillerw.blockadex.inventory.ContainerNull;
import dmillerw.blockadex.lib.BlockIndexData;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.List;

/**
 * @author dmillerw
 */
public class GuiBlockADex extends GuiContainer {

    public static List<BlockIndexData> clientIndexData = Lists.newArrayList();

    public GuiBlockADex() {
        super(new ContainerNull());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {

    }
}
