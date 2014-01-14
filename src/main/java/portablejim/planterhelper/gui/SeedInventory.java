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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.IPlantable;
import portablejim.planterhelper.items.Planter;

/**
 * Inventory that is tied to an item (e.g. bag). Size is variable.
 */
public class SeedInventory implements IInventory {
    private ItemStack[] inventoryItems;
    private ItemStack currentItem;
    private int size;

    public SeedInventory(ItemStack current) {
        this.currentItem = current;

        assert(currentItem != null);
        assert(currentItem.getItem() instanceof Planter);

        int itemNumSlots = ((Planter)currentItem.getItem()).getInvSlots();
        if(itemNumSlots > 0) {
            inventoryItems = new ItemStack[itemNumSlots];
            size = itemNumSlots;
        }
        else {
            inventoryItems = new ItemStack[0];
            size = 0;
        }

        if(!currentItem.hasTagCompound()) {
            currentItem.setTagCompound(new NBTTagCompound());
        }

        loadFromNBT(this.currentItem.getTagCompound());
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if(i >= getSizeInventory()) {
            throw new IndexOutOfBoundsException();
        }

        return inventoryItems[i];
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if(inventoryItems[index] == null) {
            return null;
        }

        ItemStack output;
        if(inventoryItems[index].stackSize <= amount) {
            output = inventoryItems[index];
            inventoryItems[index] = null;
        }
        else {
            output = inventoryItems[index].splitStack(amount);

            if(inventoryItems[index].stackSize ==0) {
                inventoryItems[index] = null;
            }
        }
        onInventoryChanged();

        return output;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotNum) {
        if(inventoryItems[slotNum] != null) {
            ItemStack stack = inventoryItems[slotNum];
            setInventorySlotContents(slotNum, null);
            return stack;
        }
        else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int num, ItemStack itemStack) {
        inventoryItems[num] = itemStack;

        if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
            itemStack.stackSize = getInventoryStackLimit();
        }

        onInventoryChanged();
    }

    @Override
    public String func_145825_b() {
        // Temp function until MCP names get fixed up.
        return getInvName();
    }

    @Override
    public boolean func_145818_k_() {
        // Temp function until MCP names get fixed up.
        return isInvNameLocalized();
    }

    //@Override
    public String getInvName() {
        return "Planter";
    }

    //@Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
        // Clear useless slots with an itemstack of size 0.
        for(int i = 0; i < getSizeInventory(); i++) {
            if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
                setInventorySlotContents(i, null);
            }
        }

        this.saveToNBT(this.currentItem.getTagCompound());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof IPlantable;
    }

    public void loadFromNBT(NBTTagCompound tagCompound) {
        final int NBT_TAGLIST = 10;
        NBTTagList tagList = tagCompound.func_150295_c("ItemsPlanterHelper", NBT_TAGLIST);

        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTag = tagList.func_150305_b(i);
            int slot = itemTag.getInteger("SlotPlanterHelper");

            if(slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
            }
        }
    }

    public void saveToNBT(NBTTagCompound newCompound) {
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = inventoryItems[i];
            if(stack != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("SlotPlanterHelper", i);
                stack.writeToNBT(itemTag);
                list.appendTag(itemTag);
            }
        }

        newCompound.setTag("ItemsPlanterHelper", list);
    }
}
