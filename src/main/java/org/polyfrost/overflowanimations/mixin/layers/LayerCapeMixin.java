package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.renderer.entity.layers.LayerCape;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerCape.class)
public class LayerCapeMixin {

    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    public void overflowAnimations$allowCombine(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(OldAnimationsSettings.damageCape && OldAnimationsSettings.INSTANCE.enabled);
    }

}
