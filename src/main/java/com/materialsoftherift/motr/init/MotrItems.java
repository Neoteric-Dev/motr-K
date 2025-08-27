package com.materialsoftherift.motr.init;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.item.StableBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class MotrItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MaterialsOfTheRift.MODID);
    public static final List<DeferredItem<BlockItem>> BLOCK_ITEMS = new ArrayList<>();
    public static final List<DeferredItem<BlockItem>> DEV_BLOCK_ITEMS = new ArrayList<>();

    public static <T extends Block> DeferredItem<BlockItem> registerSimpleBlockItem(String id, DeferredBlock<T> block) {
        DeferredItem<BlockItem> simpleBlockItem = ITEMS.registerSimpleBlockItem(id, block);
        BLOCK_ITEMS.add(simpleBlockItem);
        return simpleBlockItem;
    }

    public static <T extends Block> DeferredItem<BlockItem> registerSimpleDevBlockItem(
            String id,
            DeferredBlock<T> block) {
        DeferredItem<BlockItem> simpleBlockItem = ITEMS.registerSimpleBlockItem(id, block);
        DEV_BLOCK_ITEMS.add(simpleBlockItem);
        return simpleBlockItem;
    }

    public static DeferredItem<BlockItem> registerStableBlockItem(String id, DeferredBlock<Block> block) {
        DeferredItem<BlockItem> stableBlockItem = ITEMS.register(id, () -> new StableBlockItem(block.get(), new Item.Properties()));
        BLOCK_ITEMS.add(stableBlockItem);
        return stableBlockItem;
    }

}
