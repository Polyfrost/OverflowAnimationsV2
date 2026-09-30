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

package org.polyfrost.overflowanimations.legacy.entity;

import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.model.ModelTransformations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;

public final class LegacyItemRendering {
    public static ModelTransformations.Type transform;
    public static boolean flat;

    private LegacyItemRendering() {
    }

    public static boolean isFlat(final BakedModel model) {
        if (model == null || model.isGui3d()) {
            return false;
        }

        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return (transform == ModelTransformations.Type.GROUND && items.itemDrops2D) || (transform == ModelTransformations.Type.FIXED && items.itemFramed2D);
    }

    public static boolean hideGlint(final BakedModel model) {
        if (!isFlat(model)) {
            return false;
        }

        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return transform == ModelTransformations.Type.GROUND ? !items.glintOnItemDrops2D : !items.glintOnItemFramed2D;
    }

    public static void applyTransform(final float x, final float y, final float z, final float rotationX, final float rotationY, final float rotationZ, final float scale) {
        if (x == 0.0F && y == 0.0F && z == 0.0F && rotationX == 0.0F && rotationY == 0.0F && rotationZ == 0.0F && scale == 0.0F) {
            return;
        }

        GlStateManager.translatef(x, y, z);
        GlStateManager.rotatef(rotationX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(rotationY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(rotationZ, 0.0F, 0.0F, 1.0F);
        final float factor = (float) Math.exp(scale);
        GlStateManager.scalef(factor, factor, factor);
    }
}
