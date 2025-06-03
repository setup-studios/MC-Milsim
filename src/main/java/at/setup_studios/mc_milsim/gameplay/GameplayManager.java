package at.setup_studios.mc_milsim.gameplay;

import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import at.setup_studios.mc_milsim.gameplay.player.PlayerRole;
import at.setup_studios.mc_milsim.gameplay.player.Team;
import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The GameplayManager class manages the coordination, assignment, and interaction
 * of players and teams within the game. It provides methods for team creation,
 * player management, and team-player relationships.
 */
public class GameplayManager {
    /** List of all teams in the game */
    private static final ArrayList<Team> teamList = new ArrayList<>();

    /** Map of player UUIDs to their corresponding ModPlayers objects */
    private static final HashMap<UUID, ModPlayer> playerList = new HashMap<>();

    /* Static initializer block that creates default Red and Blue teams with 5 player capacity each */
    static {
        teamList.add(new Team("Red", 5, ChatFormatting.RED));
        teamList.add(new Team("Blue", 5, ChatFormatting.BLUE));
    }
/* THIS IS JUST FOR TESTING
    private static final PlayerRole RIFLER;

    static {
        RIFLER = new PlayerRole("Rifler", "THE RIFLER", 5, "THE SMALL FUCKER, that loves to shoot guns");
        RIFLER.addItem(0, new ItemStack(Items.BOW));
        RIFLER.addItem(1, new ItemStack(Items.GOLDEN_APPLE, 32));
        RIFLER.addItem(9, new ItemStack(Items.ARROW, 64));
        RIFLER.addItem(15, new ItemStack(Items.ARROW, 64));
        RIFLER.addItem(25, new ItemStack(Items.EGG, 5));
        List<ItemStack> armor = new ArrayList<>();
        armor.add(new ItemStack(Items.CHAINMAIL_BOOTS));
        armor.add(new ItemStack(Items.NETHERITE_LEGGINGS));
        armor.add(new ItemStack(Items.DIAMOND_CHESTPLATE));
        armor.add(new ItemStack(Items.NETHERITE_HELMET));
        RIFLER.setArmor(armor);
    }
*/
    //Privat constructor since this is a utility class
    private GameplayManager() {
        ModLogger.error("Utility class should not be instantiated");
    }

    /**
     * Retrieves the ModPlayers object associated with a ServerPlayer.
     * @param player The ServerPlayer to get the ModPlayers object for
     * @return The associated ModPlayers object, or null if not found
     */
    public static ModPlayer getPlayerObject (ServerPlayer player) {
        if (player == null) {
            ModLogger.error("Cannot get player object: player is null");
            return null;
        }

        ModPlayer modPlayer = playerList.get(player.getUUID());
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
        ModPlayer modPlayer = new ModPlayer(uuid, player.getName().getString(), player);
        playerList.put(uuid, modPlayer);

        // Assign player to the team with the lowest player count
        Team team = teamWithLowestCount(teamList);
        team.addPlayer(modPlayer);
    }

    /**
     * Handles player leave event by removing them from their team and the player list.
     * @param player The ServerPlayer who left the game
     */
    public static void onPlayerLeave (ServerPlayer player) {
        UUID uuid=player.getUUID();
        ModPlayer modPlayer = playerList.get(uuid);
        if (modPlayer == null) {
            ModLogger.warn("Player not found in playerList: " + player.getName().getString());
            return;
        }

        // Remove player from their team if they're on one
        Team team = modPlayer.getTeam();
        if (team != null) team.removePlayer(modPlayer);

        // Remove player from the master list
        playerList.remove(uuid);
    }

    /**
     * Finds the team with the lowest number of players.
     * @param teamList List of teams to search through
     * @return The team with the lowest player count, or null if the list is empty
     */
    public static Team teamWithLowestCount(ArrayList<Team> teamList) {
        if (teamList == null || teamList.isEmpty()) {
            ModLogger.warn("teamList is null or empty\n");
            return null;
        }

        // Find team with lowest player count through iteration
        Team teamWithLowestCount = teamList.get(0);
        for (Team team: teamList) {
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
    public static void addPlayerToTeam(ModPlayer player, Team newTeam) {
        if (player == null) {
            ModLogger.error("Cannot add player to team: player is null");
            return;
        }
        if (newTeam == null) {
            ModLogger.error("Cannot add player to team: team is null");
            return;
        }

        // Remove from old team if necessary
        Team oldPlayerTeam = player.getTeam();
        if (oldPlayerTeam != null) oldPlayerTeam.removePlayer(player);

        // Add to new team
        player.setTeam(newTeam);
        newTeam.addPlayer(player);
    }

    /**
     * Removes a player from their current team.
     * @param player The player to remove from their team
     */
    public static void removePlayerFromTeam(ModPlayer player) {
        if (player == null) {
            ModLogger.error("Cannot remove player from team: player is null");
            return;
        }

        Team team = player.getTeam();
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
    public static ArrayList<Team> getTeamList() { return new ArrayList<>(teamList); }

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
        for (Team existingTeam : teamList) {
            if (existingTeam.getName().equalsIgnoreCase(name)) {
                ModLogger.error("Cannot create team: team name already exists");
                return;
            }
        }

        teamList.add(new Team(name, maxPlayers, color));
    }

    /**
     * Deletes a team and properly handles removal of all its players.
     * @param team The team to delete
     */
    public static void deleteTeam(Team team) {
        if (team == null) {
            ModLogger.error("Cannot delete team: team is null\n");
            return;
        }

        // Create a copy of the players list to avoid concurrent modification
        ArrayList<ModPlayer> playersToRemove = new ArrayList<>(team.getPlayers());

        // Remove each player from the team
        for (ModPlayer player : playersToRemove) {
            removePlayerFromTeam(player);
        }

        // Finally remove the team from the list
        teamList.remove(team);
    }
}
