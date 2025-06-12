package at.setup_studios.mc_milsim.events;

import at.setup_studios.mc_milsim.commands.*;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mc_milsim")
public class CommandEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        GetPlayerTeamCommand.register(event.getDispatcher());
        GetTeamPlayerListCommand.register(event.getDispatcher());
        SpawnPlayerCommand.register(event.getDispatcher());
        GetTeamListCommand.register(event.getDispatcher());
        CreateTeamCommand.register(event.getDispatcher());
        DeleteTeamCommand.register(event.getDispatcher());
        GetTeamInfoCommand.register(event.getDispatcher());
        RemovePlayerTeamCommand.register(event.getDispatcher());
        SpawnPlayerCommand.register(event.getDispatcher());
        SetPlayerTeamCommand.register(event.getDispatcher());
    }
}
