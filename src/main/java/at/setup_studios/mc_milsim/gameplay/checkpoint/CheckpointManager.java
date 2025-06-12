package at.setup_studios.mc_milsim.gameplay.checkpoint;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import at.setup_studios.mc_milsim.gameplay.player.Team;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;

public class CheckpointManager {
    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    PlayerList list = server.getPlayerList();

    public Boolean checkPlayer (Checkpoint point, ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        if (pos.getY()>point.getMaxY()) return false;
        return (Math.sqrt(pos.getX()-point.getX())+Math.sqrt(pos.getZ()-point.getZ()))<=Math.sqrt(point.getRadius());
    }

    public List<ServerPlayer> inCheckPoint (Checkpoint point) {
        List<ServerPlayer> playerList = new ArrayList<>();
        for (ServerPlayer player : list.getPlayers()) {
            if (checkPlayer(point, player)) playerList.add(player);
        }
        return playerList;
    }

    public List<ModPlayer> modPlayerInCheckPoint (Checkpoint point) {
        List<ServerPlayer> playerList = inCheckPoint(point);
        List<ModPlayer> modPlayerList  = new ArrayList<>();
        for (ServerPlayer player : playerList) {
            modPlayerList.add(GameplayManager.getPlayerObject(player));
        }
        return modPlayerList;
    }

    public void addPoints (Checkpoint point) {
        HashMap<Team, Integer> playerCount = new HashMap<>();

        List<ModPlayer> modPlayerList = modPlayerInCheckPoint(point);
        for (ModPlayer player: modPlayerList){
            Team t = player.getTeam();
            playerCount.put(t, playerCount.getOrDefault(t, 0) + 1);
        }
        List<Integer> sorted = playerCount.values().stream().sorted((i, j ) -> j - i).toList();
        System.out.println(Arrays.toString(sorted.stream().mapToInt( i -> i).toArray()));
        if (sorted.size() >= 2) {
            int diff = sorted.get(0) - sorted.get(1);
            if (diff <= 0) return;
            Team team = getTeamWithValue(playerCount, sorted.get(0));
            switch (diff) {
                //example numbers, can be changed later when balancing (not me)
                case 1: point.addPoints(team, 5);
                case 2: point.addPoints(team, 10);
                case 3: point.addPoints(team, 15);
                case 4: point.addPoints(team, 20);
                case 5: point.addPoints(team, 22);
                case 6: point.addPoints(team, 24);
                default: point.addPoints(team, 26);
            }
        }

    }

    public Team getTeamWithValue(HashMap<Team, Integer> playerCount, int value) {
        for (Map.Entry<Team, Integer> entry : playerCount.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }
}