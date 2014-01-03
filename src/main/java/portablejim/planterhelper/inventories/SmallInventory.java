package portablejim.planterhelper.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.IPlantable;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallInventory implements IInventory {
    private ItemStack[] inventoryItems = new ItemStack[27];
    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if(i >= getSizeInventory()) {
            throw new IndexOutOfBoundsException();
        }

        return inventoryItems[i] == null ? null : inventoryItems[i].copy();
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

        return output == null ? null : output.copy();
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotNum) {
        if(inventoryItems[slotNum] != null) {
            ItemStack stack = inventoryItems[slotNum];
            inventoryItems[slotNum] = null;
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
        //onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Planter";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
        // Not updating NBT until close of inventory.
        // NOOP
        assert Boolean.TRUE;
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

    public void loadFromNBT(ItemStack toolStack) {
        if(!toolStack.hasTagCompound()) {
            return;
        }

        NBTTagCompound tagCompound = toolStack.getTagCompound();
        NBTTagList tagList = tagCompound.getTagList("ItemsPlanterHelper");

        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTag = (NBTTagCompound) tagList.tagAt(i);
            int slot = itemTag.getByte("SlotPlanterHelper");

            if(slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
            }
        }

    }

    public void saveToNBT(ItemStack toolStack) {
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = inventoryItems[i];
            if(stack != null) {
                NBTTagCompound itemTag = new NBTTagCompound();

                itemTag.setByte("SlotPlanterHelper", (byte) i);
                stack.writeToNBT(itemTag);
                list.appendTag(itemTag);
            }
        }

        NBTTagCompound newCompound = new NBTTagCompound();
        newCompound.setTag("ItemsPlanterHelper", list);
        toolStack.setTagCompound(newCompound);
    }

    public void clearInventory() {
        for(int i = 0; i < getSizeInventory(); i++) {
            inventoryItems[i] = null;
        }
    }
}