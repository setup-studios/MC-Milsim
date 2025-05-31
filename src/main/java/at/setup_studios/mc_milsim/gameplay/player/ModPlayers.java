package at.setup_studios.mc_milsim.gameplay.player;

import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

/**
 * Represents a player in the mod system with their associated data.
 * This class manages player information including their Minecraft player instance,
 * name, UUID, and team assignment.
 */
public class ModPlayers {
    // Core player data fields
    private Player player;    // Minecraft player instance
    private String name;      // Player's display name
    private UUID uuid;        // Unique identifier for the player
    private Teams team;       // Player's assigned team

    /**
     * Constructs a new ModPlayers instance with the specified parameters.
     *
     * @param uuid   The unique identifier for the player
     * @param name   The player's display name
     * @param player The Minecraft player instance
     */
    public ModPlayers(UUID uuid, String name, Player player) {
        if (uuid == null || name == null || player == null) {
            ModLogger.error("Attempted to create ModPlayers with null values - UUID: " + uuid + ", name: " + name + ", player: " + player);
            return;
        }
        this.name = name;
        this.uuid = uuid;
        this.team = null;    // Player starts with no team assigned
        this.player = player;
        ModLogger.debug("Created new ModPlayers instance for player: " + name + " (UUID: " + uuid + ")");
    }

    /**
     * Updates the player instance and synchronizes the player's name.
     * This is useful when player data needs to be updated after reconnection.
     *
     * @param player The new Minecraft player instance
     */
    public void setPlayer(Player player) {
        if (player == null) {
            ModLogger.error("Attempted to set null player for: " + this.name);
            return;
        }

        this.player = player;
        this.name = player.getName().getString();    // Update name from player instance
        ModLogger.debug("Updated player instance for: " + this.name);
    }

    /**
     * Assigns the player to a specific team.
     *
     * @param team The team to assign the player to
     */
    public void setTeam(Teams team) {
        this.team = team;
    }

    /**
     * Retrieves the player's unique identifier.
     *
     * @return The UUID of the player
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Retrieves the player's display name.
     *
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the player's current team assignment.
     *
     * @return The team the player is assigned to, or null if not assigned to any team
     */
    public Teams getTeam() {
        return team;
    }

    /**
     * Retrieves the Minecraft player instance.
     *
     * @return The current Minecraft player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the player is currently assigned to a team.
     *
     * @return true if the player is on a team, false otherwise
     */
    public boolean hasTeam() {
        return team != null;
    }

    @Override
    public String toString() {
        return ChatFormatting.AQUA + name +
                ChatFormatting.GRAY + " (UUID: " + ChatFormatting.YELLOW + uuid + ChatFormatting.GRAY + ")" +
                (team != null ? ChatFormatting.GRAY + " [" + team.toString() + ChatFormatting.GRAY + "]" : "") +
                ChatFormatting.RESET;
    }

}