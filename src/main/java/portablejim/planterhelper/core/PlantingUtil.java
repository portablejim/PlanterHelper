package portablejim.planterhelper.core;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 18/12/13
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlantingUtil {
    public static int getTargetSlot(InventoryPlayer inv) {
        int targetItemNum;

        // Wrap around on the left
        if(inv.currentItem == InventoryPlayer.getHotbarSize() - 1) {
            targetItemNum = 0;
        }
        else {
            targetItemNum = inv.currentItem + 1;
        }

        return targetItemNum;
    }

    public static boolean hasAvailableSeeds(InventoryPlayer inv) {
        int targetItemNum = getTargetSlot(inv);

        ItemStack resultItem = inv.mainInventory[targetItemNum];

        return resultItem != null && resultItem.getItem() instanceof IPlantable;
    }

    public static boolean targetedSuitableFarmland(World world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        int blockId = world.getBlockId(x, y, z);

        if(blockId > Block.blocksList.length - 1) {
            return false;
        }

        if(Block.blocksList[blockId] == null) {
            return false;
        }

        return Block.blocksList[blockId].canSustainPlant(world, x, y, z, direction, plantable);
    }

    public static boolean canPlant(InventoryPlayer inv, World world, int x, int y, int z, ForgeDirection direction) {
        if(hasAvailableSeeds(inv)) {
            Item targetSlot = inv.mainInventory[getTargetSlot(inv)].getItem();
            IPlantable seeds = (IPlantable) targetSlot;

            if(targetedSuitableFarmland(world, x, y, z, direction, seeds)){
                return true;
            }
        }
        return false;
    }
}
