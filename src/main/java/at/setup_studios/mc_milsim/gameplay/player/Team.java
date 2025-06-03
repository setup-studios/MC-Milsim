package at.setup_studios.mc_milsim.gameplay.player;

import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.ChatFormatting;
import java.util.ArrayList;

/**
 * Represents a team in the game with players management functionality.
 * Each team has a name, maximum player capacity, and a specific color.
 */
public class Team {
    // Team properties
    private final String name;
    private final int maxPlayers;
    private final ChatFormatting nameColor;
    private final ArrayList<ModPlayer> playerList;

    /**
     * Constructs a new team with specified parameters.
     *
     * @param teamName The name of the team
     * @param maxPlayers The maximum number of players allowed in the team
     * @param color The color formatting for the team's name
     */
    public Team(String teamName, int maxPlayers, ChatFormatting color) {
        if (teamName == null || teamName.trim().isEmpty()) {
            ModLogger.error("Team name cannot be null or empty");
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        if (maxPlayers <= 0) {
            ModLogger.error("Maximum players must be positive, got: " + maxPlayers);
            throw new IllegalArgumentException("Maximum players must be positive");
        }
        if (color == null) {
            ModLogger.error("Team color cannot be null");
            throw new IllegalArgumentException("Team color cannot be null");
        }

        this.name = teamName;
        this.maxPlayers = maxPlayers;
        this.nameColor = color;
        this.playerList  = new ArrayList<>();
        ModLogger.info("Created new team: " + teamName + " with max players: " + maxPlayers);
    }

    /**
     * Adds a player to the team if there's available space.
     * If the team is full or player is null, appropriate messages are logged.
     *
     * @param player The player to be added to the team
     */
    public void addPlayer(ModPlayer player) {
        if (player == null) {
            ModLogger.error("Cannot add null player to team: " + this.name);
        }

        if (!hasAvailableSpace()) {
            ModLogger.warn("Cannot add player to team " + this.name + ": team is full (" + playerList.size() + "/" + maxPlayers + ")");
            return;
        }

        playerList.add(player);
        player.setTeam(this);
        ModLogger.info("Added player " + player.getName() + " to team " + this.name);
    }

    /**
     * Removes a player from the team.
     * Logs a warning if the player is null.
     *
     * @param player The player to be removed from the team
     */
    public void removePlayer(ModPlayer player) {
        if (player == null) {
            ModLogger.error("Cannot remove null player from team: " + this.name);
        }

        boolean removed = this.playerList.remove(player);
        if (removed) {
            ModLogger.info("Removed player " + player.getName() + " from team " + this.name);
        } else {
            ModLogger.warn("Failed to remove player " + player.getName() + " from team " + this.name + ": player not found in team");
        }
    }

    /**
     * Converts the list of players to a list of player names.
     *
     * @return ArrayList containing names of all players in the team
     */
    public ArrayList<String> getPlayersAsString() {
        if (playerList.isEmpty()) {
            ModLogger.debug("Converting empty player list to string list for team: " + this.name);
        }

        ArrayList<String> players = new ArrayList<>();
        // Convert each player object to player name
        for (ModPlayer player: this.playerList) {
            players.add(player.getName());
        }
        return players;
    }

    /**
     * Returns a string representation of the team. The string includes the team's
     * name, the current number of players, the maximum number of players allowed,
     * and the color associated with the team's name.
     *
     * @return a string representation of the team
     */
    @Override
    public String toString() {
        return nameColor + name + ChatFormatting.RESET +
                ChatFormatting.GRAY + " [" +
                playerList.size() + "/" + maxPlayers +
                "]" + ChatFormatting.RESET;
    }

    public ArrayList<ModPlayer> getPlayers() {
        if (playerList.isEmpty()) {
            ModLogger.debug("Returning empty player list for team: " + this.name);
        }
        return new ArrayList<>(playerList);
    }

    public String getName() {
        return name;
    }

    public ChatFormatting getColor(){
        return nameColor;
    }

    public boolean hasAvailableSpace() {
        return playerList.size() < maxPlayers;
    }

    public int getCurrentPlayers() {
        return playerList.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }


}