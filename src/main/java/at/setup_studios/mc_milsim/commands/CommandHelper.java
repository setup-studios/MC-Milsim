package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.Team;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;

/**
 * Utility class providing helper functions for command processing.
 * Contains methods for team management, suggestions, and color handling.
 */
public class CommandHelper {
    
    /**
     * Suggestion provider for team name auto-completion.
     * Suggests existing team names that match the partial input.
     */
    public static final SuggestionProvider<CommandSourceStack> TEAM_SUGGESTIONS =
            (context, builder) -> {
                // Iterate through all teams and suggest matching names
                for (Team team : GameplayManager.getTeamList()) {
                    if (team.getName().toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                        builder.suggest(team.getName());
                    }
                }
                return builder.buildFuture();
            };

    /**
     * Finds a team by its name.
     * Case-insensitive search through all existing teams.
     *
     * @param name The name of the team to find
     * @return The found team or null if no team matches the name
     */
    public static Team findTeamByName(String name) {
        for (Team team : GameplayManager.getTeamList()) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Gets a list of all team names.
     *
     * @return ArrayList containing all team names
     */
    public static ArrayList<String> getTeamNames() {
        ArrayList<String> teamNames = new ArrayList<>();
        // Collect all team names into a list
        for (Team team : GameplayManager.getTeamList()) {
            teamNames.add(team.getName());
        }
        return teamNames;
    }

    /**
     * Suggestion provider for chat color auto-completion.
     * Suggests all available Minecraft chat colors.
     */
    public static final SuggestionProvider<CommandSourceStack> COLOR_SUGGESTIONS =
            (context, builder) -> {
                // Suggest all available color formats
                for (ChatFormatting format : ChatFormatting.values()) {
                    if (format.isColor()) {
                        if (format.getName().toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                            builder.suggest(format.getName());
                        }
                    }
                }
                return builder.buildFuture();
            };

    /**
     * Retrieves ChatFormatting from command context.
     * 
     * @param context The command context containing the color argument
     * @param argumentName The name of the argument containing the color name
     * @return The corresponding ChatFormatting object
     */
    public static ChatFormatting getChatFormatting(CommandContext<CommandSourceStack> context, String argumentName) {
        // Extract color name from command argument and convert to ChatFormatting
        String colorName = StringArgumentType.getString(context, argumentName);
        return ChatFormatting.getByName(colorName);
    }
}