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
 * Actually coordinates the connected planting of the crops.
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
