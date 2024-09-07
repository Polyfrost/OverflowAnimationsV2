package org.polyfrost.overflowanimations.mixin.interfaces;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RendererLivingEntity.class)
public interface RendererLivingEntityInvoker {

    @Invoker
    boolean invokeSetDoRenderBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks);

    @Invoker
    void invokeUnsetBrightness();

    @Invoker
    int invokeGetColorMultiplier(EntityLivingBase entitylivingbaseIn, float lightBrightness, float partialTickTime);

}
