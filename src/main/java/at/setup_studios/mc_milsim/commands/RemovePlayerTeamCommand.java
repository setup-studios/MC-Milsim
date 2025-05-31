package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayers;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Command handler for removing players from their current team.
 * Provides functionality to unassign players from their teams in the milsim system.
 */
public class RemovePlayerTeamCommand {

    /**
     * Registers the remove player from team command with the dispatcher.
     * Command structure: /milsim teams remove <player>
     * Removes the specified player from their current team.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for removing player from team
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> executeCommand(context))
                                )
                        )
                )
        );
    }

    /**
     * Executes the remove player from team command.
     * Removes the specified player from their current team and sends confirmation message.
     *
     * @param context The command context containing the player argument
     * @return 1 if successful, 0 if player not found
     * @throws CommandSyntaxException If there's an error parsing the player argument
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        // Get command source and target player
        CommandSourceStack source = context.getSource();
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        
        // Get player's mod data
        ModPlayers modPlayer = GameplayManager.getPlayerObject(player);

        // Check if player exists in the mod system
        if (modPlayer == null) {
            source.sendFailure(Component.literal("Player '" + player.getName().getString() + "' not found!"));
            return 0;
        }

        // Store old team for message and remove player from team
        Teams oldTeam = modPlayer.getTeam();
        GameplayManager.removePlayerFromTeam(modPlayer);

        // Send success message with old team name in team's color
        source.sendSuccess(() -> Component.literal("Removed player: " + modPlayer.getName() + " from team: ")
                .append(Component.literal(oldTeam.getName()).withStyle(oldTeam.getColor())), false);

        return 1; // Success
    }
}