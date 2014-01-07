package portablejim.planterhelper.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 8/01/14
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
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
