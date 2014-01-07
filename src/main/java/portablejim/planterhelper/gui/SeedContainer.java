package portablejim.planterhelper.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import portablejim.planterhelper.gui.util.DisabledSlot;
import portablejim.planterhelper.gui.util.SeedSlot;
import portablejim.planterhelper.gui.SeedInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedContainer extends Container {
    private SeedInventory inv;
    private int rows;

    public SeedContainer(InventoryPlayer player, SeedInventory inventory) {
        this.inv = inventory;
        rows = this.inv.getSizeInventory() / 9;

        int invHeightPx = (this.rows - 4) * 18;

        int invRow;
        int invColumn;

        for(invRow = 0; invRow < this.rows; invRow++) {
            for(invColumn = 0; invColumn < 9; invColumn++) {
                this.addSlotToContainer(new SeedSlot(inventory, invColumn + invRow * 9, 8 + invColumn * 18, 18 + invRow * 18));
            }
        }

        for(invRow = 0; invRow < 3; invRow++) {
            for(invColumn = 0; invColumn < 9; invColumn++) {
                this.addSlotToContainer(new Slot(player, invColumn + invRow * 9 + 9, 8 + invColumn * 18, 103 + invRow * 18 + invHeightPx));
            }
        }

        for(invColumn = 0; invColumn < 9; invColumn++) {
            if(invColumn == player.currentItem) {
                this.addSlotToContainer(new DisabledSlot(player, invColumn, 8 + invColumn * 18, 161 + invHeightPx));
            }
            else {
                this.addSlotToContainer(new Slot(player, invColumn, 8 + invColumn * 18, 161 + invHeightPx));
            }
        }

    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        //inv.saveToNBT(player.getCurrentEquippedItem());
        //inv.clearInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return inv.isUseableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack slotClick(int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer) {
        return super.slotClick(paramInt1, paramInt2, paramInt3, paramEntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < 9) {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                    return null;
                }
            }
            //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
}
