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

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import java.util.EnumSet;

/**
 * Server sided ticker to plant.
 */
public class VeinTicker implements ITickHandler {
    final VeinPlanterInstance instance;
    public VeinTicker(VeinPlanterInstance instance) {
        this.instance = instance;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) { }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        if(type.contains(TickType.SERVER)) {
            instance.plantScheduled();
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return "PlanterHelperVeinTicker";
    }
}
