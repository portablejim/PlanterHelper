package portablejim.planterhelper.gui;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedPlanterGui extends GuiChest {
    public SeedPlanterGui(IInventory playerInv, IInventory itemInv)
    {
        super(playerInv, itemInv);
    }
}
