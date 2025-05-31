package at.setup_studios.mc_milsim.gameplay.player;

import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.ChatFormatting;
import java.util.ArrayList;

public class Teams {
    private String name;
    private int maxPlayers;
    private ChatFormatting nameColor;
    private ArrayList<ModPlayers> playerList;

    public Teams(String teamName, int maxPlayers, ChatFormatting color) {
        this.name = teamName;
        this.maxPlayers = maxPlayers;
        this.nameColor = color;
        this.playerList  = new ArrayList<>();
    }

    public void addPlayer(ModPlayers player) {
        if (player == null) {
            ModLogger.error("Player cant be null");
            return;
        }
        if (playerList.size() < maxPlayers) {
            playerList.add(player);
            player.setTeam(this);
        }
        else {
            ModLogger.info("Team: " + this.name + " is full");
        }
    }
    public void removePlayer(ModPlayers player) {
        if (player == null) {
            ModLogger.warn("Player cant be null");
            return;
        }
        this.playerList.remove(player);
    }
    public ArrayList<ModPlayers> getPlayers() {
        return playerList;
    }
    public ArrayList<String> getPlayersAsString() {
        ArrayList<String> players = new ArrayList<>();
        for (ModPlayers player: this.playerList) {
            players.add(player.getName());
        }
        return players;
    }
    public String getName() {
        return name;
    }
    public ChatFormatting getColor(){
        return nameColor;
    }

}


