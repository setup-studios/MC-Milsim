package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Command handler for displaying the list of players in a specific team.
 * Provides functionality to view all players currently assigned to a team.
 */
public class GetTeamPlayerListCommand {

    /**
     * Registers the team player list command with the dispatcher.
     * Command structure: /milsim teams players <team>
     * The team argument includes suggestions based on existing teams.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for listing team players
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("players")
                                .then(Commands.argument("team", StringArgumentType.string())
                                        .suggests(helpFunctions.TEAM_SUGGESTIONS) // Provide suggestions from existing teams
                                        .executes(context -> executeCommand(context))
                                )
                        )
                )
        );
    }

    /**
     * Executes the team player list command.
     * Displays the team name and all players currently in that team.
     *
     * @param context The command context containing the team name
     * @return 1 if successful, 0 if the team wasn't found
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        // Extract command arguments
        String teamName = StringArgumentType.getString(context, "team");
        CommandSourceStack source = context.getSource();

        // Find team by name
        Teams selectedTeam = helpFunctions.findTeamByName(teamName);

        // Check if team exists
        if (selectedTeam == null) {
            source.sendFailure(Component.literal("Team '" + teamName + "' not found!"));
            return 0;
        }

        // Get team details
        String name = selectedTeam.getName();
        ChatFormatting color = selectedTeam.getColor();
        
        // Display team name with its color
        source.sendSuccess(() -> Component.literal("Team found: ")
                .append(Component.literal(name).withStyle(color)), false);

        // Display team players if available
        if (selectedTeam.getPlayers() != null) {
            source.sendSuccess(() -> Component.literal("Player: " + 
                    selectedTeam.getPlayersAsString().toString()), false);
        }

        return 1; // Success
    }
}