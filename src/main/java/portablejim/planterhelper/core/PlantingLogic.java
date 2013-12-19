package portablejim.planterhelper.core;

import cpw.mods.fml.common.FMLLog;
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
    public static void plantSquare(IInventory inv, World world, int startX, int startY, int startZ, int width, ForgeDirection direction, int initialSlot) {
        int startCornerX;
        int startCornerZ;
        switch (direction) {
            case NORTH:
                startCornerX = startX - ((width - 1) / 2);
                startCornerZ = startZ;

                for(int i = startCornerZ; i > startCornerZ - width; --i) {
                    for(int j = startCornerX; j < startCornerX + width; j++) {
                        int slot = getSeedsSlot(inv, initialSlot);
                        if(slot < 0) {
                            return;
                        }
                        boolean success = placeSeed(inv, world, j, startY, i, slot, direction);
                        if(success) {
                            inv.decrStackSize(slot, 1);
                        }
                    }
                }
                break;
            case EAST:
                startCornerX = startX;
                startCornerZ = startZ - ((width - 1) / 2);

                for(int i = startCornerX; i < startCornerX + width; ++i) {
                    for(int j = startCornerZ; j < startCornerZ + width; j++) {
                        int slot = getSeedsSlot(inv, initialSlot);
                        if(slot < 0) {
                            return;
                        }
                        boolean success = placeSeed(inv, world, i, startY, j, slot, direction);
                        if(success) {
                            inv.decrStackSize(slot, 1);
                        }
                    }
                }
                break;
            case SOUTH:
                startCornerX = startX + ((width - 1) / 2);
                startCornerZ = startZ;

                for(int i = startCornerZ; i < startCornerZ + width; ++i) {
                    for(int j = startCornerX; j > startCornerX - width; j--) {
                        int slot = getSeedsSlot(inv, initialSlot);
                        if(slot < 0) {
                            return;
                        }
                        boolean success = placeSeed(inv, world, j, startY, i, slot, direction);
                        if(success) {
                            inv.decrStackSize(slot, 1);
                        }
                    }
                }
                break;
            case WEST:
                startCornerX = startX;
                startCornerZ = startZ + ((width - 1) / 2);

                for(int i = startCornerX; i > startCornerX - width; --i) {
                    for(int j = startCornerZ; j > startCornerZ - width; j--) {
                        int slot = getSeedsSlot(inv, initialSlot);
                        if(slot < 0) {
                            return;
                        }
                        boolean success = placeSeed(inv, world, i, startY, j, slot, direction);
                        if(success) {
                            inv.decrStackSize(slot, 1);
                        }
                    }
                }
        }
    }

    private static int getSeedsSlot(IInventory inv, int initialSlot) {
        ItemStack targetHotbarSlot = inv.getStackInSlot(initialSlot);

        if(targetHotbarSlot != null && targetHotbarSlot.stackSize > 0) {
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

    private static boolean placeSeed(IInventory inv, World world, int x, int y, int z, int invPos, ForgeDirection direction) {

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
}
