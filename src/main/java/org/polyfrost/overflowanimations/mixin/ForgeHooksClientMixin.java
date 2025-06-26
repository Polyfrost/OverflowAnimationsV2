package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public abstract class ForgeHooksClientMixin {
    @Inject(method = "handleCameraTransforms", at = @At("HEAD"))
    private static void overflowAnimations$getCameraPerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfoReturnable<IBakedModel> cir) {
        TransformTypeHook.transform = cameraTransformType;
    }
}
