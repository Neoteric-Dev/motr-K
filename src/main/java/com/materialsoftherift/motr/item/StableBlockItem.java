package com.materialsoftherift.motr.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StableBlockItem extends BlockItem {

    public StableBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack item, @NotNull TooltipContext tooltipContext,
                                @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("item.motr.ignores_gravity"));
        super.appendHoverText(item, tooltipContext, list, flag);
    }
}
