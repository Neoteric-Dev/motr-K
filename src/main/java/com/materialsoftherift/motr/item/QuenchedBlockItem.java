package com.materialsoftherift.motr.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuenchedBlockItem extends GlowingBlockItem {

    public QuenchedBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack item,
            @NotNull TooltipContext tooltipContext,
            @NotNull List<Component> list,
            @NotNull TooltipFlag flag) {
        list.add(Component.translatable("item.motr.does_not_require_water"));
        super.appendHoverText(item, tooltipContext, list, flag);
    }

}