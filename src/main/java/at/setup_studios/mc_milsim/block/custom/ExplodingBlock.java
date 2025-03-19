package at.setup_studios.mc_milsim.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ExplodingBlock extends Block {
    public ExplodingBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        explode(level, pos, state);
        super.stepOn(level, pos, state, entity);
    }

    private void explode(Level level, BlockPos pos, BlockState state) {
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 5F, Level.ExplosionInteraction.TNT);

    }
}
