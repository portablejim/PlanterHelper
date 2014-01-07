package portablejim.planterhelper.items;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import portablejim.planterhelper.core.VeinPlanterInstance;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 7/01/14
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class VeinSeedPlanter extends Planter {

    public VeinSeedPlanter(int itemId) {
        super(itemId, 54, -1);
        this.setUnlocalizedName("PlanterHelper:veinSeedPlanter");
        this.setTextureName("PlanterHelper:veinSeedPlanter");
    }

    @Override
    public void plant(IInventory inv, World world, int startX, int startY, int startZ, int width, float playerRotation) {
        int intFacing = MathHelper.floor_double((double) (playerRotation * 4.0F / 360.0F) + 0.5D) & 3;
        ForgeDirection[] directions = { ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST };
        ForgeDirection direction = directions[intFacing];
        if(plantSeedInPlace(inv, world, startX, startY, startZ, direction)) {
            VeinPlanterInstance instance = new VeinPlanterInstance(inv, this, world, startX, startY, startZ, direction);
            instance.plantField(startX, startY, startZ);
        }
    }
}
