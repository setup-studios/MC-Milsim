package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayers;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GetPlayerTeamCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("milsim")
                        .then(Commands.literal("teams")
                            .then(Commands.literal("getteam")
                                .then(Commands.argument("player", EntityArgument.player())
                                    .executes(context -> {
                                        CommandSourceStack source = context.getSource();
                                        ServerPlayer player = EntityArgument.getPlayer(context, "player");
                                        ModPlayers modPlayer = GameplayManager.getPlayerObject(player);
                                        if (modPlayer == null) {
                                            source.sendFailure(Component.literal("Player data not found!"));
                                            return 0;
                                        }
                                        Teams playerTeam = modPlayer.getTeam();
                                        if (playerTeam == null) {
                                            source.sendFailure(Component.literal("Player is not in any team!"));
                                            return 0;
                                        }
                                        ChatFormatting color = modPlayer.getTeam().getColor();
                                        String team = playerTeam.getName();
                                        if (team == null) {
                                            source.sendFailure(Component.literal("The player was not found!"));
                                        }
                                        else {
                                            source.sendSuccess(() -> Component.literal(player.getName().getString() + " is in Team: ").append(Component.literal(team).withStyle(color)), false);
                                        }
                                        return 1;
                                    })
                                )
                            )
                        )
                );
    }
}
