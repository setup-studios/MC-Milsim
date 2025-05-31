package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;

public class helpFunctions {
    // Provider for Team auto completion
    public static final SuggestionProvider<CommandSourceStack> TEAM_SUGGESTIONS =
            (context, builder) -> {
                for (Teams team : GameplayManager.getTeamList()) {
                    if (team.getName().toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                        builder.suggest(team.getName());
                    }
                }
                return builder.buildFuture();
            };

    // Hilfsmethode um Team anhand des Namens zu finden
    public static Teams findTeamByName(String name) {
        for (Teams team : GameplayManager.getTeamList()) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public static ArrayList<String> getTeamNames() {
        ArrayList<String> teamNames = new ArrayList<>();
        for (Teams team : GameplayManager.getTeamList()) {
            teamNames.add(team.getName());
        }
        return teamNames;
    }

    // In einer Helper-Klasse
    public static final SuggestionProvider<CommandSourceStack> COLOR_SUGGESTIONS =
            (context, builder) -> {
                for (ChatFormatting format : ChatFormatting.values()) {
                    if (format.isColor()) {
                        builder.suggest(format.getName());
                    }
                }
                return builder.buildFuture();
            };

    public static ChatFormatting getChatFormatting(CommandContext<CommandSourceStack> context, String argumentName) {
        String colorName = StringArgumentType.getString(context, argumentName);
        return ChatFormatting.getByName(colorName);
    }
}

