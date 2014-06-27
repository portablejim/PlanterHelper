/* This file is part of PlanterHelper.
 *
 *    PlanterHelper is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as
 *    published by the Free Software Foundation, either version 3 of
 *     the License, or (at your option) any later version.
 *
 *    PlanterHelper is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with PlanterHelper.
 *    If not, see <http://www.gnu.org/licenses/>.
 */

package portablejim.planterhelper.gui;

import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import org.lwjgl.input.Keyboard;
import portablejim.planterhelper.gui.util.DisabledSlot;
import portablejim.planterhelper.gui.util.SeedSlot;

/**
 * Container that sets-up arbitrarily sized inventories like chests.
 */
@ChestContainer
public class SeedContainer extends Container {
    private SeedInventory inv;

    public SeedContainer(InventoryPlayer player, SeedInventory inventory) {
        this.inv = inventory;
        int rows = this.inv.getSizeInventory() / 9;

        int invHeightPx = (rows - 4) * 18;

        int invRow;
        int invColumn;

        for(invRow = 0; invRow < rows; invRow++) {
            for(invColumn = 0; invColumn < 9; invColumn++) {
                this.addSlotToContainer(new SeedSlot(inventory, invColumn + invRow * 9, 8 + invColumn * 18, 18 + invRow * 18));
            }
        }

        for(invRow = 0; invRow < 3; invRow++) {
            for(invColumn = 0; invColumn < 9; invColumn++) {
                this.addSlotToContainer(new Slot(player, invColumn + invRow * 9 + 9, 8 + invColumn * 18, 102 + invRow * 18 + invHeightPx + 1));
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
        inv.saveToNBT(player.getCurrentEquippedItem().getTagCompound());
        //inv.clearInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return inv.isUseableByPlayer(entityPlayer);
    }

    @Override
    public ItemStack slotClick(int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer) {
         if(paramInt3 == 4) {
            if(!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) {
                return super.slotClick(paramInt1, paramInt2, 0, paramEntityPlayer);
            }
            else {
                return transferStackInSlot(paramEntityPlayer, paramInt1);
            }
        }
        return super.slotClick(paramInt1, paramInt2, paramInt3, paramEntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack() && slotObject.getStack().getItem() instanceof IPlantable) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < inv.getSizeInventory()) {
                if (!this.mergeItemStack(stackInSlot, inv.getSizeInventory() + 1, inv.getSizeInventory() + 36, true)) {
                    return null;
                }
            }
            //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, inv.getSizeInventory(), false)) {
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
