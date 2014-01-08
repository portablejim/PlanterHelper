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

package portablejim.planterhelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import portablejim.planterhelper.commands.CommandSmiteMe;
import portablejim.planterhelper.config.ConfigValues;
import portablejim.planterhelper.gui.GuiHandler;
import portablejim.planterhelper.items.AdvancedSeedPlanter;
import portablejim.planterhelper.items.BasicSeedPlanter;
import portablejim.planterhelper.items.DragonEggToken;
import portablejim.planterhelper.items.VeinSeedPlanter;
import portablejim.planterhelper.network.PacketHandler;
import java.util.HashSet;

import static cpw.mods.fml.common.Mod.*;

/**
 * Mod that helps with planting crops
 *
 * Main mod class.
 */

@Mod(modid = PlanterHelper.MODID, name = PlanterHelper.NAME)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {PlanterHelper.MODID}, packetHandler = PacketHandler.class)
public class PlanterHelper {
    public static final String MODID = "PlanterHelper";
    public static final String NAME = "Planter Helper";

    @Instance(MODID)
    public static PlanterHelper instance;

    public ConfigValues configValues;

    public static BasicSeedPlanter basicPlanter;
    public static AdvancedSeedPlanter advancedPlanter;
    public static VeinSeedPlanter veinPlanter;
    public static DragonEggToken eggToken;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configValues = new ConfigValues(event.getSuggestedConfigurationFile());
        configValues.loadConfigFile();

        eggToken = new DragonEggToken(configValues.ITEMIDS_EGG_TOKEN);
        basicPlanter = new BasicSeedPlanter(configValues.ITEMIDS_BASIC_PLANTER);
        advancedPlanter = new AdvancedSeedPlanter(configValues.ITEMIDS_ADVANCED_PLANTER);
        veinPlanter = new VeinSeedPlanter(configValues.ITEMIDS_VEIN_PLANTER);

        GameRegistry.registerItem(eggToken, "PlanterHelper:dragonEggToken");
        GameRegistry.registerItem(basicPlanter, "PlanterHelper:basicPlanter");
        GameRegistry.registerItem(advancedPlanter, "PlanterHelper:advancedPlanter");
        GameRegistry.registerItem(veinPlanter, "PlanterHelper:veinPlanter");
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

        ItemStack netherStarStack = new ItemStack(Item.netherStar);
        ItemStack dragonEggStack = new ItemStack(Block.dragonEgg);
        ItemStack chestStack = new ItemStack(Block.chest);
        ItemStack clockStack = new ItemStack(Item.pocketSundial);

        new GuiHandler();

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

        GameRegistry.addRecipe(new ItemStack(veinPlanter), " n ", "dal", " h ",
                'n', netherStarStack,
                'd', dragonEggStack, 'a', advancedPlanter, 'l', clockStack,
                'h', chestStack);

        GameRegistry.addShapelessRecipe(new ItemStack(Block.dragonEgg), eggToken);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        ServerCommandManager scm = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();
        scm.registerCommand(new CommandSmiteMe());
    }

    @ForgeSubscribe
    public void lightningStrike(EntityStruckByLightningEvent event) {
        final int HOTBAR_SIZE = 9;
        Entity entity = event.entity;
        HashSet<String> easterEggUsers = new HashSet<String>();
        easterEggUsers.add("portablejim");
        easterEggUsers.add("straymaverick");

        if(entity instanceof EntityPlayer && easterEggUsers.contains(((EntityPlayer) entity).username.toLowerCase())) {
            EntityPlayer player = (EntityPlayer) entity;
            for(int i = 0; i < HOTBAR_SIZE; i++) {
                ItemStack item = player.inventory.getStackInSlot(i);
                if(item != null && item.getItem() instanceof VeinSeedPlanter && item.getItemDamage() == 0) {
                    item.setItemDamage(1);
                }
            }
        }
    }
}
