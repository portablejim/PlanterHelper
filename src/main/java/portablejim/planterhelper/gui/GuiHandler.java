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
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
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
