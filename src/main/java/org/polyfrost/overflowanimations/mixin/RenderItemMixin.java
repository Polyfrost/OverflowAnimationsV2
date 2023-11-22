package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class RenderItemMixin {

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = 8.0f))
    private float modifyScale(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ? 6.0f : original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = -50F))
    private float modifyEffect(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ?
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? -5.0f : 0.0f : original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = 10.0F))
    private float modifyEffect2(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ?
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? 205.0f : 0.0f : original;
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 1), index = 0)
    private float modifyEffect3(float original) {
        return (OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ? -1F : 1F) * original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(longValue = 4873L))
    private long modifyEffectSpeed3(long original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? 2000L : original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = 4873.0f))
    private float modifyEffectSpeed4(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? 2000.0f : original;
    }

    @Inject(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER))
    public void modifyEffect4(IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform == ItemCameraTransforms.TransformType.GUI) {
            GlStateManager.rotate(70.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        }
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(intValue = -8372020))
    private int modifyEffect6(int original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ?
                TransformTypeHook.transform == ItemCameraTransforms.TransformType.GUI ? -6788658 : -10799734 : original;
    }

    @Inject(method = "renderEffect", at = @At(value = "HEAD"), cancellable = true)
    public void modifyEffect5(IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.spritesGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform == ItemCameraTransforms.TransformType.GROUND) {
            ci.cancel();
        }
    }
}
