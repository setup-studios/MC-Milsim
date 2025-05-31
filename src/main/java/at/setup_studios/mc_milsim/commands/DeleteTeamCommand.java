package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Command handler for deleting teams in the milsim system.
 * Handles the /milsim teams delete command which removes existing teams.
 */
public class DeleteTeamCommand {

    /**
     * Registers the delete team command with the dispatcher.
     * Command structure: /milsim teams delete <teamname>
     * The teamname argument includes suggestions based on existing teams.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for team deletion
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("delete")
                                .then(Commands.argument("teamname", StringArgumentType.string())
                                        .suggests(helpFunctions.TEAM_SUGGESTIONS) // Provide suggestions from existing teams
                                        .executes(context -> executeCommand(context))
                                )
                        )
                )
        );
    }

    /**
     * Executes the team deletion command.
     * Attempts to delete the specified team and provides feedback on the operation result.
     *
     * @param context The command context containing the team name to delete
     * @return 1 if deletion was successful, 0 if it failed
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        // Get the team name from command arguments
        String teamName = StringArgumentType.getString(context, "teamname");
        
        // Try to find the team by name
        Teams teamToDelete = helpFunctions.findTeamByName(teamName);

        // Check if team exists
        if (teamToDelete == null) {
            source.sendFailure(Component.literal("Team does not exist!"));
            return 0;
        }

        try {
            // Attempt to delete the team
            GameplayManager.deleteTeam(teamToDelete);
            
            // Send success message with team name in team's color
            source.sendSuccess(() -> Component.literal("Team: ")
                    .append(Component.literal(teamToDelete.getName()).withStyle(teamToDelete.getColor()))
                    .append(Component.literal(" has been deleted")), true);
            return 1;
        } catch (Exception e) {
            // Handle any errors during team deletion
            source.sendFailure(Component.literal("Failed to delete team: " + e.getMessage()));
            return 0;
        }
    }
}