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

package portablejim.planterhelper.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Token that can be crafted back to a dragon egg.
 * Used to allow the tier 3 to be crafted with a dragon egg without loosing
 * the egg.
 */
public class DragonEggToken extends Item {
    public DragonEggToken(int par1) {
        super(par1);
        this.setUnlocalizedName("PlanterHelper:dragonEggToken");
        this.setTextureName("PlanterHelper:dragonEggToken");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        //noinspection unchecked
        par3List.add(StatCollector.translateToLocal(String.format("%s.info1", this.getUnlocalizedName())));
    }
}
