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

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Methods for planting seeds useful in multiple places.
 */
public class PlantingLogic {
    public static int getSeedsSlot(IInventory inv, int initialSlot) {
        if(initialSlot < 0) {
            return -1;
        }

        ItemStack initialTarget = inv.getStackInSlot(initialSlot);

        if(initialTarget != null && initialTarget.stackSize > 0) {
            return initialSlot;
        }

        int slot = 0;

        while(inv.getStackInSlot(slot) == null || inv.getStackInSlot(slot).getItem() == null || !(inv.getStackInSlot(slot).getItem() instanceof IPlantable) || inv.getStackInSlot(slot).stackSize <= 0 && slot < inv.getSizeInventory()) {
            ++slot;
            if(slot >= inv.getSizeInventory()) {
                return -1;
            }
        }

        return slot;
    }

    public static boolean placeSeed(IInventory inv, World world, int x, int y, int z, int invPos, ForgeDirection direction) {

        ItemStack currentItem = inv.getStackInSlot(invPos);
        if(currentItem == null || !(currentItem.getItem() instanceof IPlantable)) {
            return false;
        }

        IPlantable plantable = (IPlantable) currentItem.getItem();

        Block targetBlock = world.func_147439_a(x, y, z);
        if(targetBlock == null || !targetBlock.canSustainPlant(world, x, y, z, direction, plantable)) {
            return false;
        }

        if(!world.func_147437_c(x, y + 1, z)) {
            return false;
        }

        Block plantablePlant = plantable.getPlant(world, x, y + 1, z);
        int plantMeta = plantable.getPlantMetadata(world, x, y + 1, z);

        world.func_147465_d(x, y + 1, z, plantablePlant, plantMeta, 3);

        return true;
    }

    public static boolean targetedSuitableFarmland(World world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        Block block = world.func_147439_a(x, y, z);

        return block != null && block.canSustainPlant(world, x, y, z, direction, plantable);
    }
}
