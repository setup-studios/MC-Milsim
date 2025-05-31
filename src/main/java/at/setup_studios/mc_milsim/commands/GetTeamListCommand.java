package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

/**
 * Command handler for listing all teams in the milsim system.
 * Provides functionality to display all currently existing teams.
 */
public class GetTeamListCommand {

    /**
     * Registers the team list command with the dispatcher.
     * Command structure: /milsim teams list
     * Shows all existing teams with their respective colors.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for listing teams
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("list")
                                .executes(context -> executeCommand(context))
                        )
                )
        );
    }

    /**
     * Executes the team list command.
     * Retrieves and displays all existing teams with their respective colors.
     *
     * @param context The command context
     * @return 1 for successful execution
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        // Send header message
        source.sendSuccess(() -> Component.literal("Teams:"), false);
        
        // Iterate through all teams and display them with their respective colors
        for (Teams team : GameplayManager.getTeamList()) {
            source.sendSuccess(() -> Component.literal(team.getName()).withStyle(team.getColor()), false);
        }

        return 1; // Success
    }
}