package org.polyfrost.overflowanimations.mixin;

import me.mixces.animations.hook.GlintModelHook;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
        value = ForgeHooksClient.class,
        remap = false
)
public class ForgeHooksClientMixin {

    @Inject(
            method = "handleCameraTransforms",
            at = @At(
                    value = "RETURN"
            )
    )
    private static void overflowAnimations$captureCameraPerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfoReturnable<IBakedModel> cir) {
        TransformTypeHook.INSTANCE.setTransform(cameraTransformType);
        GlintModelHook.INSTANCE.setTransformType(cameraTransformType);
    }

}
