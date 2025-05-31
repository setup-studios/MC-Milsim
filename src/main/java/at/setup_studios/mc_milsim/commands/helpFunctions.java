package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;

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
}

