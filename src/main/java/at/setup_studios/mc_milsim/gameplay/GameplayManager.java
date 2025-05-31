package at.setup_studios.mc_milsim.gameplay;

import at.setup_studios.mc_milsim.gameplay.player.ModPlayers;
import at.setup_studios.mc_milsim.gameplay.player.Teams;
import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameplayManager {
    private static ArrayList<Teams> teamList = new ArrayList<>();
    private static HashMap<UUID, ModPlayers> playerList = new HashMap<>();

    static {
        teamList.add(new Teams("Red", 5, ChatFormatting.RED));
        teamList.add(new Teams("Blue", 5, ChatFormatting.BLUE));
    }

    public static ModPlayers getPlayerObject (ServerPlayer player) {
        if (player == null) return null;

        ModPlayers modPlayer = playerList.get(player.getUUID());
        if (modPlayer == null) {
            ModLogger.error("Player object not found: " + player.getName().getString());
        }
        return modPlayer;
    }

    public static void onPlayerJoin (ServerPlayer player) {
        UUID uuid=player.getUUID();
        ModPlayers modPlayer = new ModPlayers(uuid, player.getName().getString(), player);
        playerList.put(uuid, modPlayer);
        Teams team = teamWithLowestCount(teamList);
        team.addPlayer(modPlayer);
    }

    public static void onPlayerLeave (ServerPlayer player) {
        UUID uuid=player.getUUID();
        ModPlayers modPlayer = playerList.get(uuid);
        if (modPlayer == null) return;

        Teams team = modPlayer.getTeam();
        if (team == null) return;
        team.removePlayer(modPlayer);

        playerList.remove(uuid);
    }

    public static Teams teamWithLowestCount(ArrayList<Teams> teamList) {
        if (teamList.isEmpty()) {
            ModLogger.warn("teamList is empty");
            return null;
        }

        Teams teamWithLowestCount = teamList.get(0);
        for (Teams team: teamList) {
            if (team.getPlayers().size()<teamWithLowestCount.getPlayers().size()) {
                teamWithLowestCount = team;
            }
        }
        return teamWithLowestCount;
    }

    public static void addPlayerToTeam(ModPlayers player, Teams newTeam) {
        if (player == null || newTeam == null) ModLogger.error("Player and Team cannot be null");
        Teams oldPlayerTeam = player.getTeam();
        if (oldPlayerTeam != null) oldPlayerTeam.removePlayer(player);
        player.setTeam(newTeam);
        newTeam.addPlayer(player);
    }

    public static void removePlayerFromTeam(ModPlayers player) {
        Teams team = player.getTeam();
        if (team == null) {
            ModLogger.error("Player is not on a Team");
            return;
        }
        team.removePlayer(player);
        player.setTeam(null);
    }

    public static ArrayList<Teams> getTeamList() {
        return teamList;
    }

    public static void createTeam(String name, int maxPlayers, ChatFormatting color) {
        teamList.add(new Teams(name, maxPlayers, color));
    }
}
