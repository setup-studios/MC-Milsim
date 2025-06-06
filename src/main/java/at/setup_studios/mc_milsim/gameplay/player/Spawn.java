package at.setup_studios.mc_milsim.gameplay.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import at.setup_studios.mc_milsim.util.ModLogger;


public class Spawn {
    private static final Random RANDOM = new Random();
    /**
     * Spawns a player at the given location, clears inventory, and gives class items.
     *
     * @param modPlayer The custom player wrapper
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param z         The z coordinate
     */

    public static void spawnPlayerAt(ModPlayer modPlayer, double x, double y, double z) {
        ServerPlayer player = (ServerPlayer) modPlayer.getPlayer();
        /*TODO create Role class*/
        PlayerRole currentRole = modPlayer.getRole();

        // Getting a valid spawnPoint
        Vec3 safeSpawn = findSafeRandomSpawn(player.serverLevel(),new BlockPos((int)x, (int)y, (int)z),10,10);
        // Teleport player to the valid spawnPoint
        player.teleportTo(player.serverLevel(), safeSpawn.x, safeSpawn.y, safeSpawn.z, player.getYRot(), player.getXRot());
        player.getInventory().clearContent();
        // Get player role and therefore add the to the role belonging items to the inventory
        if (currentRole != null) {
            // Inventarplätze setzen
            Map<Integer, ItemStack> items = currentRole.getInventory();
            for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
                int slot = entry.getKey();
                ItemStack stack = entry.getValue();
                if (slot >= 0 && slot < player.getInventory().items.size()) {
                    player.getInventory().setItem(slot, stack.copy());
                }
            }

            // equip armor (index 0 = helmet ... 3 = boots)
            List<ItemStack> armor = currentRole.getArmor();
            if (armor != null && armor.size() == 4) {
                for (int i = 0; i < 4; i++) {
                    player.getInventory().armor.set(i, armor.get(i).copy());
                }
            }
        } else {
            ModLogger.error("Player {} has no role assigned! " + player.getName().getString());
        }
    }

    /**
     *
     * @param level         The ServerLevel the player is in
     * @param center        The root block
     * @param radius        The offset from the root block
     * @param maxAttempts   The amount of times the method checks for valid spawning spots to return, before returning the root spot
     * @return              Returns a Vec3 with coordinates of a valid spawn spot for the player
     */

    private static Vec3 findSafeRandomSpawn(ServerLevel level, BlockPos center, int radius, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int dx = RANDOM.nextInt(radius * 2 + 1) - radius;
            int dz = RANDOM.nextInt(radius * 2 + 1) - radius;
            BlockPos tryPos = center.offset(dx, 0, dz);

            // Get top Y at that x/z
            int topY = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, tryPos).getY();
            BlockPos groundPos = new BlockPos(tryPos.getX(), topY - 1, tryPos.getZ());

            // Safety checks
            if (isSafeSpawnLocation(level, groundPos)) {
                return new Vec3(groundPos.getX() + 0.5, groundPos.getY() + 1, groundPos.getZ() + 0.5);
            }
        }

        // Fallback: center position if none found
        return new Vec3(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);
    }

    /**
     *
     * @param level         The ServerLevel the player is in
     * @param groundPos     The ground position of the space that is to be checked
     * @return              Returns if a location is a valid spawn position for a player
     */

    private static boolean isSafeSpawnLocation(ServerLevel level, BlockPos groundPos) {
        BlockState ground = level.getBlockState(groundPos);
        BlockState above = level.getBlockState(groundPos.above());
        BlockState above2 = level.getBlockState(groundPos.above(2));
        return ground.isSolid() && (!ground.getFluidState().isSource()) && above.isAir() && above2.isAir();
    }
}