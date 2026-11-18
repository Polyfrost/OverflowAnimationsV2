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

package org.polyfrost.overflowanimations.legacy.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.living.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.polyfrost.compose.render.PolyColor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class DamageTint {
    private static MethodHandle enabled;
    private static MethodHandle fade;
    private static MethodHandle fadeDeath;
    private static MethodHandle fadeDuration;
    private static MethodHandle colorForEntity;

    static {
        if (FabricLoader.getInstance().isModLoaded("damagetint")) {
            try {
                final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
                final Class<?> config = Class.forName("org.polyfrost.damagetint.client.DamageTintConfig");
                final Class<?> tracker = Class.forName("org.polyfrost.damagetint.client.utils.DamageVariantTracker");
                final Class<?> variant = Class.forName("org.polyfrost.damagetint.client.utils.DamageVariant");
                final MethodHandle colorFor = lookup.findStatic(config, "colorFor", MethodType.methodType(PolyColor.class, variant));
                final MethodHandle variantOf = lookup.findStatic(tracker, "get", MethodType.methodType(variant, LivingEntity.class));
                colorForEntity = MethodHandles.filterArguments(colorFor, 0, variantOf);
                enabled = lookup.findStaticGetter(config, "enabled", boolean.class);
                fade = lookup.findStaticGetter(config, "fade", boolean.class);
                fadeDeath = lookup.findStaticGetter(config, "fadeDeath", boolean.class);
                fadeDuration = lookup.findStaticGetter(config, "fadeDuration", float.class);
            } catch (final ReflectiveOperationException e) {
                LogManager.getLogger("OverflowAnimations/DamageTint").warn("DamageTint is loaded but its config could not be read, the 1.7 damage tint will ignore it", e);
                colorForEntity = null;
            }
        }
    }

    private DamageTint() {
    }

    public static boolean active() {
        try {
            return colorForEntity != null && (boolean) enabled.invokeExact();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static int argb(final LivingEntity entity) {
        try {
            final int argb = ((PolyColor) colorForEntity.invokeExact(entity)).getArgb();
            float alpha = (argb >>> 24) / 255.0F;
            if ((boolean) fade.invokeExact()) {
                final float duration = (float) fadeDuration.invokeExact();
                if (entity.deathTicks > 0 && (boolean) fadeDeath.invokeExact()) {
                    alpha *= Math.clamp(1.0F - entity.deathTicks / duration, 0.0F, 1.0F);
                } else if (entity.deathTicks == 0) {
                    alpha *= Math.clamp(entity.damagedTimer / duration, 0.0F, 1.0F);
                }
            }
            return (int) (alpha * 255.0F) << 24 | argb & 0xFFFFFF;
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
