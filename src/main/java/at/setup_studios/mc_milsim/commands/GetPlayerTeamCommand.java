package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import at.setup_studios.mc_milsim.gameplay.player.Team;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Command handler for retrieving a player's team information.
 * Provides functionality to check which team a specific player belongs to.
 */
public class GetPlayerTeamCommand {

    /**
     * Registers the get player team command with the dispatcher.
     * Command structure: /milsim teams getteam <player>
     * Returns information about which team the specified player is in.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for getting player team information
        dispatcher.register(
                Commands.literal("milsim")
                        .then(Commands.literal("teams")
                            .then(Commands.literal("getteam")
                                .then(Commands.argument("player", EntityArgument.player())
                                    .executes(context -> executeCommand(context))
                                )
                            )
                        )
                );
    }

    private static int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        // Get command source and target player
        CommandSourceStack source = context.getSource();
        ServerPlayer player = EntityArgument.getPlayer(context, "player");

        // Get player's mod data
        ModPlayer modPlayer = GameplayManager.getPlayerObject(player);
        if (modPlayer == null) {
            source.sendFailure(Component.literal("Player data not found!"));
            return 0;
        }

        // Get player's team information
        Team playerTeam = modPlayer.getTeam();
        if (playerTeam == null) {
            source.sendFailure(Component.literal("Player is not in any team!"));
            return 0;
        }

        // Get team color and name
        ChatFormatting color = playerTeam.getColor();
        String team = playerTeam.getName();
        // Display team info with team's color
        source.sendSuccess(() -> Component.literal(player.getName().getString() + " is in Team: ")
                .append(Component.literal(team).withStyle(color)), false);

        return 1;
    }
}