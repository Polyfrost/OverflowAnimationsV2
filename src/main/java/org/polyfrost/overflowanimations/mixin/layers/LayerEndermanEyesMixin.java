package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.entity.monster.EntityEnderman;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerEndermanEyes.class)
public abstract class LayerEndermanEyesMixin {

    @Shadow @Final private RenderEnderman endermanRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntityEnderman;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$renderHitColor(EntityEnderman entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            boolean bl = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;
            HitColorHook.renderHitColorPre(entitylivingbaseIn, bl, partialTicks, endermanRenderer);
            if (bl) {
                endermanRenderer.getMainModel().render(entitylivingbaseIn, f, g, h, i, j, scale);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

}
