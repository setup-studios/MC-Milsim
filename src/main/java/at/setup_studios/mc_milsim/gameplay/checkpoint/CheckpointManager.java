package at.setup_studios.mc_milsim.gameplay.checkpoint;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class CheckpointManager {
    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    PlayerList list = server.getPlayerList();

    public Boolean checkPlayer (Checkpoint point, ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        if ((Math.sqrt(pos.getX()-point.getX())+Math.sqrt(pos.getZ()-point.getZ()))<=Math.sqrt(point.getRadius())) {
            return true;
        }
        return false;
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
}
