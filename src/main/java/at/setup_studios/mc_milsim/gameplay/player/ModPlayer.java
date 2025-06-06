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
public class ModPlayer {
    // Core player data fields
    private Player player;    // Minecraft player instance
    private String name;      // Player's display name
    private final UUID uuid;        // Unique identifier for the player
    private Team team;       // Player's assigned team
    private PlayerRole role; // Asign Player role


    /**
     * Constructs a new ModPlayers instance with the specified parameters.
     *
     * @param uuid   The unique identifier for the player
     * @param name   The player's display name
     * @param player The Minecraft player instance
     */
    public ModPlayer(UUID uuid, String name, Player player) {
        if (uuid == null) {
            ModLogger.error("UUID cannot be null");
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (name == null) {
            ModLogger.error("Name cannot be null");
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (player == null) {
            ModLogger.error("Player instance cannot be null");
            throw new IllegalArgumentException("Player instance cannot be null");
        }
        this.name = name;
        this.uuid = uuid;
        this.team = null;    // Player starts with no team assigned
        this.player = player;
        ModLogger.info("Created new ModPlayers instance for player: " + name + " (UUID: " + uuid + ")");
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
     * Returns a string representation of the player, including the player's name, UUID,
     * and associated team information, if applicable. Each attribute is formatted using
     * Minecraft's text formatting for better visual distinction in the output.
     *
     * @return a formatted string representation of the player's details
     */
    @Override
    public String toString() {
        return ChatFormatting.AQUA + name +
                ChatFormatting.GRAY + " (UUID: " + ChatFormatting.YELLOW + uuid + ChatFormatting.GRAY + ")" +
                (team != null ? ChatFormatting.GRAY + " [" + team.toString() + ChatFormatting.GRAY + "]" : "") +
                ChatFormatting.RESET;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    public Team getTeam() {
        return team;
    }
    public boolean hasTeam() {
        return team != null;
    }

    public UUID getUUID() {
        return uuid;
    }
    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }
    public PlayerRole getRole() { return this.role; }
    public void setRole(PlayerRole role) { this.role = role; }
  
}