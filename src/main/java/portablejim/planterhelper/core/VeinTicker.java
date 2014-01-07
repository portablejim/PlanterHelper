package portablejim.planterhelper.core;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 7/01/14
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
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
