package portablejim.planterhelper.items;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import portablejim.planterhelper.PlanterHelper;
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
    private SmallInventory inventory;

    public AdvancedSeedPlanter(int par1) {
        super(par1);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("advancedSeedPlanter");

        inventory = new SmallInventory();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

        if(!par2World.isRemote) {
            inventory.loadFromNBT(par1ItemStack);
        }
        par3EntityPlayer.openGui(PlanterHelper.instance, 0, par2World, 0, 0, 0);

        return par1ItemStack;
    }

    public SmallInventory getInventory() {
        return inventory;
    }
}
