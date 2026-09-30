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

package org.polyfrost.overflowanimations.mixins.legacy.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.FishingBobberRenderer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberRenderer.class)
public abstract class MixinFishingBobberRenderer_FishingLine {
    @WrapOperation(method = "render(Lnet/minecraft/entity/FishingBobberEntity;DDDFF)V", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d overflowanimations$fishingLinePosition(final double x, final double y, final double z, final Operation<Vec3d> original) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        double baseX = x, baseY = y, baseZ = z;
        if (items.fishingRodVersion == FishingRodVersionSetting.V1_13) {
            final double scale = Minecraft.getInstance().options.fov / 100.0;
            baseX = -0.36 * scale;
            baseY = -0.045 * scale;
            baseZ = 0.4;
        } else if (items.fishingRodVersion == FishingRodVersionSetting.MODERN) {
            final Minecraft minecraft = Minecraft.getInstance();
            final double fov = minecraft.options.fov;
            final double scale = 960.0 / fov;
            final double planeHeight = Math.tan(Math.toRadians(fov) / 2.0) * 0.05;
            final double planeWidth = planeHeight * minecraft.width / Math.max(minecraft.height, 1);
            baseX = -0.525 * planeWidth * scale;
            baseY = -0.1 * planeHeight * scale;
            baseZ = 0.05 * scale;
        } else if (items.fishingRodLineFov) {
            final double fov = Minecraft.getInstance().options.fov / 110.0;
            baseX = -fov + fov / 2.5 - fov / 8.0 + 0.16;
            baseY = 0.0;
            baseZ = 0.4;
        }

        if (items.customRodLine) {
            return original.call(baseX + items.rodLinePositionX, baseY + items.rodLinePositionY, baseZ + items.rodLinePositionZ);
        }

        return original.call(baseX, baseY, baseZ);
    }

    @Inject(method = "render(Lnet/minecraft/entity/FishingBobberEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/vertex/BufferBuilder;begin(ILnet/minecraft/client/render/vertex/VertexFormat;)V", ordinal = 1))
    private void overflowanimations$fishingLineThickness(final CallbackInfo ci, @Share("lineWidth") final LocalFloatRef previousWidth) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        final float width = items.thinFishingRodLineThickness ? 1.0F : items.fishingRodLineThickness;
        if (width > 0.0F) {
            previousWidth.set(GL11.glGetFloat(GL11.GL_LINE_WIDTH));
            GL11.glLineWidth(width);
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/FishingBobberEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/vertex/Tesselator;end()V", ordinal = 1, shift = At.Shift.AFTER))
    private void overflowanimations$restoreLineThickness(final CallbackInfo ci, @Share("lineWidth") final LocalFloatRef previousWidth) {
        if (previousWidth.get() > 0.0F) {
            GL11.glLineWidth(previousWidth.get());
        }
    }
}
