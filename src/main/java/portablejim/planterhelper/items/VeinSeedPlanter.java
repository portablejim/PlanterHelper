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

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import portablejim.planterhelper.PlanterHelper;
import portablejim.planterhelper.core.VeinPlanterInstance;

import java.util.List;

/**
 * Tier 3 planter that has a large inventory (6 rows) and plants in a
 * connected fashion until the seeds are used up or the radius is reached.
 */
public class VeinSeedPlanter extends Planter {
    public static final String[] ICONSTRING = { "veinSeedPlanter", "veinSeedPlanter_magenta" };
    public static Icon[] icons;

    public VeinSeedPlanter(int itemId) {
        super(itemId, 54, -1);
        this.setUnlocalizedName("PlanterHelper:veinSeedPlanter");
    }

    @Override
    public void plant(EntityPlayer player, IInventory inv, World world, int startX, int startY, int startZ, int width, float playerRotation) {
        int intFacing = MathHelper.floor_double((double) (playerRotation * 4.0F / 360.0F) + 0.5D) & 3;
        ForgeDirection[] directions = { ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST };
        ForgeDirection direction = directions[intFacing];
        if(plantSeedInPlace(inv, world, startX, startY, startZ, direction)) {
            VeinPlanterInstance instance = new VeinPlanterInstance(player, inv, this, world, startX, startY, startZ, direction);
            instance.plantField(startX, startY, startZ);
        }
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        icons = new Icon[ICONSTRING.length];

        for(int i = 0; i < ICONSTRING.length; i++) {
            icons[i] = iconRegister.registerIcon(String.format("%s:%s", PlanterHelper.MODID, ICONSTRING[i]));
        }
    }

    @Override
    public Icon getIconFromDamage(int damage) {
        return icons[damage];
    }

    @Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        for(int i = 0; i < icons.length; i++) {
            ItemStack itemstack = new ItemStack(id, 1, i);
            //noinspection unchecked
            list.add(itemstack);
        }

    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if(par1ItemStack.getItemDamage() == 1) {
            //noinspection unchecked
            par3List.add(StatCollector.translateToLocal(String.format("%s.info1", this.getUnlocalizedName())));
        }
    }
}
