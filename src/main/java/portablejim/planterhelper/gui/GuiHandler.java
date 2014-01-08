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

package portablejim.planterhelper.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import portablejim.planterhelper.PlanterHelper;
import portablejim.planterhelper.items.AdvancedSeedPlanter;
import portablejim.planterhelper.items.Planter;
import portablejim.planterhelper.items.VeinSeedPlanter;

/**
 * Handle the GUI/container opening code.
 */
public class GuiHandler implements IGuiHandler {
    public GuiHandler() {
        NetworkRegistry.instance().registerGuiHandler(PlanterHelper.instance, this);
    }

    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i2, int i3, int i4) {
        if(entityPlayer == null || entityPlayer.getCurrentEquippedItem() == null || entityPlayer.getCurrentEquippedItem().getItem() == null) {
            return null;
        }

        Item currentItem = entityPlayer.getCurrentEquippedItem().getItem();
        if(!(currentItem instanceof Planter)) {
            return null;
        }

        switch (i) {
            case 0:
                if(currentItem instanceof AdvancedSeedPlanter || currentItem instanceof VeinSeedPlanter) {
                    return new SeedContainer(entityPlayer.inventory, new SeedInventory(entityPlayer.getHeldItem()));
                }
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i2, int i3, int i4) {
        if(entityPlayer == null || entityPlayer.getCurrentEquippedItem() == null || entityPlayer.getCurrentEquippedItem().getItem() == null) {
            return null;
        }

        Item currentItem = entityPlayer.getCurrentEquippedItem().getItem();
        if(!(currentItem instanceof Planter)) {
            return null;
        }

        switch (i) {
            case 0:
                if(currentItem instanceof AdvancedSeedPlanter || currentItem instanceof VeinSeedPlanter) {
                    return new SeedPlanterGui(entityPlayer.inventory, new SeedInventory(entityPlayer.getHeldItem()));
                }
            default:
                return null;
        }
    }
}
