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

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.layer.AbstractArmorLayer;
import net.minecraft.client.render.model.Model;
import net.minecraft.client.render.platform.GLX;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.entity.living.LivingEntity;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.tint.LegacyGlint;
import org.polyfrost.overflowanimations.util.enums.ArmorGlintSetting;
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
    private LivingEntityRenderer<?> parent;

    @Inject(method = "renderEnchantmentGlint", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$renderModernGlint(final LivingEntity entity, final Model model, final float walkAnimationProgress, final float walkAnimationSpeed, final float tickDelta, final float bob, final float yaw, final float pitch, final float scale, final CallbackInfo ci) {
        final ArmorGlintSetting setting = OverflowAnimationsConfig.instance().items.armorGlint;
        if (setting != ArmorGlintSetting.V1_15 && setting != ArmorGlintSetting.MODERN) {
            return;
        }

        final boolean modern = setting == ArmorGlintSetting.MODERN;
        final float strength = modern ? LegacyGlint.modernStrength() : 1.0F;
        ci.cancel();
        this.parent.bindTexture(modern ? LegacyGlint.MODERN_ARMOR_TEXTURE : LegacyGlint.MODERN_ITEM_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.depthMask(false);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        GlStateManager.color4f(strength, strength, strength, 1.0F);
        GLX.multiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.loadIdentity();
        LegacyGlint.setupModernTexturing(0.16F, modern ? LegacyGlint.modernSpeed() : 1.0);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        model.render(entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
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
