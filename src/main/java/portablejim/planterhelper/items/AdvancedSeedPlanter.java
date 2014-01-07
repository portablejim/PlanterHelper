package portablejim.planterhelper.items;

import portablejim.planterhelper.PlanterHelper;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 22/12/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdvancedSeedPlanter extends Planter {
    public AdvancedSeedPlanter(int par1) {
        super(par1, 27, 9);
        this.setUnlocalizedName("PlanterHelper:advancedSeedPlanter");
        this.setTextureName("PlanterHelper:advancedSeedPlanter");
        this.setContainerItem(PlanterHelper.eggToken);
    }
}
