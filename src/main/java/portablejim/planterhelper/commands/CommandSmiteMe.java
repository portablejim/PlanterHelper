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

package portablejim.planterhelper.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * Command that calls lightning to strike the caller.
 */
public class CommandSmiteMe extends CommandBase {
    private static final String COMMAND_NAME = "smiteme";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "/" + COMMAND_NAME;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        World world = icommandsender.getEntityWorld();
        ChunkCoordinates coordinates = icommandsender.getPlayerCoordinates();

        EntityLightningBolt newBolt = new EntityLightningBolt(world, coordinates.posX, coordinates.posY, coordinates.posZ);
        world.addWeatherEffect(newBolt);
    }

    @Override
    public int compareTo(Object o) {
        return COMMAND_NAME.compareTo((String)o);
    }
}
