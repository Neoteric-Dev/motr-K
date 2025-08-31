package com.materialsoftherift.motr.event;

import com.materialsoftherift.motr.MaterialsOfTheRift;
import com.materialsoftherift.motr.init.MotrBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = MaterialsOfTheRift.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MotrBlocks.REGISTERED_QUENCHED_BLOCKS
                .forEach((name, info) -> ItemBlockRenderTypes.setRenderLayer(info.block().get(), RenderType.cutout()));
        MotrBlocks.REGISTERED_UNBOUND_BLOCKS
                .forEach((name, info) -> ItemBlockRenderTypes.setRenderLayer(info.block().get(), RenderType.cutout()));
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();

        MotrBlocks.REGISTERED_UNBOUND_BLOCKS.forEach((name, info) -> {
//            if (info.baseBlock() instanceof CropBlock) {
//                event.register((state, getter, pos, tintIndex) ->
//                    blockColors.getColor(info.baseBlock().defaultBlockState()
//                        .setValue(CropBlock.AGE, state.getValue(CropBlock.AGE)), getter, pos, tintIndex), info.block().get());
//                return;
//            }

            event.register((state, getter, pos, tintIndex) -> blockColors.getColor(info.baseBlock().defaultBlockState(),
                    getter, pos, tintIndex), info.block().get());

//            event.register((state, getter, pos, tintIndex) -> -1, info.block().get());
        });

//        event.register((state, getter, pos, tintIndex) -> -1,
//                MotrBlocks.UNBOUND_WHEAT.block().get(),
//                MotrBlocks.UNBOUND_CARROTS.block().get(),
//                MotrBlocks.UNBOUND_POTATOES.block().get(),
//                MotrBlocks.UNBOUND_BEETROOTS.block().get()
//        );
    }

}