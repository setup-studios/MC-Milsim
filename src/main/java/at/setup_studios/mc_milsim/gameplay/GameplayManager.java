package at.setup_studios.mc_milsim.gameplay;

import at.setup_studios.mc_milsim.gameplay.player.ModPlayers;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * The GameplayManager class manages the coordination, assignment, and interaction
 * of players and teams within the game. It provides methods for team creation,
 * player management, and team-player relationships.
 */
public class GameplayManager {
    /** List of all teams in the game */
    private static final ArrayList<Teams> teamList = new ArrayList<>();

    /** Map of player UUIDs to their corresponding ModPlayers objects */
    private static final HashMap<UUID, ModPlayers> playerList = new HashMap<>();

    /* Static initializer block that creates default Red and Blue teams with 5 player capacity each */
    static {
        teamList.add(new Teams("Red", 5, ChatFormatting.RED));
        teamList.add(new Teams("Blue", 5, ChatFormatting.BLUE));
    }
    //Privat constructor since this is a utility class
    private GameplayManager() {
        ModLogger.error("Utility class should not be instantiated");
    }

    /**
     * Retrieves the ModPlayers object associated with a ServerPlayer.
     * @param player The ServerPlayer to get the ModPlayers object for
     * @return The associated ModPlayers object, or null if not found
     */
    public static ModPlayers getPlayerObject (ServerPlayer player) {
        if (player == null) {
            ModLogger.error("Cannot get player object: player is null");
            return null;
        }

        ModPlayers modPlayer = playerList.get(player.getUUID());
        if (modPlayer == null) {
            ModLogger.error("Player object not found: " + player.getName().getString());
        }
        return modPlayer;
    }

    /**
     * Handles player join event by creating a new ModPlayers object and assigning them to a team.
     * @param player The ServerPlayer who joined the game
     */
    public static void onPlayerJoin (ServerPlayer player) {
        // Create new ModPlayer and add to player list
        UUID uuid=player.getUUID();
        ModPlayers modPlayer = new ModPlayers(uuid, player.getName().getString(), player);
        playerList.put(uuid, modPlayer);

        // Assign player to the team with the lowest player count
        Teams team = teamWithLowestCount(teamList);
        team.addPlayer(modPlayer);
    }

    /**
     * Handles player leave event by removing them from their team and the player list.
     * @param player The ServerPlayer who left the game
     */
    public static void onPlayerLeave (ServerPlayer player) {
        UUID uuid=player.getUUID();
        ModPlayers modPlayer = playerList.get(uuid);
        if (modPlayer == null) {
            ModLogger.warn("Player not found in playerList: " + player.getName().getString());
            return;
        }

        // Remove player from their team if they're on one
        Teams team = modPlayer.getTeam();
        if (team != null) team.removePlayer(modPlayer);

        // Remove player from the master list
        playerList.remove(uuid);
    }

    /**
     * Finds the team with the lowest number of players.
     * @param teamList List of teams to search through
     * @return The team with the lowest player count, or null if the list is empty
     */
    public static Teams teamWithLowestCount(ArrayList<Teams> teamList) {
        if (teamList == null || teamList.isEmpty()) {
            ModLogger.warn("teamList is null or empty\n");
            return null;
        }

        // Find team with lowest player count through iteration
        Teams teamWithLowestCount = teamList.get(0);
        for (Teams team: teamList) {
            if (team.getPlayers().size()<teamWithLowestCount.getPlayers().size()) {
                teamWithLowestCount = team;
            }
        }
        return teamWithLowestCount;
    }

    /**
     * Adds a player to a specified team, handling their removal from any previous team.
     * @param player The player to add
     * @param newTeam The team to add the player to
     */
    public static void addPlayerToTeam(ModPlayers player, Teams newTeam) {
        if (player == null) {
            ModLogger.error("Cannot add player to team: player is null");
            return;
        }
        if (newTeam == null) {
            ModLogger.error("Cannot add player to team: team is null");
            return;
        }

        // Remove from old team if necessary
        Teams oldPlayerTeam = player.getTeam();
        if (oldPlayerTeam != null) oldPlayerTeam.removePlayer(player);

        // Add to new team
        player.setTeam(newTeam);
        newTeam.addPlayer(player);
    }

    /**
     * Removes a player from their current team.
     * @param player The player to remove from their team
     */
    public static void removePlayerFromTeam(ModPlayers player) {
        if (player == null) {
            ModLogger.error("Cannot remove player from team: player is null");
            return;
        }

        Teams team = player.getTeam();
        if (team == null) {
            ModLogger.error("Player is not on a Team");
            return;
        }
        team.removePlayer(player);
        player.setTeam(null);
    }

    /**
     * Gets the list of all teams in the game.
     * @return ArrayList containing all teams
     */
    public static ArrayList<Teams> getTeamList() {
        return teamList;
    }

    /**
     * Creates a new team with specified parameters.
     * @param name The name of the team
     * @param maxPlayers Maximum number of players allowed in the team
     * @param color ChatFormatting color for the team
     */
    public static void createTeam(String name, int maxPlayers, ChatFormatting color) {
        // Validate input parameters
        if (name == null || name.trim().isEmpty()) {
            ModLogger.error("Cannot create team: name is null or empty");
            return;
        }
        if (color == null) {
            ModLogger.error("Cannot create team: color is null");
            return;
        }
        if (maxPlayers <= 0) {
            ModLogger.error("Cannot create team: maxPlayers must be positive");
            return;
        }

        // Check for duplicate team names
        for (Teams existingTeam : teamList) {
            if (existingTeam.getName().equalsIgnoreCase(name)) {
                ModLogger.error("Cannot create team: team name already exists");
                return;
            }
        }

        teamList.add(new Teams(name, maxPlayers, color));
    }

    /**
     * Deletes a team and properly handles removal of all its players.
     * @param team The team to delete
     */
    public static void deleteTeam(Teams team) {
        if (team == null) {
            ModLogger.error("Cannot delete team: team is null\n");
            return;
        }

        // Create a copy of the players list to avoid concurrent modification
        ArrayList<ModPlayers> playersToRemove = new ArrayList<>(team.getPlayers());

        // Remove each player from the team
        for (ModPlayers player : playersToRemove) {
            removePlayerFromTeam(player);
        }

        // Finally remove the team from the list
        teamList.remove(team);
    }
}
