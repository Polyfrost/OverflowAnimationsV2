package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.hooks.TransformTypeHook;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public class ForgeHooksClientMixin {

    @Inject(method = "handleCameraTransforms", at = @At("RETURN"))
    private static void getCameraPerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfoReturnable<IBakedModel> cir) {
        switch (cameraTransformType) {
            case NONE:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.NONE;
                break;
            case THIRD_PERSON:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.THIRD_PERSON;
                break;
            case FIRST_PERSON:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.FIRST_PERSON;
                break;
            case HEAD:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.HEAD;
                break;
            case GUI:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.GUI;
                break;
            case GROUND:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.GROUND;
                break;
            case FIXED:
                TransformTypeHook.transform = ItemCameraTransforms.TransformType.FIXED;
                break;
        }
    }
}