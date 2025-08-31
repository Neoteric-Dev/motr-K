package com.materialsoftherift.motr.mixin;

import com.materialsoftherift.motr.blocks.stable.StableAnvilBlock;
import com.materialsoftherift.motr.init.MotrBlocks;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilMenuMixin {

    @Inject(method = "damage(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("HEAD"), cancellable = true)
    private static void damage(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (state.is(MotrBlocks.STABLE_ANVIL.block().get()) || state.is(MotrBlocks.STABLE_CHIPPED_ANVIL.block().get())
                || state.is(MotrBlocks.STABLE_DAMAGED_ANVIL.block().get())) {

            BlockState damagedState = StableAnvilBlock.damage(state);

            cir.setReturnValue(damagedState);
        }
    }

}