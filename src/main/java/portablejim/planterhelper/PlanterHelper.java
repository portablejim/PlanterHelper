package portablejim.planterhelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import portablejim.planterhelper.gui.SmallGuiHandler;
import portablejim.planterhelper.items.AdvancedSeedPlanter;
import portablejim.planterhelper.items.BasicSeedPlanter;

import static cpw.mods.fml.common.Mod.*;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 17/12/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */

@Mod(modid = PlanterHelper.MODID, version = PlanterHelper.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {PlanterHelper.MODID})
public class PlanterHelper {
    public static final String MODID = "PlanterHelper";
    public static final String VERSION = "0.1";

    @Instance(MODID)
    public static PlanterHelper instance;

    public static BasicSeedPlanter basicPlanter;
    public static AdvancedSeedPlanter advancedPlanter;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        basicPlanter = new BasicSeedPlanter(5432);
        advancedPlanter = new AdvancedSeedPlanter(5433);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ItemStack hoeStack = new ItemStack(Item.hoeIron);
        ItemStack dispenserStack = new ItemStack(Block.dispenser);

        ItemStack basicPlanterStack = new ItemStack(basicPlanter);
        ItemStack hopperStack = new ItemStack(Block.hopperBlock);
        ItemStack diamondStack = new ItemStack(Item.diamond);
        ItemStack blazeRodStack = new ItemStack(Item.blazeRod);
        ItemStack pumpkinStack = new ItemStack(Block.pumpkin);

        ItemStack wheatStack = new ItemStack(Item.wheat);
        ItemStack carrotStack = new ItemStack(Item.carrot);
        ItemStack potatoStack = new ItemStack(Item.potato);

        new SmallGuiHandler();

        GameRegistry.addRecipe(new ItemStack(basicPlanter), "wcp", " d ", " h ",
                'w', wheatStack, 'c', carrotStack, 'p', potatoStack,
                'd', dispenserStack,
                'h', hoeStack);
        GameRegistry.addRecipe(new ItemStack(basicPlanter), "wpc", " d ", " h ",
                'w', wheatStack, 'c', carrotStack, 'p', potatoStack,
                'd', dispenserStack,
                'h', hoeStack);
        GameRegistry.addRecipe(new ItemStack(basicPlanter), "cwp", " d ", " h ",
                'w', wheatStack, 'c', carrotStack, 'p', potatoStack,
                'd', dispenserStack,
                'h', hoeStack);

        GameRegistry.addRecipe(new ItemStack(advancedPlanter), "dhp", " b ", " r ",
                'd', diamondStack,
                'h', hopperStack,
                'p', pumpkinStack,
                'b', basicPlanterStack,
                'r', blazeRodStack);

        LanguageRegistry.addName(basicPlanter, "Basic Planter");
        LanguageRegistry.addName(advancedPlanter, "Advanced Planter");
    }


}