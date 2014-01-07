package portablejim.planterhelper.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import portablejim.planterhelper.PlanterHelper;
import portablejim.planterhelper.core.PlantingLogic;
import portablejim.planterhelper.gui.SeedInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Planter extends Item{
    protected boolean hasGui;
    protected int invSlots;
    protected int range;

    public Planter(int itemId, int guiSlots, int range) {
        super(itemId);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);

        if(guiSlots > 0) {
            this.hasGui = true;
            this.invSlots = guiSlots;
        }
        else {
            this.hasGui = false;
            this.invSlots = -1;
        }

        this.range = range;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

        if(hasGui && par3EntityPlayer.isSneaking()) {
            if(!par2World.isRemote) {
                //inventory.loadFromNBT(par1ItemStack);
                par3EntityPlayer.openGui(PlanterHelper.instance, 0, par2World, 0, 0, 0);
            }
        }

        return par1ItemStack;
    }

    @Override
    public boolean onItemUse(ItemStack itemStackUsed, EntityPlayer player, World world, int x, int y, int z, int intDirection, float par8, float par9, float par10) {
        if(hasGui && player.isSneaking()) {
            return false;
        }

        IInventory inventory = getInventory(player);
        if(inventory == null) {
            return false;
        }

        if(canPlant(inventory, world, x, y, z, ForgeDirection.getOrientation(intDirection))) {
            plant(inventory, world, x, y, z, this.range, player.rotationYaw);
        }

        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 1;
    }

    /*
     * Overridden in Basic
     */
    public IInventory getInventory(EntityPlayer player) {
        if(player.getHeldItem().getItem() == this) {
            return new SeedInventory(player.getHeldItem());
        }
        return null;
    }

    /*
     * Overridden in Basic
     */
    public int getFirstSlot(IInventory inventory) {
        return 0;
    }

    public boolean canPlant(IInventory inv, World world, int x, int y, int z, ForgeDirection direction) {
        int nextSlot = PlantingLogic.getSeedsSlot(inv, getFirstSlot(inv));
        if(nextSlot >= 0) {
            ItemStack targetItem = inv.getStackInSlot(nextSlot);
            assert(targetItem != null);
            assert(targetItem.getItem() instanceof IPlantable);
            IPlantable targetPlantable = (IPlantable) targetItem.getItem();

            return PlantingLogic.targetedSuitableFarmland(world, x, y, z, direction, targetPlantable);
        }
        return false;
    }

    /*
     * Overridden in Vein
     */
    public void plant(IInventory inv, World world, int startX, int startY, int startZ, int width, float playerRotation) {
        int startCornerX;
        int startCornerZ;

        int intFacing = MathHelper.floor_double((double) (playerRotation * 4.0F / 360.0F) + 0.5D) & 3;
        ForgeDirection[] directions = { ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST };
        ForgeDirection direction = directions[intFacing];

        switch (direction) {
            case NORTH:
                startCornerX = startX - ((width - 1) / 2);
                startCornerZ = startZ;

                for(int rowZ = startCornerZ; rowZ > startCornerZ - width; --rowZ) {
                    for(int columnX = startCornerX; columnX < startCornerX + width; columnX++) {
                        plantSeedInPlace(inv, world, columnX, startY, rowZ, direction);
                    }
                }
                break;
            case EAST:
                startCornerX = startX;
                startCornerZ = startZ - ((width - 1) / 2);

                for(int rowX = startCornerX; rowX < startCornerX + width; ++rowX) {
                    for(int columnZ = startCornerZ; columnZ < startCornerZ + width; columnZ++) {
                        plantSeedInPlace(inv, world, rowX, startY, columnZ, direction);
                    }
                }
                break;
            case SOUTH:
                startCornerX = startX + ((width - 1) / 2);
                startCornerZ = startZ;

                for(int rowZ = startCornerZ; rowZ < startCornerZ + width; ++rowZ) {
                    for(int columnX = startCornerX; columnX > startCornerX - width; columnX--) {
                        plantSeedInPlace(inv, world, columnX, startY, rowZ, direction);
                    }
                }
                break;
            case WEST:
                startCornerX = startX;
                startCornerZ = startZ + ((width - 1) / 2);

                for(int rowX = startCornerX; rowX > startCornerX - width; --rowX) {
                    for(int columnZ = startCornerZ; columnZ > startCornerZ - width; columnZ--) {
                        plantSeedInPlace(inv, world, rowX, startY, columnZ, direction);
                    }
                }
        }
    }

    public boolean plantSeedInPlace(IInventory inv, World world, int x, int y, int z, ForgeDirection direction) {
        int slot = PlantingLogic.getSeedsSlot(inv, getFirstSlot(inv));
        if(slot < 0) {
            return false;
        }
        boolean success = PlantingLogic.placeSeed(inv, world, x, y, z, slot, direction);
        if(success) {
            inv.decrStackSize(slot, 1);
        }

        return success;
    }

    public int getInvSlots() {
        return this.invSlots;
    }
}
