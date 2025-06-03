package at.setup_studios.mc_milsim.gameplay.player;

import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player role
 * This class manages role-specific attributes, inventory, and armor loadouts.
 */
public class PlayerRole {
    // Core role attributes
    private final String name;
    private final String displayName;
    private final int maxPlayersPerTeam;  // Will be changed to max players per squad when squad system is implemented
    private final String description;
    
    // Storage for inventory and armor items
    private Map<Integer, ItemStack> inv = new HashMap<>();
    private List<ItemStack> armor = new ArrayList<>();

    /**
     * Creates a new PlayerRole with specified attributes.
     *
     * @param name The internal name of the role
     * @param displayName The displayed name of the role
     * @param maxPlayers Maximum number of players that can have this role per team
     * @param description A detailed description of the role
     * @throws IllegalArgumentException if any parameter is null or if maxPlayers is less than or equal to 0
     */
    public PlayerRole(String name, String displayName, int maxPlayers, String description) {
        if (name == null) {
            ModLogger.error("Name cannot be null");
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (displayName == null) {
            ModLogger.error("Name cannot be null");
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (description == null) {
            ModLogger.error("Description cannot be null");
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (maxPlayers <= 0) {
            ModLogger.error("Maximum players must be greater than 0");
            throw new IllegalArgumentException("Maximum players must be greater than 0");
        }
        this.name = name;
        this.maxPlayersPerTeam = maxPlayers;
        this.description = description;
        this.displayName = displayName;
        ModLogger.info("Created new role: " + name + " with max players: " + maxPlayers);
    }

    /**
     * Sets the inventory contents with a deep copy of the provided items.
     *
     * @param items Map of slot numbers to ItemStacks
     */
    public void setInventory(Map<Integer, ItemStack> items) {
        if (items == null) {
            ModLogger.error("Inventory items cannot be null");
            return;
        }
        
        // Create a new HashMap with deep copy of items
        this.inv = new HashMap<>();
        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            if (entry.getValue() != null) {
                this.inv.put(entry.getKey(), entry.getValue().copy());
            }
        }
    }

    /**
     * Sets the armor contents with a deep copy of the provided items.
     *
     * @param items List of armor ItemStacks in order: helmet, chestplate, leggings, boots
     */
    public void setArmor(List<ItemStack> items) {
        this.armor = new ArrayList<>();
        for (ItemStack item : items) {
            this.armor.add(item.copy());
        }
    }

    /**
     * Returns a deep copy of the inventory contents.
     *
     * @return Map of slot numbers to ItemStacks
     */
    public Map<Integer, ItemStack> getInventory() {
        Map<Integer, ItemStack> copy = new HashMap<>();
        for (Map.Entry<Integer, ItemStack> entry : inv.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    /**
     * Returns a deep copy of the armor contents.
     *
     * @return List of armor ItemStacks
     */
    public List<ItemStack> getArmor() {
        List<ItemStack> copy = new ArrayList<>();
        for (ItemStack item : armor) {
            copy.add(item.copy());
        }
        return copy;
    }

    /**
     * Returns a formatted string representation of the role.
     *
     * @return Formatted string with role information
     */
    @Override
    public String toString() {
        return "§6§l" + displayName + "§r\n" +
                "§7Role: §f" + name + "\n" +
                "§7Max Players: §f" + maxPlayersPerTeam + "\n" +
                "§7Description: §f" + description;
    }

    // Basic getters for role properties
    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public int getMaxPlayersPerTeam() { return maxPlayersPerTeam; }

    // Inventory management methods
    public ItemStack getItemAtSlot(Integer slot) { return this.inv.get(slot).copy(); }
    public boolean hasItemInSlot(Integer slot) { return inv.containsKey(slot); }
    public void addItem(Integer slot, ItemStack item) { this.inv.put(slot, item); }
    public void removeItem(Integer slot) { this.inv.remove(slot); }
    public int getInventorySize() { return inv.size(); }

    // Armor management methods
    public void setArmorSlot(int slot, ItemStack item) { this.armor.set(slot, item); }
    public ItemStack getHelmet() { return this.armor.get(0).copy(); }
    public ItemStack getChestplate() { return this.armor.get(1).copy(); }
    public ItemStack getLeggins() { return this.armor.get(2).copy(); }
    public ItemStack getBoots() { return this.armor.get(3).copy(); }
}