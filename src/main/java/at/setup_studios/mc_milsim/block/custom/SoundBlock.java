package at.setup_studios.mc_milsim.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SoundBlock extends Block {
    private SoundEvent sound = SoundEvents.NOTE_BLOCK_IMITATE_ENDER_DRAGON.get();
    public SoundBlock(Properties pProperties, SoundEvent music) {
        super(pProperties);
        sound = music;
    }

//    @Override
//   public void stepOn(Level pLevel, net.minecraft.core.BlockPos pPos, BlockState pState, net.minecraft.world.entity.Entity pEntity) {
//        super.stepOn(pLevel, pPos, pState, pEntity);
//        pLevel.rainLevel =  500;
//    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(pPlayer, pPos, sound, SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }
}
