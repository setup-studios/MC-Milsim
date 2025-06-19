package at.setup_studios.mc_milsim.commands;

import at.setup_studios.mc_milsim.gameplay.checkpoint.CheckpointManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CreateCheckpointOnCordsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("milsim")
                .then(Commands.literal("checkpoint")
                        .then(Commands.literal("create")
                                .then(Commands.literal("oncords")
                                        .then(Commands.argument("checkpoint name", StringArgumentType.string())
                                                .then(Commands.argument("Order Position", IntegerArgumentType.integer(1))
                                                        .then(Commands.argument("max y height", IntegerArgumentType.integer())
                                                                .then(Commands.argument("x", IntegerArgumentType.integer())
                                                                        .then(Commands.argument("z", IntegerArgumentType.integer())
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
                        )
                )
        );
    }

    public static int executeCommand (CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        String name = StringArgumentType.getString(context, "checkpoint name");
        int maxY = IntegerArgumentType.getInteger(context, "max y height");
        int radius = IntegerArgumentType.getInteger(context,"radius");
        int orderPos = IntegerArgumentType.getInteger(context, "Order Position");
        int maxPoints = IntegerArgumentType.getInteger(context, "maxPoints");
        int x = IntegerArgumentType.getInteger(context, "x");
        int z = IntegerArgumentType.getInteger(context, "z");
        return CheckpointManager.addCheckpointWithMessage(source, name, maxY, radius, orderPos, maxPoints, x, z);
    }


}
