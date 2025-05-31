package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Command handler for creating new teams in the milsim system.
 * Registers and processes the /milsim teams create command.
 */
public class CreateTeamCommand {

    /**
     * Registers the create team command with the dispatcher.
     * Command structure: /milsim teams create <teamname> <maxplayers> <color>
     *
     * @param dispatcher The command dispatcher to register the command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for team creation
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("create")
                                .then(Commands.argument("teamname", StringArgumentType.string())
                                        .then(Commands.argument("maxplayers", IntegerArgumentType.integer())
                                                .then(Commands.argument("color", StringArgumentType.string())
                                                        .suggests(helpFunctions.COLOR_SUGGESTIONS)
                                                        .executes(context -> executeCommand(context))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    /**
     * Executes the team creation command.
     * Creates a new team with the specified parameters and sends feedback to the command source.
     *
     * @param context The command context containing all command arguments
     * @return 1 if successful, -1 if failed
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        // Validate color argument
        ChatFormatting color = helpFunctions.getChatFormatting(context, "color");
        if (color == null || !color.isColor()) {
            source.sendFailure(Component.literal("Color is not valid"));
            return -1;
        }

        // Extract command arguments
        int maxPlayers = IntegerArgumentType.getInteger(context,"maxplayers");
        String teamName = StringArgumentType.getString(context, "teamname");
        
        // Create the team
        GameplayManager.createTeam(teamName, maxPlayers, color);

        // Send success feedback with team details
        source.sendSuccess(() -> Component.literal("Team has been created").withStyle(ChatFormatting.GREEN), false);
        source.sendSuccess(() -> Component.literal("  Name:           ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(teamName).withStyle(ChatFormatting.WHITE)), false);
        source.sendSuccess(() -> Component.literal("  Max Players: ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(String.valueOf(maxPlayers)).withStyle(ChatFormatting.YELLOW)), false);
        source.sendSuccess(() -> Component.literal("  Color:          ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(color.getName()).withStyle(color)), false);

        return 1;
    }
}