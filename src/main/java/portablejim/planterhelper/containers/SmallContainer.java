package portablejim.planterhelper.containers;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import portablejim.planterhelper.inventories.SmallInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallContainer extends ContainerChest {
    private SmallInventory inv;

    public SmallContainer(InventoryPlayer player, SmallInventory inventory) {
        super(player, inventory);
        this.inv = inventory;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        inv.saveToNBT(player.getCurrentEquippedItem());
        inv.clearInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return inv.isUseableByPlayer(entityPlayer);
    }
}
