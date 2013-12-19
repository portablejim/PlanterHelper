package portablejim.planterhelper.items;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import portablejim.planterhelper.core.PlantingLogic;
import portablejim.planterhelper.core.PlantingUtil;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 17/12/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicSeedPlanter extends Item {

    public BasicSeedPlanter(int par1) {
        super(par1);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("basicSeedPlanter");
    }

    @Override
    public boolean onItemUse(ItemStack itemStackUsed, EntityPlayer player, World world, int x, int y, int z, int intDirection, float par8, float par9, float par10) {
        int intFacing = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        ForgeDirection[] directions = { ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST };
        if(PlantingUtil.canPlant(player.inventory, world, x, y, z, ForgeDirection.getOrientation(intDirection))) {
            ForgeDirection direction = directions[intFacing];
            PlantingLogic.plantSquare(player.inventory, world, x, y, z, 9, direction, PlantingUtil.getTargetSlot(player.inventory));
        }

        return true;
    }
}
