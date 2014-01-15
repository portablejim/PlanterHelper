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
    private int xSize;
    private int ySize;
    private int width;
    private int height;

    InventoryPlayer playerInventory;
    SeedInventory seedInventory;

    public SeedPlanterGui(InventoryPlayer playerInv, SeedInventory itemInv)
    {
        super(new SeedContainer(playerInv, itemInv));
        this.playerInventory = playerInv;
        this.seedInventory = itemInv;

        this.width = this.field_146294_l;
        this.height = this.field_146295_m;
        this.xSize = this.field_146999_f;
        this.ySize = this.field_147000_g;

        int i = 222;
        int j = i - 108;
        this.rows = itemInv.getSizeInventory() / 9;

        this.ySize = (j + this.rows * 18);
    }

    /*public void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(this.seedInventory.isInvNameLocalized() ? this.seedInventory.getInvName() : I18n.getString(this.seedInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.isInvNameLocalized() ? this.playerInventory.getInvName() : I18n.getString(this.playerInventory.getInvName()), 8, this.ySize - 94, 4210752);
    }*/

    @Override
    protected void func_146979_b(int p_146979_1_, int p_146979_2_) {
        // Hacky manual coding because the rendering is weird.
        int offset = rows == 3 ? 0 : 28;

        this.field_146289_q.drawString(this.seedInventory.func_145818_k_() ? this.seedInventory.func_145825_b() : I18n.getStringParams(this.seedInventory.func_145825_b()), 8, 6 - offset, 4210752);
        this.field_146289_q.drawString(this.playerInventory.func_145818_k_() ? this.playerInventory.func_145825_b() : I18n.getStringParams(this.playerInventory.func_145825_b()), 8, this.ySize - 94 - offset, 4210752);
    }

    //@Override
    @SuppressWarnings("UnusedParameters")
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.width = this.field_146294_l;
        this.height = this.field_146295_m;
        // GUI Color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.field_146297_k.getTextureManager().bindTexture(background);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, this.xSize, this.rows * 18 + 17);
        drawTexturedModalRect(i, j + this.rows * 18 + 17, 0, 126, this.xSize, 96);
    }

    @Override
    protected void func_146976_a(float var1, int var2, int var3) {
        drawGuiContainerBackgroundLayer(var1, var2, var3);
    }
}
