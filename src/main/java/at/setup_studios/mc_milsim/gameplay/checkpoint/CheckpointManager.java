package at.setup_studios.mc_milsim.gameplay.checkpoint;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import at.setup_studios.mc_milsim.gameplay.player.Team;
import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;

public class CheckpointManager {
    private static final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    private static final PlayerList list = server.getPlayerList();
    private static final List<Checkpoint> checkpointList = new ArrayList<>();

    //Privat constructor since this is a utility class
    private CheckpointManager() {
        ModLogger.error("Utility class should not be instantiated");
    }

    public static Boolean checkPlayer(Checkpoint point, ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        if (pos.getY() > point.getMaxY()) return false;
        return (Math.sqrt(pos.getX() - point.getX()) + Math.sqrt(pos.getZ() - point.getZ())) <= Math.sqrt(point.getRadius());
    }

    public static List<ServerPlayer> inCheckPoint(Checkpoint point) {
        List<ServerPlayer> playerList = new ArrayList<>();
        for (ServerPlayer player : list.getPlayers()) {
            if (checkPlayer(point, player)) playerList.add(player);
        }
        return playerList;
    }

    public static List<ModPlayer> modPlayerInCheckPoint(Checkpoint point) {
        List<ServerPlayer> playerList = inCheckPoint(point);
        List<ModPlayer> modPlayerList = new ArrayList<>();
        for (ServerPlayer player : playerList) {
            modPlayerList.add(GameplayManager.getPlayerObject(player));
        }
        return modPlayerList;
    }

    public static void check(Checkpoint point) {
        HashMap<Team, Integer> playerCount = new HashMap<>();

        List<ModPlayer> modPlayerList = modPlayerInCheckPoint(point);
        for (ModPlayer player : modPlayerList) {
            Team t = player.getTeam();
            playerCount.put(t, playerCount.getOrDefault(t, 0) + 1);
        }
        List<Integer> sorted = playerCount.values().stream().sorted((i, j) -> j - i).toList();
        System.out.println(Arrays.toString(sorted.stream().mapToInt(i -> i).toArray()));
        if (sorted.size() >= 2) {
            int diff = sorted.get(0) - sorted.get(1);
            if (diff <= 0) return;
            Team team = getTeamWithValue(playerCount, sorted.get(0));
            switch (diff) {
                case 1, 2, 3, 4 -> point.addPoints(team, diff * 5);
                case 5 -> point.addPoints(team, 22);
                case 6 -> point.addPoints(team, 24);
                default -> point.addPoints(team, 26);
            }
        }
    }

    public static Team getTeamWithValue(HashMap<Team, Integer> playerCount, int value) {
        for (Map.Entry<Team, Integer> entry : playerCount.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void checkAll() {
        for (Checkpoint point : checkpointList) {
            check(point);
        }
    }

    public static boolean isOrderPositionTaken(int position) {
        return checkpointList.stream().anyMatch(cp -> cp.getOrderPos() == position);
    }

    public static void addCheckpoint (String name, int x, int z, int maxY, int radius, int maxPoints, int position) {
        if (isOrderPositionTaken(position)) {
            ModLogger.info("Position already taken, making space by moving");
            for (Checkpoint point : checkpointList) {
                if (point.getOrderPos() >= position) {
                    point.setOrderPos(point.getOrderPos()+1);
                }
            }
            ModLogger.info("Made space for new Checkpoint: "+ name);
        }
        checkpointList.add(new Checkpoint(name, x, z, maxY, radius, maxPoints, position));
        ModLogger.info("New Checkpoint made");
    }

    public static ArrayList<Checkpoint> getCheckpointList () {
        return new ArrayList<>(checkpointList);
    }

}