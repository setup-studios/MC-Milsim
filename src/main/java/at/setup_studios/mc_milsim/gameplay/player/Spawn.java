package at.setup_studios.mc_milsim.gameplay.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.Random;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class Spawn {

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
        /*TODO create Role class
        Role currentRole = modPlayer.getRole();
        */
        // Getting a valid spawnPoint
        Vec3 safeSpawn = findSafeRandomSpawn(player.serverLevel(),new BlockPos((int)x, (int)y, (int)z),10,10);
        // Teleport player to the valid spawnPoint
        player.teleportTo(player.serverLevel(), safeSpawn.x, safeSpawn.y, safeSpawn.z, player.getYRot(), player.getXRot());
        player.getInventory().clearContent();
        /* Get player role and therefore add the, to the role belonging items to the inventory
        if (currentRole != null) {
            for (ItemStack stack : currentRole.getInventoryItems()) {
                player.getInventory().add(stack.copy()); // Copy to avoid sharing item references
            }
       }
       */
    }

    /**
     *
     * @param level         The ServerLevel the player is in
     * @param center        The root block
     * @param radius        The offset from the root block
     * @param maxAttempts   The amount of times the method checks for valid spawning spots to return, before returning the root spot
     * @return              Returns a Vec3 with coordinates of a valid spawn spot for the player
     */

    public static Vec3 findSafeRandomSpawn(ServerLevel level, BlockPos center, int radius, int maxAttempts) {
        Random random = new Random();

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int dx = random.nextInt(radius * 2 + 1) - radius;
            int dz = random.nextInt(radius * 2 + 1) - radius;
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