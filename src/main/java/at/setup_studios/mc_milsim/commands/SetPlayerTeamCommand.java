package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import at.setup_studios.mc_milsim.gameplay.player.Team;
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
 * Command handler for adding players to teams.
 * Provides functionality to assign players to specific teams in the milsim system.
 */
public class SetPlayerTeamCommand {

    /**
     * Registers the add player to team command with the dispatcher.
     * Command structure: /milsim teams add <player> <team>
     * Assigns the specified player to the specified team.
     * Includes team name auto-completion suggestions.
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for adding player to team
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("add")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("team", StringArgumentType.string())
                                                .suggests(CommandHelper.TEAM_SUGGESTIONS) // Add team name suggestions
                                                .executes(context -> executeCommand(context))
                                        )
                                )
                        )
                )
        );
    }

    /**
     * Executes the add player to team command.
     * Validates both player and team existence before adding the player to the team.
     *
     * @param context The command context containing player and team arguments
     * @return 1 if successful, 0 if player or team not found
     * @throws CommandSyntaxException If there's an error parsing the player argument
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        // Extract command arguments
        String teamName = StringArgumentType.getString(context, "team");
        CommandSourceStack source = context.getSource();
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ModPlayer modPlayer = GameplayManager.getPlayerObject(player);

        // Find team by name
        Team selectedTeam = CommandHelper.findTeamByName(teamName);

        // Validate team existence
        if (selectedTeam == null) {
            source.sendFailure(Component.literal("Team '" + teamName + "' not found!"));
            return 0;
        }
        
        // Validate player existence in mod system
        if (modPlayer == null) {
            source.sendFailure(Component.literal("Player '" + player.getName().getString() + "' not found!"));
            return 0;
        }

        // Get team color for message formatting
        ChatFormatting color = selectedTeam.getColor();

        // Add player to team
        GameplayManager.addPlayerToTeam(modPlayer, selectedTeam);

        // Send success message with team name in team's color
        source.sendSuccess(() -> Component.literal("Added player: " + modPlayer.getName() + " to team: ")
                .append(Component.literal(teamName).withStyle(color)), false);

        return 1; // Success
    }
}