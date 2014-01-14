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

package portablejim.planterhelper.items;

import portablejim.planterhelper.PlanterHelper;

/**
 * Tier 2 planter with a small inventory (3 rows) that plants a medium
 * sized area (9x9)
 */
public class AdvancedSeedPlanter extends Planter {
    public AdvancedSeedPlanter() {
        super(27, 9);
        this.setUnlocalizedName("PlanterHelper:advancedSeedPlanter");
        this.setTextureName("PlanterHelper:advancedSeedPlanter");
        this.setContainerItem(PlanterHelper.eggToken);
    }
}
