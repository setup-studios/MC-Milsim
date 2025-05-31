package at.setup_studios.mc_milsim.commands;// Command Klasse

import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class GetTeamPlayerListCommand {


    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("teams")
                        .then(Commands.literal("players")
                                .then(Commands.argument("team", StringArgumentType.string())
                                        .suggests(helpFunctions.TEAM_SUGGESTIONS)
                                        .executes(context -> executeCommand(context))
                                )
                        )
                )
        );
    }

    private static int executeCommand(CommandContext<CommandSourceStack> context) {
        String teamName = StringArgumentType.getString(context, "team");
        CommandSourceStack source = context.getSource();


        // Team anhand des Namens finden
        Teams selectedTeam = helpFunctions.findTeamByName(teamName);

        if (selectedTeam == null) {
            source.sendFailure(Component.literal("Team '" + teamName + "' not found!"));
            return 0;
        }

        String name = selectedTeam.getName();
        ChatFormatting color = selectedTeam.getColor();
        // Hier kannst du mit dem gefundenen Team arbeiten
        source.sendSuccess(() -> Component.literal("Team found: ").append(Component.literal(name).withStyle(color)), false);

        // Beispiel: Alle Spieler des Teams anzeigen (falls du eine players Liste hast)
        if (selectedTeam.getPlayers() != null) {
            source.sendSuccess(() -> Component.literal("Player: " + selectedTeam.getPlayersAsString().toString()), false);
        }

        return 1; // Erfolg
    }
}

