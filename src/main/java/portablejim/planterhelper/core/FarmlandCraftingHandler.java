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

package portablejim.planterhelper.core;

import cpw.mods.fml.common.ICraftingHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;


/**
 * Handle when a hoe is used to make farmland and make the hoe take damage.
 */
public class FarmlandCraftingHandler implements ICraftingHandler {
    @Override
    public void onCrafting(EntityPlayer player, ItemStack itemStack, IInventory iInventory) {
        int countDirt = 0;
        int countHoe = 0;
        boolean farmland = itemStack != null && itemStack.itemID == Block.tilledField.blockID;
        int hoeSlot = -1;

        for(int i = 0; i < iInventory.getSizeInventory(); i++) {
            ItemStack itemInSlot = iInventory.getStackInSlot(i);

            if(itemInSlot == null) continue;
            if(itemInSlot.itemID == Block.dirt.blockID) countDirt++;
            if(itemInSlot.getItem() instanceof ItemHoe) {
                countHoe++;
                hoeSlot = i;
            }
        }

        if(countDirt == 1 && countHoe == 1 && farmland) {
            ItemStack hoe = iInventory.getStackInSlot(hoeSlot);
            hoe.stackSize++;
            hoe.damageItem(1, player);
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack itemStack) { }
}
