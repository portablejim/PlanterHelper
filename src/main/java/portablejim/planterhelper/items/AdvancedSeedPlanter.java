package portablejim.planterhelper.items;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import portablejim.planterhelper.PlanterHelper;
import portablejim.planterhelper.core.PlantingLogic;
import portablejim.planterhelper.core.PlantingUtil;
import portablejim.planterhelper.inventories.SmallInventory;
import portablejim.planterhelper.items.lib.InventoryPlanterHelperTool;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdvancedSeedPlanter extends InventoryPlanterHelperTool {
    public AdvancedSeedPlanter(int par1) {
        super(par1);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("advancedSeedPlanter");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

        if(par3EntityPlayer.isSneaking()) {
            if(!par2World.isRemote) {
                //inventory.loadFromNBT(par1ItemStack);
                par3EntityPlayer.openGui(PlanterHelper.instance, 0, par2World, 0, 0, 0);
            }
        }

        return par1ItemStack;
    }

    @Override
    public boolean onItemUse(ItemStack itemStackUsed, EntityPlayer player, World world, int x, int y, int z, int intDirection, float par8, float par9, float par10) {
        if(!player.isSneaking()) {
            int intFacing = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            ForgeDirection[] directions = { ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST };
            if(PlantingUtil.canPlant(player.inventory, world, x, y, z, ForgeDirection.getOrientation(intDirection))) {
                ForgeDirection direction = directions[intFacing];
                /*PlantingLogic.plantSquare(inventory, world, x, y, z, 9, direction, PlantingUtil.getTargetSlot(player.inventory));
                inventory.saveToNBT(player.inventory.getStackInSlot(PlantingUtil.getTargetSlot(player.inventory)));*/
            }

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 1;
    }
}
