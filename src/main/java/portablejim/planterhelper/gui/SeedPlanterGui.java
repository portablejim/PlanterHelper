package portablejim.planterhelper.gui;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import portablejim.planterhelper.containers.SmallContainer;
import portablejim.planterhelper.inventories.SmallInventory;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedPlanterGui extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/generic_54.png");

    private int rows;

    InventoryPlayer playerInventory;
    SmallInventory seedInventory;

    public SeedPlanterGui(InventoryPlayer playerInv, SmallInventory itemInv)
    {
        super(new SmallContainer(playerInv, itemInv));
        this.playerInventory = playerInv;
        this.seedInventory = itemInv;

        int i = 222;
        int j = i - 108;
        this.rows = itemInv.getSizeInventory() / 9;

        this.ySize = (j + this.rows * 18);
    }

    public void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(this.seedInventory.isInvNameLocalized() ? this.seedInventory.getInvName() : I18n.getString(this.seedInventory.getInvName()), 8, 6, 7210752);
        this.fontRenderer.drawString(this.playerInventory.isInvNameLocalized() ? this.playerInventory.getInvName() : I18n.getString(this.playerInventory.getInvName()), 8, this.ySize - 96, 7210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        // GUI Color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(background);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, this.xSize, this.rows * 18 + 17);
        drawTexturedModalRect(i, j + this.rows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
