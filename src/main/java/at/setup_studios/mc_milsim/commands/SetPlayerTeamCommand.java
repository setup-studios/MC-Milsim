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

public class SetPlayerTeamCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("add")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("team", StringArgumentType.string())
                                                .suggests(helpFunctions.TEAM_SUGGESTIONS)
                                                .executes(context -> executeCommand(context))
                                        )
                                )
                        )
                )

        );

    }
    private static int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String teamName = StringArgumentType.getString(context, "team");
        CommandSourceStack source = context.getSource();
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ModPlayers modPlayer = GameplayManager.getPlayerObject(player);


        // Team anhand des Namens finden
        Teams selectedTeam = helpFunctions.findTeamByName(teamName);

        if (selectedTeam == null) {
            source.sendFailure(Component.literal("Team '" + teamName + "' not found!"));
            return 0;
        }
        if (modPlayer == null) {
            source.sendFailure(Component.literal("Player '" + player.getName().getString() + "' not found!"));
            return 0;
        }

        ChatFormatting color = selectedTeam.getColor();

        GameplayManager.addPlayerToTeam(modPlayer, selectedTeam);

        source.sendSuccess(() -> Component.literal("Added player: " + modPlayer.getName() + " to team: ").append(Component.literal(teamName).withStyle(color)), false);

        return 1; // Erfolg

    }
}
