package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.player.Team;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Command handler for displaying team information.
 * Provides detailed information about a specific team including its properties and members.
 */
public class GetTeamInfoCommand {

    /**
     * Registers the team info command with the dispatcher.
     * Command structure: /milsim teams info <teamname>
     * Includes auto-completion for team names.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for team info display
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("info")
                                .then(Commands.argument("teamname", StringArgumentType.string())
                                        .suggests(CommandHelper.TEAM_SUGGESTIONS) // Add team name suggestions
                                        .executes(context -> executeCommand(context))
                                )
                        )
                )
        );
    }

    /**
     * Executes the team info command.
     * Retrieves and displays detailed information about the specified team.
     *
     * @param context The command context containing the team name
     * @return 1 if successful, 0 if team not found or error occurs
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        // Get command source and team name
        CommandSourceStack source = context.getSource();
        String teamName = StringArgumentType.getString(context, "teamname");
        
        // Find team by name
        Team teamForInfo = CommandHelper.findTeamByName(teamName);

        // Validate team existence
        if (teamForInfo == null) {
            source.sendFailure(Component.literal("Team does not exist!"));
            return 0;
        }

        // Use team's toString method to get formatted info
        source.sendSuccess(() -> Component.literal(teamForInfo.toString()), false);
        return 1; // Success

    }
}