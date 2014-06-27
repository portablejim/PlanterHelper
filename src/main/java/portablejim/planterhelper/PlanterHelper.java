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

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import portablejim.planterhelper.commands.CommandSmiteMe;
import portablejim.planterhelper.config.ConfigValues;
import portablejim.planterhelper.gui.GuiHandler;
import portablejim.planterhelper.items.AdvancedSeedPlanter;
import portablejim.planterhelper.items.BasicSeedPlanter;
import portablejim.planterhelper.items.DragonEggToken;
import portablejim.planterhelper.items.VeinSeedPlanter;

import java.util.HashMap;
import java.util.HashSet;

import static cpw.mods.fml.common.Mod.EventHandler;
import static cpw.mods.fml.common.Mod.Instance;

/**
 * Mod that helps with planting crops
 *
 * Main mod class.
 */

@Mod(modid = PlanterHelper.MODID, name = PlanterHelper.NAME)
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

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configValues = new ConfigValues(event.getSuggestedConfigurationFile());
        configValues.loadConfigFile();

        eggToken = new DragonEggToken();
        basicPlanter = new BasicSeedPlanter();
        advancedPlanter = new AdvancedSeedPlanter();
        veinPlanter = new VeinSeedPlanter();

        GameRegistry.registerItem(eggToken, "PlanterHelper:dragonEggToken");
        GameRegistry.registerItem(basicPlanter, "PlanterHelper:basicPlanter");
        GameRegistry.registerItem(advancedPlanter, "PlanterHelper:advancedPlanter");
        GameRegistry.registerItem(veinPlanter, "PlanterHelper:veinPlanter");
    }

    @EventHandler
    public void init(@SuppressWarnings("UnusedParameters") FMLInitializationEvent event) {
        ItemStack hoeStack = new ItemStack(Items.iron_hoe);
        ItemStack dispenserStack = new ItemStack(Blocks.dispenser);

        ItemStack basicPlanterStack = new ItemStack(basicPlanter);
        ItemStack hopperStack = new ItemStack(Blocks.hopper);
        ItemStack diamondStack = new ItemStack(Items.diamond);
        ItemStack blazeRodStack = new ItemStack(Items.blaze_rod);
        ItemStack pumpkinStack = new ItemStack(Blocks.pumpkin);

        ItemStack wheatStack = new ItemStack(Items.wheat);
        ItemStack carrotStack = new ItemStack(Items.carrot);
        ItemStack potatoStack = new ItemStack(Items.potato);

        ItemStack netherStarStack = new ItemStack(Items.nether_star);
        ItemStack dragonEggStack = new ItemStack(Blocks.dragon_egg);
        ItemStack chestStack = new ItemStack(Blocks.chest);
        ItemStack clockStack = new ItemStack(Items.clock);

        new GuiHandler();

        /*
         * Item Recipes
         */
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

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dragon_egg), eggToken);

        FMLCommonHandler.instance().bus().register(this);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        HashMap<String, Integer> itemsList = new HashMap<String, Integer>();
        GameData.getItemRegistry().serializeInto(itemsList);
        for(String itemName : itemsList.keySet()) {
            String itemProperName = itemName.startsWith("\u0002") ? itemName.substring(1) : itemName;
            Item item = GameData.getItemRegistry().getObject(itemProperName);
            if(item != null && item instanceof ItemHoe) {
                OreDictionary.registerOre("hoe", new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
            }
        }

        /*
         * Misc Recipes
         */
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.farmland), Blocks.dirt, "hoe"));
    }

    @SuppressWarnings("UnusedDeclaration")
    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        ServerCommandManager scm = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();
        scm.registerCommand(new CommandSmiteMe());
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void craftingFarmland(PlayerEvent.ItemCraftedEvent event) {
        ItemStack farmlandItemStack = new ItemStack(Blocks.farmland);
        ItemStack dirtItemStack = new ItemStack(Blocks.dirt);

        ItemStack itemStack = event.crafting;
        IInventory iInventory = event.craftMatrix;

        int countDirt = 0;
        int countHoe = 0;
        boolean farmland = itemStack != null && farmlandItemStack.isItemEqual(itemStack);
        int hoeSlot = -1;

        for(int i = 0; i < iInventory.getSizeInventory(); i++) {
            ItemStack itemInSlot = iInventory.getStackInSlot(i);

            if(itemInSlot == null) continue;
            if(dirtItemStack.isItemEqual(itemInSlot)) countDirt++;
            if(itemInSlot.getItem() instanceof ItemHoe) {
                countHoe++;
                hoeSlot = i;
            }
        }

        if(countDirt == 1 && countHoe == 1 && farmland) {
            ItemStack hoe = iInventory.getStackInSlot(hoeSlot);
            hoe.stackSize++;
            hoe.damageItem(1, event.player);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void lightningStrike(EntityStruckByLightningEvent event) {
        final int HOTBAR_SIZE = 9;
        Entity entity = event.entity;
        HashSet<String> easterEggUsers = new HashSet<String>();
        easterEggUsers.add("98513389c42a4c9d81fa16c247673e61"); // Portablejim
        easterEggUsers.add("9000e6350e3e422594214a2ce2a92227"); // Straymaverick

        String uuidFull = entity.getUniqueID().toString();
        String uuidStripped = uuidFull.replace("-", "");

        if(easterEggUsers.contains(uuidStripped) || this.configValues.EASTER_EGG_SHARE) {
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
