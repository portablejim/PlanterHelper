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

package portablejim.planterhelper.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

/**
 * Tier 1 planter without an inventory. Plants a small area (3x3).
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

            ItemStack resultItem = inventory.getStackInSlot(targetItemNum);

            return resultItem != null && resultItem.getItem() instanceof IPlantable ? targetItemNum : -1;
        }
        else {
            return super.getFirstSlot(inventory);
        }
    }
}
