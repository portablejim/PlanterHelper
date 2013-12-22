package portablejim.planterhelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import portablejim.planterhelper.containers.SmallContainer;
import portablejim.planterhelper.inventories.SmallInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallGui extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/generic_54.png");

    public SmallGui(InventoryPlayer invPlayer, SmallInventory inventory) {
        super(new SmallContainer(invPlayer, inventory));

        this.ySize = 168;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
