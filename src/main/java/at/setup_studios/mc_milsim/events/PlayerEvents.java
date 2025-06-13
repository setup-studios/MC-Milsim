package at.setup_studios.mc_milsim.events;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.checkpoint.CheckpointManager;
import at.setup_studios.mc_milsim.util.ModLogger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mc_milsim")
public class PlayerEvents {
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onPLayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) GameplayManager.onPlayerJoin(player);
    }

    @SubscribeEvent
    public static void onPLayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) GameplayManager.onPlayerLeave(player);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (++tickCounter >= 10) { // Every 10 ticks = 0,5 second
            tickCounter = 0;
            CheckpointManager.checkAll();
        }
    }
}