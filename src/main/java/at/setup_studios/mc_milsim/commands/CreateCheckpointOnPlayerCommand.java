package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.checkpoint.CheckpointManager;
import at.setup_studios.mc_milsim.util.ModLogger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CreateCheckpointOnPlayerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("checkpoint")
                        .then(Commands.literal("create")
                                .then(Commands.literal("onplayer")
                                        .then(Commands.argument("checkpoint name", StringArgumentType.string())
                                                .then(Commands.argument("Order Position", IntegerArgumentType.integer(1))
                                                        .then(Commands.argument("max y height", IntegerArgumentType.integer())
                                                                .then(Commands.argument("radius", IntegerArgumentType.integer(1))
                                                                        .then(Commands.argument("maxPoints", IntegerArgumentType.integer(1))
                                                                                .executes(context -> executeCommand(context))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static int executeCommand(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String name = StringArgumentType.getString(context, "checkpoint name");
        int maxY = IntegerArgumentType.getInteger(context, "max y height");
        int radius = IntegerArgumentType.getInteger(context,"radius");
        int orderPos = IntegerArgumentType.getInteger(context, "Order Position");
        int maxPoints = IntegerArgumentType.getInteger(context, "maxPoints");
        try {
            ServerPlayer player = source.getPlayerOrException();
            BlockPos pos = player.blockPosition();
            int pX = pos.getX();
            int pZ = pos.getZ();
            return CheckpointManager.addCheckpointWithMessage(source, name, maxY, radius, orderPos, maxPoints, pX, pZ);
        } catch (CommandSyntaxException e) {
            ModLogger.error("Command has to be executed either with Coordinates or by a Player!");
            source.sendFailure(Component.literal("Command has to be executed either with Coordinates or by a Player!"));
            return 0;
        }
    }
}
