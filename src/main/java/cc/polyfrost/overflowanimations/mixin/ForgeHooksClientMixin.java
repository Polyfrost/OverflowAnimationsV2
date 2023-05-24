package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
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
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.NONE;
                break;
            case THIRD_PERSON:
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.THIRD_PERSON;
                break;
            case FIRST_PERSON:
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.FIRST_PERSON;
                break;
            case HEAD:
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.HEAD;
                break;
            case GUI:
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.GUI;
                break;
            case GROUND:
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.GROUND;
                break;
            case FIXED:
                OverflowAnimations.transform = ItemCameraTransforms.TransformType.FIXED;
                break;
        }
    }
}