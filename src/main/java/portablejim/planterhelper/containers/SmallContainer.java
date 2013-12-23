package portablejim.planterhelper.containers;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import portablejim.planterhelper.inventories.SmallInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallContainer extends ContainerChest {
    private SmallInventory inv;

    public SmallContainer(InventoryPlayer player, SmallInventory inventory) {
        super(player, inventory);
        this.inv = inventory;
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
