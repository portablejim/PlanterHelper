package portablejim.planterhelper.inventories;

import cpw.mods.fml.common.FMLLog;
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
    private ItemStack currentItem;

    public SmallInventory(ItemStack current) {
        this.currentItem = current;

        if(!currentItem.hasTagCompound()) {
            currentItem.setTagCompound(new NBTTagCompound());
        }

        loadFromNBT(this.currentItem.getTagCompound());
    }

    @Override
    public int getSizeInventory() {
        return 27;
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
        NBTTagList tagList = tagCompound.getTagList("ItemsPlanterHelper");

        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTag = (NBTTagCompound) tagList.tagAt(i);
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
        FMLLog.getLogger().info(String.format("Writing %d items to tag", list.tagCount()));
    }

    public void clearInventory() {
        for(int i = 0; i < getSizeInventory(); i++) {
            inventoryItems[i] = null;
        }
    }
}
