package portablejim.planterhelper.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 17/12/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicSeedPlanter extends Planter {

    public BasicSeedPlanter(int itemId) {
        super(itemId, 0, 3);
        this.setUnlocalizedName("PlanterHelper:basicSeedPlanter");
        this.setTextureName("PlanterHelper:basicSeedPlanter");
    }

    @Override
    public IInventory getInventory(EntityPlayer player) {
        return player.inventory;
    }

    @Override
    public int getFirstSlot(IInventory inventory) {
        if(inventory instanceof InventoryPlayer) {
            int targetItemNum;
            InventoryPlayer inventoryPlayer = (InventoryPlayer) inventory;

            // Wrap around on the left
            if(inventoryPlayer.currentItem == InventoryPlayer.getHotbarSize() - 1) {
                targetItemNum = 0;
            }
            else {
                targetItemNum = inventoryPlayer.currentItem + 1;
            }

            return targetItemNum;
        }
        else {
            return -1;
        }
    }
}
