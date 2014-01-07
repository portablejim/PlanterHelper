package portablejim.planterhelper.core;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 17/12/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlantingLogic {
    public static int getSeedsSlot(IInventory inv, int initialSlot) {
        initialSlot = initialSlot < 0 ? 0 : initialSlot;

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

        int blockId = world.getBlockId(x, y, z);
        if(blockId > Block.blocksList.length - 1) {
            return false;
        }
        if(Block.blocksList[blockId] == null || !Block.blocksList[blockId].canSustainPlant(world, x, y, z, direction, plantable)) {
            return false;
        }

        if(!world.isAirBlock(x, y + 1, z)) {
            return false;
        }

        int plantId = plantable.getPlantID(world, x, y + 1, z);
        int plantMeta = plantable.getPlantMetadata(world, x, y + 1, z);

        world.setBlock(x, y + 1, z, plantId, plantMeta, 3);

        return true;
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
}
