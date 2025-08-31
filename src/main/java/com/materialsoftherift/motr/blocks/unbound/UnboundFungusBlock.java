package com.materialsoftherift.motr.blocks.unbound;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class UnboundFungusBlock extends FungusBlock {

    public UnboundFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Block block,
            BlockBehaviour.Properties properties) {
        super(feature, block, properties);
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

    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return true;
    }

}
