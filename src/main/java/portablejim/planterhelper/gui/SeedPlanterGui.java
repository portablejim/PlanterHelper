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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Varible sized GUI that auto-resizes (like the chest GUI) to the inventory
 * size.
 */
public class SeedPlanterGui extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/generic_54.png");

    private int rows;

    InventoryPlayer playerInventory;
    SeedInventory seedInventory;

    public SeedPlanterGui(InventoryPlayer playerInv, SeedInventory itemInv)
    {
        super(new SeedContainer(playerInv, itemInv));
        this.playerInventory = playerInv;
        this.seedInventory = itemInv;

        int i = 222;
        int j = i - 108;
        this.rows = itemInv.getSizeInventory() / 9;

        this.ySize = (j + this.rows * 18);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.seedInventory.hasCustomInventoryName() ? this.seedInventory.getInventoryName() : I18n.format(this.seedInventory.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.hasCustomInventoryName() ? this.playerInventory.getInventoryName() : I18n.format(this.playerInventory.getInventoryName()), 8, this.ySize - 94, 4210752);
    }

    //@Override
    @SuppressWarnings("UnusedParameters")
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
