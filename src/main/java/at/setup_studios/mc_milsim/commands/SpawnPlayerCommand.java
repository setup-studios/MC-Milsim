package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.GameplayManager;
import at.setup_studios.mc_milsim.gameplay.player.ModPlayer;
import at.setup_studios.mc_milsim.gameplay.player.Spawn;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

/**
 * Manages the player spawn command functionality for the military simulation mod.
 * Provides command handling for teleporting players to specific spawn locations.
 */
public class SpawnPlayerCommand {

    /**
     * Registers the spawn command with the Minecraft command system.
     * Sets up the command structure: /milsim player spawn [player]
     *
     * @param dispatcher The command dispatcher to register this command with
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Build command tree for spawning a player
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("player")
                        .then(Commands.literal("spawn")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> executeCommand(context))
                                )
                        )
                )
        );
    }
    
    /**
     * Handles the execution of the spawn command.
     *
     * @param context The command execution context containing the command source and arguments
     * @return 1 if the command executes successfully
     * @throws CommandSyntaxException If the player argument cannot be parsed
     */
    private static int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        // Get the command source
        CommandSourceStack source = context.getSource();
        // Get the target player from command arguments
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        // Convert to mod's player representation
        ModPlayer modPlayer = GameplayManager.getPlayerObject(player);

        // Teleport player to specific coordinates
        Spawn.spawnPlayerAt(modPlayer, -11, 68, 105);

        return 1; // Success
    }
}