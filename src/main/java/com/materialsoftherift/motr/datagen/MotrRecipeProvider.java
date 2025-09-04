package com.materialsoftherift.motr.datagen;

import com.materialsoftherift.motr.init.MotrBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/* Handles Data Generation for Recipes of the Wotr mod */
public class MotrRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    protected MotrRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> getter = this.registries.lookupOrThrow(Registries.ITEM);

        generateStableBlockRecipes(MotrBlocks.REGISTERED_STABLE_SANDS, getter);
        generateStableBlockRecipes(MotrBlocks.REGISTERED_STABLE_CONCRETE_POWDERS, getter);
        generateStableBlockRecipes(MotrBlocks.REGISTERED_STABLE_ANVILS, getter);

        MotrBlocks.REGISTERED_QUENCHED_BLOCKS.forEach((id, blockInfo) -> {
            ItemLike quenchedBlock = blockInfo.block().get();
            ItemLike vanillaBlock = blockInfo.getBaseItem();
            if (vanillaBlock == Items.AIR) return;

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, quenchedBlock, 1)
                    .requires(vanillaBlock)
                    .requires(Items.PRISMARINE_CRYSTALS)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "quenched_" + id + "_from_prismarine_crystals");

            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, quenchedBlock, 8)
                    .pattern("###")
                    .pattern("#W#")
                    .pattern("###")
                    .define('#', vanillaBlock)
                    .define('W', Items.WET_SPONGE)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "quenched_" + id + "_from_wet_sponge");

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, vanillaBlock, 1)
                    .requires(quenchedBlock)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, id + "_from_quenched");
        });

        MotrBlocks.REGISTERED_UNBOUND_BLOCKS.forEach((id, blockInfo) -> {
            ItemLike unboundBlock = blockInfo.block().get();
            ItemLike vanillaBlock = blockInfo.getBaseItem();

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, unboundBlock, 1)
                    .requires(vanillaBlock)
                    .requires(Items.HANGING_ROOTS)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "unbound_" + id + "_from_hanging_roots");

            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, unboundBlock, 8)
                    .pattern("###")
                    .pattern("#R#")
                    .pattern("###")
                    .define('#', vanillaBlock)
                    .define('R', Blocks.ROOTED_DIRT)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "unbound_" + id + "_from_rooted_dirt");

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, vanillaBlock, 1)
                    .requires(unboundBlock)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, id + "_from_unbound");
        });

        MotrBlocks.REGISTERED_STANDARD_SLABS.forEach((id, slabInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                .pattern("###")
                .define('#', slabInfo.getBaseItem())
                .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_GLASS_SLABS.forEach((id, slabInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                .pattern("###")
                .define('#', slabInfo.getBaseItem())
                .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_DIRECTIONAL_SLABS.forEach((id, slabInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                .pattern("###")
                .define('#', slabInfo.getBaseItem())
                .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_TRIMM_SLABS.forEach((id, slabInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, slabInfo.slab().get(), 6)
                .pattern("###")
                .define('#', slabInfo.getBaseItem())
                .unlockedBy("has_" + id, has(slabInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_STANDARD_WALLS.forEach((id, wallInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, wallInfo.wall().get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', wallInfo.getBaseItem())
                .unlockedBy("has_" + id, has(wallInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_GLASS_WALLS.forEach((id, wallInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, wallInfo.wall().get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', wallInfo.getBaseItem())
                .unlockedBy("has_" + id, has(wallInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_FENCES.forEach((id, fenceInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, fenceInfo.fence().get(), 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', fenceInfo.getBaseItem())
                .define('S', Items.STICK)
                .unlockedBy("has_" + id, has(fenceInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_BUTTONS.forEach((id, buttonInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, buttonInfo.button().get(), 1)
                .pattern("#")
                .define('#', buttonInfo.getBaseItem())
                .unlockedBy("has_" + id, has(buttonInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_FENCE_GATES.forEach((id, fenceGateInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, fenceGateInfo.fenceGate().get(), 3)
                .pattern("S#S")
                .pattern("S#S")
                .define('#', fenceGateInfo.getBaseItem())
                .define('S', Items.STICK)
                .unlockedBy("has_" + id, has(fenceGateInfo.getBaseItem()))
                .save(this.output));

        MotrBlocks.REGISTERED_STANDARD_STAIRS.forEach((id, stairInfo) -> ShapedRecipeBuilder
                .shaped(getter, RecipeCategory.BUILDING_BLOCKS, stairInfo.stair().get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', stairInfo.getBaseItem())
                .unlockedBy("has_" + id, has(stairInfo.getBaseItem()))
                .save(this.output));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, MotrBlocks.HAY_CARPET.get(), 4)
                .pattern("GG")
                .define('G', Items.HAY_BLOCK)
                .unlockedBy("has_hay_block", this.has(Items.HAY_BLOCK))
                .save(this.output);

    }

    private void generateStableBlockRecipes(Map<String, MotrBlocks.BlockInfo> blockMap, HolderGetter<Item> getter) {
        blockMap.forEach((id, blockInfo) -> {
            ItemLike stableBlock = blockInfo.block().get();
            ItemLike vanillaBlock = blockInfo.getBaseItem();

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, stableBlock, 1)
                    .requires(vanillaBlock)
                    .requires(Items.POPPED_CHORUS_FRUIT)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "stable_" + id + "_from_chorus_fruit");

            ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, stableBlock, 8)
                    .pattern("###")
                    .pattern("#C#")
                    .pattern("###")
                    .define('#', vanillaBlock)
                    .define('C', Items.CHORUS_FLOWER)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, "stable_" + id + "_from_chorus_flower");

            ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, vanillaBlock, 1)
                    .requires(stableBlock)
                    .unlockedBy("has_" + id, has(vanillaBlock))
                    .save(this.output, id + "_from_stable");
        });
    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(
                HolderLookup.@NotNull Provider provider,
                @NotNull RecipeOutput output) {
            return new MotrRecipeProvider(provider, output);
        }

        @Override
        public @NotNull String getName() {
            return "Materials of the Rift's Recipes";
        }
    }
}
