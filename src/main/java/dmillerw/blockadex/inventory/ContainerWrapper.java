package dmillerw.blockadex.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * @author dmillerw
 */
public class ContainerWrapper extends Container {

    private final Container container;

    public ContainerWrapper(Container container) {
        this.container = container;
    }

    @Override
    public boolean canDragIntoSlot(Slot par1Slot) {
        return container.canDragIntoSlot(par1Slot);
    }

    @Override
    public boolean canInteractWith(net.minecraft.entity.player.EntityPlayer arg0) {
        return true;
    }

    @Override
    public net.minecraft.inventory.Slot getSlot(int arg0) {
        return container.getSlot(arg0);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public java.util.List getInventory() {
        return container.getInventory();
    }

    @Override
    public boolean enchantItem(net.minecraft.entity.player.EntityPlayer arg0, int arg1) {
        return container.enchantItem(arg0, arg1);
    }

    @Override
    public net.minecraft.item.ItemStack slotClick(int arg0, int arg1, int arg2, net.minecraft.entity.player.EntityPlayer arg3) {
        return container.slotClick(arg0, arg1, arg2, arg3);
    }

    @Override
    public boolean func_94530_a(net.minecraft.item.ItemStack arg0, net.minecraft.inventory.Slot arg1) {
        return container.func_94530_a(arg0, arg1);
    }

    @Override
    public void addCraftingToCrafters(net.minecraft.inventory.ICrafting arg0) {
        container.addCraftingToCrafters(arg0);
    }

    @Override
    public void detectAndSendChanges() {
        container.detectAndSendChanges();
    }

    @Override
    public void removeCraftingFromCrafters(net.minecraft.inventory.ICrafting arg0) {
        container.removeCraftingFromCrafters(arg0);
    }

    @Override
    public net.minecraft.inventory.Slot getSlotFromInventory(net.minecraft.inventory.IInventory arg0, int arg1) {
        return container.getSlotFromInventory(arg0, arg1);
    }

    @Override
    public net.minecraft.item.ItemStack transferStackInSlot(net.minecraft.entity.player.EntityPlayer arg0, int arg1) {
        return container.transferStackInSlot(arg0, arg1);
    }

    @Override
    public void onCraftMatrixChanged(net.minecraft.inventory.IInventory arg0) {
        container.onCraftMatrixChanged(arg0);
    }

    @Override
    public void putStackInSlot(int arg0, net.minecraft.item.ItemStack arg1) {
        container.putStackInSlot(arg0, arg1);
    }

    @Override
    public void putStacksInSlots(net.minecraft.item.ItemStack[] arg0) {
        container.putStacksInSlots(arg0);
    }

    @Override
    public void updateProgressBar(int arg0, int arg1) {
        container.updateProgressBar(arg0, arg1);
    }

    @Override
    public short getNextTransactionID(net.minecraft.entity.player.InventoryPlayer arg0) {
        return container.getNextTransactionID(arg0);
    }

    @Override
    public boolean isPlayerNotUsingContainer(net.minecraft.entity.player.EntityPlayer arg0) {
        return container.isPlayerNotUsingContainer(arg0);
    }

    @Override
    public void setPlayerIsPresent(net.minecraft.entity.player.EntityPlayer arg0, boolean arg1) {
        container.setPlayerIsPresent(arg0, arg1);
    }
}
