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

package org.polyfrost.overflowanimations.legacy.tint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.OtherConfigCategory;
import org.polyfrost.overflowanimations.legacy.compat.Argentum;
import org.polyfrost.overflowanimations.util.enums.DamageTintSetting;

import java.nio.FloatBuffer;

public final class LegacyDamageTint {
    private LegacyDamageTint() {
    }

    public static OtherConfigCategory config() {
        return OverflowAnimationsConfig.instance().other;
    }

    public static boolean isHurt(final Entity entity) {
        return entity instanceof LivingEntity living && (living.damagedTimer > 0 || living.deathTicks > 0);
    }

    public static boolean flatTint() {
        return OverflowAnimations.isEnabled() && config().damageTintStyle == DamageTintSetting.V1_7 && !Argentum.capturingEntity();
    }

    public static boolean usesFlatPass(final Entity entity) {
        return flatTint() && isHurt(entity);
    }

    public static void putCombinerTint(final FloatBuffer buffer, final float brightness) {
        final DamageTintSetting style = config().damageTintStyle;
        if (!OverflowAnimations.isEnabled() || style == DamageTintSetting.VANILLA) {
            return;
        }

        final int color = style.getColor(style == DamageTintSetting.V1_7 ? 1.0F : brightness);
        buffer.clear();
        buffer.put((color >> 16 & 0xFF) / 255.0F).put((color >> 8 & 0xFF) / 255.0F).put((color & 0xFF) / 255.0F).put(1.0F - (color >>> 24) / 255.0F);
    }

    public static boolean beginFlatPass(final Entity entity, final float tickDelta) {
        if (!usesFlatPass(entity)) {
            return false;
        }

        final int color = DamageTintSetting.V1_7.getColor(entity.getBrightness(tickDelta));
        Minecraft.getInstance().gameRenderer.disableLightMap();
        GlStateManager.disableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.color4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F - (color >>> 24) / 255.0F);
        return true;
    }

    public static void endFlatPass() {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
        Minecraft.getInstance().gameRenderer.enableLightMap();
    }
}
