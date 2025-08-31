package com.materialsoftherift.motr.blocks.stable;

import com.materialsoftherift.motr.init.MotrBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class StableAnvilBlock extends AnvilBlock {

    public StableAnvilBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(
            @NotNull BlockState state,
            @NotNull ServerLevel level,
            @NotNull BlockPos pos,
            @NotNull RandomSource random) {
    }

    @Nullable public static BlockState damage(BlockState pState) {
        Block block = pState.getBlock();
        if (block == MotrBlocks.STABLE_ANVIL.block().get()) {
            return MotrBlocks.STABLE_CHIPPED_ANVIL.block()
                    .get()
                    .defaultBlockState()
                    .setValue(FACING, pState.getValue(FACING));
        } else {
            if (block == MotrBlocks.STABLE_CHIPPED_ANVIL.block().get()) {
                return MotrBlocks.STABLE_DAMAGED_ANVIL.block()
                        .get()
                        .defaultBlockState()
                        .setValue(FACING, pState.getValue(FACING));
            } else {
                return null;
            }
        }
    }

}