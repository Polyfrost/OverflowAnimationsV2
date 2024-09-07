package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.layers.LayerSaddle;
import net.minecraft.entity.passive.EntityPig;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSaddle.class)
public abstract class LayerSaddleMixin {

    @Shadow @Final private ModelPig pigModel;

    @Shadow @Final private RenderPig pigRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/passive/EntityPig;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPig;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$renderHitColor(EntityPig var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            boolean bl = var1.hurtTime > 0 || var1.deathTime > 0;
            HitColorHook.renderHitColorPre(var1, bl, var4, pigRenderer);
            if (bl) {
                pigModel.render(var1, var2, var3, var5, var6, var7, var8);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

}
