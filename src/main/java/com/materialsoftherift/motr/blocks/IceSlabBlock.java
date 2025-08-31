package com.materialsoftherift.motr.blocks;

import net.minecraft.world.level.block.SlabBlock;

public class IceSlabBlock extends SlabBlock {

    public IceSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getFriction() {
        return 0.98f;
    }

}