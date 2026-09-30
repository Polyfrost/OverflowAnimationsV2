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

package org.polyfrost.overflowanimations.legacy.model;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.MovementConfigCategory;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;

public final class LegacyEyeHeight {
    private static final float STANDING_EYE_HEIGHT = 1.62F;
    private static final float OLD_SNEAK_OFFSET = 0.08F;

    private static float lastEyeHeight = Float.NaN;
    private static float eyeHeight;
    private static Entity lastCamera;
    private static boolean lastSleeping;

    private LegacyEyeHeight() {
    }

    private static MovementConfigCategory config() {
        return OverflowAnimationsConfig.instance().movement;
    }

    private static boolean isSmooth() {
        final SneakAnimationSetting setting = config().sneakAnimation;
        return setting == SneakAnimationSetting.V1_7 || setting == SneakAnimationSetting.V1_13 || setting == SneakAnimationSetting.MODERN;
    }

    public static float target(final Entity entity) {
        if (config().modernSneakEyeHeight && entity instanceof PlayerEntity && entity.isSneaking() && !((PlayerEntity) entity).isSleeping() && !((PlayerEntity) entity).abilities.flying) {
            return 1.27F;
        }

        return entity.getEyeHeight();
    }

    public static void tick(final Entity camera) {
        final float target = target(camera);
        final boolean sleeping = camera instanceof PlayerEntity && ((PlayerEntity) camera).isSleeping();
        if (Float.isNaN(lastEyeHeight) || camera != lastCamera || sleeping != lastSleeping) {
            eyeHeight = target;
        }
        lastCamera = camera;
        lastSleeping = sleeping;

        lastEyeHeight = eyeHeight;
        final SneakAnimationSetting sneakAnimation = config().sneakAnimation;
        if (isSmooth() && config().longUnsneak && target > eyeHeight) {
            eyeHeight += (target - eyeHeight) * 0.6F;
        } else if (sneakAnimation == SneakAnimationSetting.V1_13 || sneakAnimation == SneakAnimationSetting.MODERN) {
            eyeHeight += (target - eyeHeight) * 0.5F;
        } else {
            eyeHeight = target;
        }
    }

    public static float get(final Entity camera, final float vanillaEyeHeight, final float tickDelta) {
        if (isSmooth()) {
            return lastEyeHeight + (eyeHeight - lastEyeHeight) * tickDelta;
        }

        return config().modernSneakEyeHeight ? target(camera) : vanillaEyeHeight;
    }

    public static boolean isOldSneakModel() {
        return config().sneakAnimation == SneakAnimationSetting.V1_7;
    }

    public static boolean isV1_13LocalSneak(final Entity entity) {
        return config().sneakAnimation == SneakAnimationSetting.V1_13 && entity == Minecraft.getInstance().player && entity.isSneaking();
    }

    public static float modelOffset(final Entity entity, final float tickDelta) {
        final Minecraft minecraft = Minecraft.getInstance();
        if (!isOldSneakModel() || entity != minecraft.player || minecraft.getCamera() != entity || minecraft.player.isSleeping()) {
            return 0.0F;
        }

        return MathHelper.clamp(STANDING_EYE_HEIGHT - get(entity, STANDING_EYE_HEIGHT, tickDelta), 0.0F, OLD_SNEAK_OFFSET);
    }
}
