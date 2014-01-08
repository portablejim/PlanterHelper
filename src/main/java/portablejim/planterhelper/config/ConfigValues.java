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

package portablejim.planterhelper.config;

import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * Store values and interface with config file.
 * Values are retrieved from here by the rest of the mod.
 */
public class ConfigValues {
    private Configuration configFile;

    public static final String CONFIG_VEIN = "veinPlanter";

    public int ITEMIDS_BASIC_PLANTER;
    public static final int ITEMIDS_BASIC_PLANTER_DEFAULT = 5432;
    public static final String ITEMIDS_BASIC_PLANTER_NAME = "basicPlanter";

    public int ITEMIDS_ADVANCED_PLANTER;
    public static final int ITEMIDS_ADVANCED_PLANTER_DEFAULT = 5433;
    public static final String ITEMIDS_ADVANCED_PLANTER_NAME = "advancedPlanter";

    public int ITEMIDS_VEIN_PLANTER;
    public static final int ITEMIDS_VEIN_PLANTER_DEFAULT = 5434;
    public static final String ITEMIDS_VEIN_PLANTER_NAME = "veinPlanter";

    public int ITEMIDS_EGG_TOKEN;
    public static final int ITEMIDS_EGG_TOKEN_DEFAULT = 5435;
    public static final String ITEMIDS_EGG_TOKEN_NAME = "dragonEggToken";

    public int VEIN_RANGE;
    public static final int VEIN_RANGE_DEFAULT = 75;
    public static final String VEIN_RANGE_NAME = "veinPlanter.range";
    public static final String VEIN_RANGE_DESCRIPTION = "How far away from where you click to plant. [default: 75]";

    public int VEIN_SPEED;
    public static final int VEIN_SPEED_DEFAULT = 20;
    public static final String VEIN_SPEED_NAME = "veinPlanter.speed";
    public static final String VEIN_SPEED_DESCRIPTION = "How many seeds to plant per-tick. [default: 20]";

    public boolean EASTER_EGG_SHARE;
    public static final boolean EASTER_EGG_SHARE_DEFAULT = false;
    public static final String EASTER_EGG_SHARE_NAME = "specialfeatures";
    public static final String EASTER_EGG_SHARE_DESCRIPTION = "Allow access to special features. [default: false]";

    public ConfigValues(File file) {
        configFile = new Configuration(file);
    }

    public void loadConfigFile() {
        configFile.load();

        ITEMIDS_BASIC_PLANTER = configFile.getItem(ITEMIDS_BASIC_PLANTER_NAME, ITEMIDS_BASIC_PLANTER_DEFAULT).getInt();
        ITEMIDS_ADVANCED_PLANTER = configFile.getItem(ITEMIDS_ADVANCED_PLANTER_NAME, ITEMIDS_ADVANCED_PLANTER_DEFAULT).getInt();
        ITEMIDS_VEIN_PLANTER = configFile.getItem(ITEMIDS_VEIN_PLANTER_NAME, ITEMIDS_VEIN_PLANTER_DEFAULT).getInt();
        ITEMIDS_EGG_TOKEN = configFile.getItem(ITEMIDS_EGG_TOKEN_NAME, ITEMIDS_EGG_TOKEN_DEFAULT).getInt();

        VEIN_RANGE = configFile.get(CONFIG_VEIN, VEIN_RANGE_NAME, VEIN_RANGE_DEFAULT, VEIN_RANGE_DESCRIPTION).getInt();
        VEIN_SPEED = configFile.get(CONFIG_VEIN, VEIN_SPEED_NAME, VEIN_SPEED_DEFAULT, VEIN_SPEED_DESCRIPTION).getInt();

        EASTER_EGG_SHARE = configFile.get(Configuration.CATEGORY_GENERAL, EASTER_EGG_SHARE_NAME, EASTER_EGG_SHARE_DEFAULT, EASTER_EGG_SHARE_DESCRIPTION).getBoolean(EASTER_EGG_SHARE_DEFAULT);

        configFile.save();
    }
}
