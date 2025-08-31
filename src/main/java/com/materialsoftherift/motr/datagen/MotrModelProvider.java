package com.materialsoftherift.motr.datagen;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.init.MotrBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MotrModelProvider extends ModelProvider {

    public MotrModelProvider(PackOutput output) {
        super(output, MaterialsOfTheRift.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {

        blockModels.createTrivialBlock(MotrBlocks.HAY_CARPET.get(), TexturedModel.CARPET.updateTexture(
                mapping -> mapping.put(TextureSlot.WOOL, ResourceLocation.withDefaultNamespace("block/hay_block_top"))
        ));

        MotrBlocks.REGISTERED_STABLE_SANDS.forEach(
                (texture, blockInfo) -> simpleVanillaModelRedirect(blockModels, blockInfo.block().get(), texture));
        MotrBlocks.REGISTERED_STABLE_CONCRETE_POWDERS.forEach(
                (texture, blockInfo) -> simpleVanillaModelRedirect(blockModels, blockInfo.block().get(), texture));
        MotrBlocks.REGISTERED_STABLE_ANVILS.forEach(
                (texture, blockInfo) -> anvilVanillaModelRedirect(blockModels, blockInfo.block().get(), texture));

        MotrBlocks.REGISTERED_QUENCHED_BLOCKS.forEach(
                (texture, blockInfo) -> {
                    if (blockInfo.baseBlock() instanceof CoralFanBlock) {
                        TexturedModel texturedmodel = TexturedModel.CORAL_FAN.get(blockInfo.baseBlock());
                        ResourceLocation resourcelocation = texturedmodel.create(blockInfo.baseBlock(),
                                blockModels.modelOutput);
                        blockModels.blockStateOutput.accept(
                                BlockModelGenerators.createSimpleBlock(blockInfo.block().get(), resourcelocation));

                        ResourceLocation defaultModel = ModelLocationUtils
                                .getModelLocation(blockInfo.baseBlock().asItem());
                        itemModels.itemModelOutput.accept(blockInfo.block().get().asItem(),
                                ItemModelUtils.plainModel(defaultModel));
                        return;
                    }
                    if (blockInfo.baseBlock() instanceof SeaPickleBlock) {
                        ResourceLocation defaultModel = ModelLocationUtils.getModelLocation(blockInfo.baseBlock());
                        itemModels.itemModelOutput.accept(blockInfo.block().get().asItem(),
                                ItemModelUtils.plainModel(defaultModel));
                        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(blockInfo.block().get())
                                .with(PropertyDispatch.property(SeaPickleBlock.PICKLES)
                                        .select(1, List.of(BlockModelGenerators.createRotatedVariants(
                                                ModelLocationUtils.getModelLocation(Blocks.SEA_PICKLE))))
                                        .select(2,
                                                List.of(BlockModelGenerators.createRotatedVariants(ResourceLocation
                                                        .withDefaultNamespace("block/two_sea_pickles"))))
                                        .select(3,
                                                List.of(BlockModelGenerators.createRotatedVariants(ResourceLocation
                                                        .withDefaultNamespace("block/three_sea_pickles"))))
                                        .select(4,
                                                List.of(BlockModelGenerators.createRotatedVariants(ResourceLocation
                                                        .withDefaultNamespace("block/four_sea_pickles"))))
                                )
                        );
                        return;
                    }

                    ResourceLocation defaultModel = ModelLocationUtils.getModelLocation(blockInfo.baseBlock());
                    itemModels.itemModelOutput.accept(blockInfo.block().get().asItem(),
                            ItemModelUtils.plainModel(defaultModel));

                    ResourceLocation vanillaModel = ModelLocationUtils.getModelLocation(blockInfo.baseBlock());
                    blockModels.blockStateOutput
                            .accept(BlockModelGenerators.createSimpleBlock(blockInfo.block().get(), vanillaModel));
                });
        MotrBlocks.REGISTERED_UNBOUND_BLOCKS.forEach(
                (textureName, blockInfo) -> {
                    Block unboundBlock = blockInfo.block().get();
                    Block baseBlock = blockInfo.baseBlock();
                    Item unboundItem = unboundBlock.asItem();

                    if (baseBlock instanceof GlowLichenBlock) {
                        ResourceLocation defaultModel = ModelLocationUtils.getModelLocation(blockInfo.baseBlock());
                        itemModels.itemModelOutput.accept(blockInfo.block().get().asItem(),
                                ItemModelUtils.plainModel(defaultModel));

                        ResourceLocation vanillaModel = ModelLocationUtils.getModelLocation(blockInfo.baseBlock());
                        blockModels.blockStateOutput
                                .accept(BlockModelGenerators.createSimpleBlock(blockInfo.block().get(), vanillaModel));
                        return;
                    }

                    if (baseBlock instanceof CropBlock || baseBlock instanceof NetherWartBlock) {
                        Property<Integer> ageProperty;
                        if (baseBlock instanceof BeetrootBlock) {
                            ageProperty = BeetrootBlock.AGE;
                        } else {
                            ageProperty = baseBlock instanceof NetherWartBlock ? NetherWartBlock.AGE : CropBlock.AGE;
                        }
                        int[] visualStages;
                        String itemName;

                        if (baseBlock instanceof BeetrootBlock) {
                            visualStages = new int[] { 0, 1, 2, 3 };
                            itemName = "beetroot_seeds";
                        } else if (baseBlock == Blocks.WHEAT) {
                            visualStages = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
                            itemName = "wheat_seeds";
                        } else if (baseBlock == Blocks.CARROTS) {
                            visualStages = new int[] { 0, 0, 1, 1, 2, 2, 2, 3 };
                            itemName = "carrot";
                        } else if (baseBlock == Blocks.POTATOES) {
                            visualStages = new int[] { 0, 0, 1, 1, 2, 2, 2, 3 };
                            itemName = "potato";
                        } else if (baseBlock == Blocks.NETHER_WART) {
                            visualStages = new int[] { 0, 1, 1, 2 };
                            itemName = "nether_wart";
                        } else {
                            visualStages = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
                            itemName = unboundItem.toString();
                        }

                        Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
                        PropertyDispatch propertydispatch = PropertyDispatch.property(ageProperty).generate((age) -> {
                            int i = visualStages[age];
                            ResourceLocation resourcelocation = int2objectmap.computeIfAbsent(i,
                                    (p_387534_) -> blockModels.createSuffixedVariant(baseBlock, "_stage" + i,
                                            ModelTemplates.CROP, TextureMapping::crop));
                            return Variant.variant().with(VariantProperties.MODEL, resourcelocation);
                        });
                        blockModels.blockStateOutput
                                .accept(MultiVariantGenerator.multiVariant(unboundBlock).with(propertydispatch));

                        ResourceLocation itemModelLoc = ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(unboundItem),
                                TextureMapping.layer0(ResourceLocation.withDefaultNamespace("item/" + itemName)),
                                itemModels.modelOutput);
                        itemModels.itemModelOutput.accept(unboundItem, ItemModelUtils.plainModel(itemModelLoc));

                        return;
                    }

                    if (baseBlock instanceof SweetBerryBushBlock) {
                        ResourceLocation itemModelLoc = ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(unboundItem),
                                TextureMapping.layer0(ResourceLocation.withDefaultNamespace("item/sweet_berries")),
                                itemModels.modelOutput);
                        itemModels.itemModelOutput.accept(unboundItem, ItemModelUtils.plainModel(itemModelLoc));
                        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(unboundBlock)
                                .with(PropertyDispatch.property(SweetBerryBushBlock.AGE).generate(age -> {
                                    String stageSuffix = "_stage" + age;
                                    ResourceLocation texture = ResourceLocation
                                            .withDefaultNamespace("block/" + textureName + stageSuffix);
                                    ResourceLocation modelLoc = ModelTemplates.CROSS.createWithSuffix(unboundBlock,
                                            stageSuffix, TextureMapping.cross(texture), blockModels.modelOutput);
                                    return Variant.variant().with(VariantProperties.MODEL, modelLoc);
                                }))
                        );
                        return;
                    }

                    if (baseBlock instanceof DoublePlantBlock) {
                        String itemTexture;
                        if ("sunflower".equals(textureName)) {
                            itemTexture = "sunflower_front";
                        } else {
                            itemTexture = textureName + "_top";
                        }
                        ResourceLocation itemModelLoc = ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(unboundItem),
                                TextureMapping.layer0(ResourceLocation.withDefaultNamespace("block/" + itemTexture)),
                                itemModels.modelOutput);
                        itemModels.itemModelOutput.accept(unboundItem, ItemModelUtils.plainModel(itemModelLoc));

                        if (baseBlock == Blocks.SUNFLOWER) {
                            ResourceLocation top = ModelLocationUtils.getModelLocation(Blocks.SUNFLOWER, "_top");
                            ResourceLocation bottom = blockModels.createSuffixedVariant(Blocks.SUNFLOWER, "_bottom",
                                    BlockModelGenerators.PlantType.NOT_TINTED.getCross(), TextureMapping::cross);
                            blockModels.createDoubleBlock(unboundBlock, top, bottom);
                            return;
                        }
                        boolean isTinted = "tall_grass".equals(textureName) || "large_fern".equals(textureName);
                        BlockModelGenerators.PlantType plantType;
                        if (isTinted) {
                            plantType = BlockModelGenerators.PlantType.TINTED;
                        } else {
                            plantType = BlockModelGenerators.PlantType.NOT_TINTED;
                        }

                        ResourceLocation topModel = blockModels.createSuffixedVariant(unboundBlock, "_top",
                                plantType.getCross(), res -> TextureMapping
                                        .cross(ResourceLocation.withDefaultNamespace("block/" + textureName + "_top")));
                        ResourceLocation bottomModel = blockModels.createSuffixedVariant(unboundBlock, "_bottom",
                                plantType.getCross(), res -> TextureMapping.cross(
                                        ResourceLocation.withDefaultNamespace("block/" + textureName + "_bottom")));
                        blockModels.createDoubleBlock(unboundBlock, topModel, bottomModel);
                        return;
                    }

                    if (baseBlock instanceof FlowerBlock || baseBlock instanceof SaplingBlock
                            || baseBlock instanceof TallGrassBlock || baseBlock instanceof FungusBlock
                            || baseBlock instanceof MushroomBlock) {
                        boolean isTinted = baseBlock instanceof TallGrassBlock || "fern".equals(textureName);
                        BlockModelGenerators.PlantType plantType;
                        if (isTinted) {
                            plantType = BlockModelGenerators.PlantType.TINTED;
                        } else {
                            plantType = BlockModelGenerators.PlantType.NOT_TINTED;
                        }
                        ResourceLocation texture = ResourceLocation.withDefaultNamespace("block/" + textureName);

                        ResourceLocation model = plantType.getCross()
                                .create(unboundBlock, TextureMapping.cross(texture), blockModels.modelOutput);
                        blockModels.blockStateOutput
                                .accept(BlockModelGenerators.createSimpleBlock(unboundBlock, model));

                        ResourceLocation itemModelLocation = ModelLocationUtils.getModelLocation(unboundItem);
                        ModelTemplates.FLAT_ITEM.create(itemModelLocation, TextureMapping.layer0(texture),
                                blockModels.modelOutput);

                        if (isTinted) {
                            blockModels.registerSimpleTintedItemModel(unboundBlock, itemModelLocation,
                                    new GrassColorSource());
                        } else {
                            itemModels.itemModelOutput.accept(unboundItem,
                                    ItemModelUtils.plainModel(itemModelLocation));
                        }
                        return;
                    }

                    if (baseBlock instanceof PointedDripstoneBlock) {
                        PropertyDispatch.C2<Direction, DripstoneThickness> myDispatch = PropertyDispatch
                                .properties(PointedDripstoneBlock.TIP_DIRECTION, PointedDripstoneBlock.THICKNESS);
                        for (DripstoneThickness thickness : DripstoneThickness.values()) {
                            myDispatch.select(Direction.UP, thickness, createUnboundPointedDripstoneVariant(blockModels,
                                    unboundBlock, Direction.UP, thickness));
                        }
                        for (DripstoneThickness thickness : DripstoneThickness.values()) {
                            myDispatch.select(Direction.DOWN, thickness, createUnboundPointedDripstoneVariant(
                                    blockModels, unboundBlock, Direction.DOWN, thickness));
                        }
                        blockModels.blockStateOutput
                                .accept(MultiVariantGenerator.multiVariant(unboundBlock).with(myDispatch));
                        ResourceLocation dripStone = ModelLocationUtils.getModelLocation(Items.POINTED_DRIPSTONE);
                        itemModels.itemModelOutput.accept(unboundItem, ItemModelUtils.plainModel(dripStone));

                        return;
                    }

                    if (baseBlock instanceof CactusBlock) {
                        ResourceLocation vanillaCactusModel = ModelLocationUtils.getModelLocation(Blocks.CACTUS);
                        blockModels.blockStateOutput
                                .accept(BlockModelGenerators.createSimpleBlock(unboundBlock, vanillaCactusModel));
                        itemModels.itemModelOutput.accept(unboundItem, ItemModelUtils.plainModel(vanillaCactusModel));
                        return;
                    }

                    if (baseBlock instanceof BambooStalkBlock) {
                        createUnboundBambooModels(blockModels, itemModels, unboundBlock);
                        return;
                    }

                    simpleVanillaModelRedirect(blockModels, blockInfo.block().get(), textureName);

                });

        MotrBlocks.REGISTERED_STANDARD_SLABS.forEach(
                (textureName, slabInfo) -> registerStandardSlabModel(blockModels, slabInfo.slab().get(), textureName));

        MotrBlocks.REGISTERED_GLASS_SLABS.forEach(
                (textureId, slabInfo) -> registerGlassSlabModel(blockModels, slabInfo.slab().get(), textureId));

        MotrBlocks.REGISTERED_TRIMM_SLABS
                .forEach((id, slabInfo) -> registerTrimmSlabModel(blockModels, slabInfo.slab().get(), id, id, id));

        MotrBlocks.REGISTERED_DIRECTIONAL_SLABS.forEach((id, slabInfo) -> {
            {
                String side = id;
                String top = id;
                String bottom = id;
                switch (id) {
                    case "bone_block" -> {
                        side = "bone_block_side";
                        top = "bone_block_top";
                        bottom = "bone_block_top";
                    }
                    case "muddy_mangrove_roots" -> {
                        side = "muddy_mangrove_roots_side";
                        top = "muddy_mangrove_roots_top";
                        bottom = "muddy_mangrove_roots_top";
                    }
                    case "podzol", "mycelium", "dirt_path" -> {
                        side = id + "_side";
                        top = id + "_top";
                        bottom = "dirt";
                    }
                }
                registerDirectionalSlabModel(blockModels, slabInfo.slab().get(), side, top, bottom);
            }
        });

        MotrBlocks.REGISTERED_STANDARD_WALLS.forEach((textureName, wallInfo) -> {
            registerWallModel(blockModels, wallInfo.wall().get(), textureName);

            ResourceLocation itemModel = ModelTemplates.WALL_INVENTORY.create(
                    wallInfo.wall().get(),
                    new TextureMapping().put(TextureSlot.WALL,
                            ResourceLocation.withDefaultNamespace("block/" + textureName)),
                    itemModels.modelOutput
            );

            itemModels.itemModelOutput.accept(
                    wallInfo.wall().get().asItem(), ItemModelUtils.plainModel(itemModel)
            );

        });

        MotrBlocks.REGISTERED_GLASS_WALLS.forEach((textureName, wallInfo) -> {
            Block wall = wallInfo.wall().get();

            registerGlassWallModel(blockModels, wall, textureName);

            // Create translucent wall inventory model
            ResourceLocation itemModel = ExtendedModelTemplateBuilder.builder()
                    .parent(ResourceLocation.withDefaultNamespace("block/wall_inventory"))
                    .suffix("_inventory")
                    .requiredTextureSlot(TextureSlot.WALL)
                    .renderType("translucent")
                    .build()
                    .create(wall,
                            new TextureMapping().put(TextureSlot.WALL,
                                    ResourceLocation.withDefaultNamespace("block/" + textureName)),
                            blockModels.modelOutput);

            itemModels.itemModelOutput.accept(
                    wall.asItem(), ItemModelUtils.plainModel(itemModel)
            );
        });

        MotrBlocks.REGISTERED_BUTTONS.forEach((textureName, buttonInfo) -> registerButtonModel(blockModels, itemModels,
                buttonInfo.button().get(), textureName));

        MotrBlocks.REGISTERED_FENCES.forEach((textureName, fenceInfo) -> registerFenceModel(blockModels, itemModels,
                fenceInfo.fence().get(), textureName));

        MotrBlocks.REGISTERED_FENCE_GATES.forEach((textureName, fenceGateInfo) -> registerFenceGateModel(blockModels,
                itemModels, fenceGateInfo.fenceGate().get(), textureName));

        MotrBlocks.REGISTERED_STANDARD_STAIRS
                .forEach((textureName, stairInfo) -> registerStandardStairModel(blockModels, stairInfo.stair().get(),
                        textureName));
    }

    private void registerStandardSlabModel(BlockModelGenerators blockModels, Block slab, String textureName) {
        TextureMapping mapping = TextureMapping.cube(ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, blockModels.modelOutput);
        ResourceLocation top = ModelTemplates.SLAB_TOP.createWithSuffix(slab, "_top", mapping, blockModels.modelOutput);
        ResourceLocation cube = ModelTemplates.CUBE_ALL.createWithSuffix(slab, "_double", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private Variant createUnboundPointedDripstoneVariant(
            BlockModelGenerators blockModels,
            Block block,
            Direction direction,
            DripstoneThickness dripstoneThickness) {
        String suffix = "_" + direction.getSerializedName() + "_" + dripstoneThickness.getSerializedName();
        ResourceLocation texture = ResourceLocation.withDefaultNamespace("block/pointed_dripstone" + suffix);
        TextureMapping textureMapping = TextureMapping.cross(texture);
        ResourceLocation model = ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(block, suffix, textureMapping,
                blockModels.modelOutput);
        return Variant.variant().with(VariantProperties.MODEL, model);
    }

    public void createUnboundBambooModels(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block unboundBamboo) {
        blockModels.blockStateOutput.accept(
                MultiPartGenerator.multiPart(unboundBamboo)
                        .with(
                                Condition.condition().term(BlockStateProperties.AGE_1, 0),
                                blockModels.createBambooModels(0)
                        )
                        .with(
                                Condition.condition().term(BlockStateProperties.AGE_1, 1),
                                blockModels.createBambooModels(1)
                        )
                        .with(
                                Condition.condition().term(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.SMALL),
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "_small_leaves"))
                        )
                        .with(
                                Condition.condition().term(BlockStateProperties.BAMBOO_LEAVES, BambooLeaves.LARGE),
                                Variant.variant()
                                        .with(VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "_large_leaves"))
                        )
        );

        ResourceLocation texture = ResourceLocation.withDefaultNamespace("item/bamboo");
        ResourceLocation itemModelLocation = ModelLocationUtils.getModelLocation(unboundBamboo);
        ModelTemplates.FLAT_ITEM.create(itemModelLocation, TextureMapping.layer0(texture), blockModels.modelOutput);
    }

    private void registerStandardStairModel(BlockModelGenerators blockModels, Block stair, String textureName) {
        TextureMapping mapping = TextureMapping.cube(ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(stair, mapping, blockModels.modelOutput);
        ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(stair, mapping, blockModels.modelOutput);
        ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(stair, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(stair, inner, straight, outer));
    }

    private void simpleVanillaModelRedirect(BlockModelGenerators blockModels, Block motrBlock, String textureName) {
        TextureMapping mapping = TextureMapping.cube(ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation cube = ModelTemplates.CUBE_ALL.create(motrBlock, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(motrBlock, cube));
    }

    private void anvilVanillaModelRedirect(BlockModelGenerators blockModels, Block motrBlock, String textureName) {
        TextureMapping mapping = TextureMapping.cube(ResourceLocation.withDefaultNamespace("block/anvil"))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + textureName + "_top"));

        ResourceLocation anvil = ModelTemplates.ANVIL.create(motrBlock, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(motrBlock, anvil)
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    private void registerDirectionalSlabModel(
            BlockModelGenerators blockModels,
            Block slab,
            String sideTex,
            String topTex,
            String bottomTex) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + sideTex))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + topTex))
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/" + bottomTex));

        ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, blockModels.modelOutput);
        ResourceLocation top = ModelTemplates.SLAB_TOP.createWithSuffix(slab, "_top", mapping, blockModels.modelOutput);
        ResourceLocation cube = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(slab, "_double", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerGlassSlabModel(BlockModelGenerators blockModels, Block slab, String textureId) {
        ResourceLocation texture = ResourceLocation.withDefaultNamespace("block/" + textureId);

        TextureMapping slabMapping = new TextureMapping().put(TextureSlot.SIDE, texture)
                .put(TextureSlot.TOP, texture)
                .put(TextureSlot.BOTTOM, texture);

        TextureMapping cubeMapping = new TextureMapping().put(TextureSlot.ALL, texture);

        ResourceLocation bottom = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_bottom"))
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.SIDE)
                .renderType("translucent")
                .build()
                .create(slab, slabMapping, blockModels.modelOutput);

        ResourceLocation top = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_top"))
                .suffix("_top")
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.SIDE)
                .renderType("translucent")
                .build()
                .create(slab, slabMapping, blockModels.modelOutput);

        ResourceLocation cube = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cube_all"))
                .suffix("_double")
                .requiredTextureSlot(TextureSlot.ALL)
                .renderType("translucent")
                .build()
                .create(slab, cubeMapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerTrimmSlabModel(
            BlockModelGenerators blockModels,
            Block slab,
            String sideTex,
            String topTex,
            String bottomTex) {
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, ResourceLocation.withDefaultNamespace("block/" + sideTex))
                .put(TextureSlot.TOP, ResourceLocation.withDefaultNamespace("block/" + topTex))
                .put(TextureSlot.BOTTOM, ResourceLocation.withDefaultNamespace("block/" + bottomTex));

        ResourceLocation bottom = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_bottom"))
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .build()
                .create(slab, mapping, blockModels.modelOutput);

        ResourceLocation top = ExtendedModelTemplateBuilder.builder()
                .parent(MaterialsOfTheRift.id("block/texture_slab_top"))
                .suffix("_top")
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.TOP)
                .requiredTextureSlot(TextureSlot.BOTTOM)
                .build()
                .create(slab, mapping, blockModels.modelOutput);

        ResourceLocation cube = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(slab, "_double", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(slab)
                .with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE)
                        .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, cube))));
    }

    private void registerWallModel(BlockModelGenerators blockModels, Block wall, String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation post = ModelTemplates.WALL_POST.create(wall, mapping, blockModels.modelOutput);
        ResourceLocation lowSide = ModelTemplates.WALL_LOW_SIDE.create(wall, mapping, blockModels.modelOutput);
        ResourceLocation tallSide = ModelTemplates.WALL_TALL_SIDE.create(wall, mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createWall(wall, post, lowSide, tallSide)
        );

    }

    private void registerGlassWallModel(BlockModelGenerators blockModels, Block wall, String textureName) {
        ResourceLocation texture = ResourceLocation.withDefaultNamespace("block/" + textureName);
        TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL, texture);

        ResourceLocation post = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.withDefaultNamespace("block/template_wall_post"))
                .requiredTextureSlot(TextureSlot.WALL)
                .renderType("translucent")
                .build()
                .create(wall, mapping, blockModels.modelOutput);

        ResourceLocation lowSide = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.withDefaultNamespace("block/template_wall_side"))
                .requiredTextureSlot(TextureSlot.WALL)
                .renderType("translucent")
                .build()
                .createWithSuffix(wall, "_side", mapping, blockModels.modelOutput);

        ResourceLocation tallSide = ExtendedModelTemplateBuilder.builder()
                .parent(ResourceLocation.withDefaultNamespace("block/template_wall_side_tall"))
                .requiredTextureSlot(TextureSlot.WALL)
                .renderType("translucent")
                .build()
                .createWithSuffix(wall, "_side_tall", mapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createWall(wall, post, lowSide, tallSide)
        );
    }

    private void registerButtonModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block button,
            String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.createWithSuffix(button, "_pressed", mapping,
                blockModels.modelOutput);
        ResourceLocation unpressed = ModelTemplates.BUTTON.create(button, mapping, blockModels.modelOutput);
        ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.createWithSuffix(button, "_inventory", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createButton(button, unpressed, pressed)
        );

        itemModels.itemModelOutput.accept(
                button.asItem(), ItemModelUtils.plainModel(inventory)
        );
    }

    private void registerFenceModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block fence,
            String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation post = ModelTemplates.FENCE_POST.create(fence, mapping, blockModels.modelOutput);
        ResourceLocation side = ModelTemplates.FENCE_SIDE.create(fence, mapping, blockModels.modelOutput);
        ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.createWithSuffix(fence, "_inventory", mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFence(fence, post, side)
        );

        itemModels.itemModelOutput.accept(
                fence.asItem(), ItemModelUtils.plainModel(inventory)
        );
    }

    private void registerFenceGateModel(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            Block fenceGate,
            String textureName) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE,
                ResourceLocation.withDefaultNamespace("block/" + textureName));

        ResourceLocation closed = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, mapping, blockModels.modelOutput);
        ResourceLocation wall = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping,
                blockModels.modelOutput);
        ResourceLocation wallOpen = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, mapping,
                blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createFenceGate(fenceGate, open, closed, wallOpen, wall, true)
        );

        itemModels.itemModelOutput.accept(
                fenceGate.asItem(), ItemModelUtils.plainModel(closed)
        );
    }

}
