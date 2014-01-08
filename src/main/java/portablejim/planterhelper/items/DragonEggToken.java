package portablejim.planterhelper.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 7/01/14
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class DragonEggToken extends Item {
    public DragonEggToken(int par1) {
        super(par1);
        this.setUnlocalizedName("PlanterHelper:DragonEggToken");
        this.setTextureName("PlanterHelper:dragonEggToken");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        //noinspection unchecked
        par3List.add(StatCollector.translateToLocal(String.format("%s.info1", this.getUnlocalizedName())));
    }
}
