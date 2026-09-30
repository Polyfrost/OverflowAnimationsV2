/**
 * OverflowAnimations
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * "MINECRAFT" LINKING EXCEPTION TO THE GPL
 */

package org.polyfrost.overflowanimations.mixins.legacy.tint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.layer.AbstractArmorLayer;
import net.minecraft.client.render.model.Model;
import net.minecraft.client.render.platform.GLX;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.resource.Identifier;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArmorLayer.class)
public abstract class MixinAbstractArmorLayer_ModernArmorGlint {
    @Shadow
    @Final
    protected static Identifier ENCHANTMENT_GLINT_TEXTURE;

    @Shadow
    @Final
    private LivingEntityRenderer<?> parent;

    @Inject(method = "renderEnchantmentGlint", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$renderModernGlint(final LivingEntity entity, final Model model, final float walkAnimationProgress, final float walkAnimationSpeed, final float tickDelta, final float bob, final float yaw, final float pitch, final float scale, final CallbackInfo ci) {
        if (!OverflowAnimationsConfig.instance().items.modernArmorGlint) {
            return;
        }

        ci.cancel();
        this.parent.bindTexture(ENCHANTMENT_GLINT_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GLX.multiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();
        final long time = Minecraft.getTime() * 8L;
        GlStateManager.translatef(-(time % 110000L) / 110000.0F, (time % 30000L) / 30000.0F, 0.0F);
        GlStateManager.rotatef(10.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scalef(0.16F, 0.16F, 0.16F);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        model.render(entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        final int light = entity.isOnFire() ? 15728880 : entity.getLightLevel(tickDelta);
        GLX.multiTexCoord2f(GLX.GL_TEXTURE1, light % 65536, light / 65536);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        GlStateManager.disableBlend();
    }
}
