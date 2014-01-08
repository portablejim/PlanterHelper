package portablejim.planterhelper.core;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import portablejim.planterhelper.PlanterHelper;
import portablejim.planterhelper.gui.SeedInventory;
import portablejim.planterhelper.items.Planter;
import portablejim.planterhelper.util.Point;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 7/01/14
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class VeinPlanterInstance {
    private ConcurrentLinkedQueue<Point> plantQueue;
    private World world;
    private ForgeDirection direction;
    private boolean finished;
    private Planter usedPlanter;
    private SeedInventory inventory;
    private Point initialBlock;

    public VeinPlanterInstance(IInventory inventory, Planter usedPlanter, World world, int x, int y, int z, ForgeDirection direction) {
        this.plantQueue = new ConcurrentLinkedQueue<Point>();
        this.world = world;
        this.direction = direction;
        this.finished = false;
        this.usedPlanter = usedPlanter;
        if(inventory instanceof SeedInventory) {
            this.inventory = (SeedInventory) inventory;
        }
        initialBlock = new Point(x, y, z);

        TickRegistry.registerTickHandler(new VeinTicker(this), Side.SERVER);
    }

    public synchronized void plantField(int x, int y, int z) {
        if(this.world == null || this.usedPlanter == null || this.inventory == null
                || !(this.usedPlanter instanceof Planter)) {
            finished = true;
        }

        if(finished) {
            return;
        }

        byte d = 1;
        for(int dx = -d; dx <= d; dx++) {
            for(int dy = -d; dy <= d; dy++) {
                for(int dz = -d; dz <= d; dz++) {
                    if(dx == 0 && dz == 0) {
                        continue;
                    }

                    Point blockPos = new Point(x + dx, y + dy, z + dz);

                    int range = PlanterHelper.instance.configValues.VEIN_RANGE;

                    if(!initialBlock.isWithinRange(blockPos, range) && range > 0) {
                        continue;
                    }

                    if(usedPlanter.canPlant(inventory, world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), direction)) {
                        boolean success = usedPlanter.plantSeedInPlace(inventory, world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), direction);
                        if(success) {
                            plantQueue.add(blockPos);
                        }

                        if(PlantingLogic.getSeedsSlot(inventory, usedPlanter.getFirstSlot(inventory)) < 0) {
                            finished = true;
                        }
                    }
                }
            }
        }
    }

    public void plantScheduled() {
        int speed = PlanterHelper.instance.configValues.VEIN_SPEED;
        for(int i = 0; i < speed; i++) {
            if(!plantQueue.isEmpty()) {
                Point target = plantQueue.remove();
                plantField(target.getX(), target.getY(), target.getZ());
            }
            else{
                return;
            }
        }
    }
}
