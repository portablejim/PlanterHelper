package portablejim.planterhelper.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import portablejim.planterhelper.PlanterHelper;
import portablejim.planterhelper.containers.SmallContainer;
import portablejim.planterhelper.inventories.SmallInventory;
import portablejim.planterhelper.items.AdvancedSeedPlanter;
import portablejim.planterhelper.items.lib.InventoryPlanterHelperTool;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallGuiHandler implements IGuiHandler {
    public SmallGuiHandler() {
        NetworkRegistry.instance().registerGuiHandler(PlanterHelper.instance, this);
    }

    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i2, int i3, int i4) {
        if(entityPlayer == null || entityPlayer.getCurrentEquippedItem() == null || entityPlayer.getCurrentEquippedItem().getItem() == null) {
            return null;
        }

        Item currentItem = entityPlayer.getCurrentEquippedItem().getItem();
        if(!(currentItem instanceof InventoryPlanterHelperTool)) {
            return null;
        }

        switch (i) {
            case 0:
                if(currentItem instanceof AdvancedSeedPlanter) {
                    return new SmallContainer(entityPlayer.inventory, ((AdvancedSeedPlanter)currentItem).getInventory());
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
        if(!(currentItem instanceof InventoryPlanterHelperTool)) {
            return null;
        }

        switch (i) {
            case 0:
                if(currentItem instanceof AdvancedSeedPlanter) {
                    return new SeedPlanterGui(entityPlayer.inventory, ((AdvancedSeedPlanter)currentItem).getInventory());
                }
            default:
                return null;
        }
    }
}