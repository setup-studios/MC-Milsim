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

public class CreateTeamCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
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
    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ChatFormatting color = helpFunctions.getChatFormatting(context, "color");
        if (color == null || !color.isColor()) {
            source.sendFailure(Component.literal("Color is not valid"));
        }
        int maxPlayers = IntegerArgumentType.getInteger(context,"maxplayers");
        String teamName = StringArgumentType.getString(context, "teamname");
        GameplayManager.createTeam(teamName, maxPlayers, color);

        source.sendSuccess(() -> Component.literal("Team has been created").withStyle(ChatFormatting.GREEN), false);

        source.sendSuccess(() -> Component.literal("  Name:           ").withStyle(ChatFormatting.GRAY).append(Component.literal(teamName).withStyle(ChatFormatting.WHITE)), false);

        source.sendSuccess(() -> Component.literal("  Max Players: ").withStyle(ChatFormatting.GRAY).append(Component.literal(String.valueOf(maxPlayers)).withStyle(ChatFormatting.YELLOW)), false);

        source.sendSuccess(() -> Component.literal("  Color:          ").withStyle(ChatFormatting.GRAY).append(Component.literal(color.getName()).withStyle(color)), false);

        return 1; // Erfolg
    }
}
