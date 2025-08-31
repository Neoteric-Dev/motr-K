package com.materialsoftherift.motr.blocks.unbound;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnboundBambooBlock extends BambooStalkBlock {

    public UnboundBambooBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(
            @NotNull BlockState state,
            @NotNull ServerLevel level,
            @NotNull BlockPos pos,
            @NotNull RandomSource source) {
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState();
    }
}
