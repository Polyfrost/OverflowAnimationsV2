package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerSlimeGel.class)
public abstract class LayerSlimeGelMixin {

    @Shadow @Final private ModelBase slimeModel;

    @Shadow @Final private RenderSlime slimeRenderer;

    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntitySlime;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER), cancellable = true)
    public void overflowAnimations$renderHitColor(EntitySlime var1, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            boolean bl = var1.hurtTime > 0 || var1.deathTime > 0;
            HitColorHook.renderHitColorPre(var1, bl, partialTicks, slimeRenderer);
            if (bl) {
                slimeModel.render(var1, f, g, h, i, j, scale);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

}
