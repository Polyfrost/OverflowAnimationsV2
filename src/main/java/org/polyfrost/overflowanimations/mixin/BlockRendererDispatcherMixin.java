package org.polyfrost.overflowanimations.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.polyfrost.overflowanimations.init.CustomModelBakery;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRendererDispatcher.class)
public class BlockRendererDispatcherMixin {

    @Inject(
            method = "getModelFromBlockState",
            at = @At(
                    value = "RETURN"
            ),
            cancellable = true
    )
    private void mixcesAnimations$customBlockModel(IBlockState state, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<IBakedModel> cir) {
        if (state == null) return;
//        if (!MixcesAnimationsConfig.INSTANCE.getFastGrass() || !MixcesAnimationsConfig.INSTANCE.enabled) return;
        if (state.getBlock() == Blocks.grass) {
            cir.setReturnValue(CustomModelBakery.LAYERLESS_GRASS.getBakedModel());
        }
    }

}