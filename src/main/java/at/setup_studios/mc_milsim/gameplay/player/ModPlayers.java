package at.setup_studios.mc_milsim.gameplay.player;

import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ModPlayers {
    private Player player;
    private String name;
    private UUID uuid;
    private Teams team;

    public ModPlayers (UUID uuid, String name, Player player) {
        this.name = name;
        this.uuid = uuid;
        this.team = null;
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.name = player.getName().getString();
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Teams getTeam() {
        return team;
    }


}
