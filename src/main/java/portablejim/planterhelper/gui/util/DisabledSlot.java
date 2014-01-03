package portablejim.planterhelper.gui.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 23/12/13
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisabledSlot extends Slot {
    public DisabledSlot(IInventory par1IInventory, int par2, int par3, int par4) {
        super(par1IInventory, par2, par3, par4);
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    public boolean canTakeStack(EntityPlayer player)
    {
        return false;
    }
}
