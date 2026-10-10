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

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.model.block.BakedQuad;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.tint.LegacyGlint;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class LegacyItemRendering {
    private static final Map<BakedModel, BakedModel> FLAT_MODELS = new WeakHashMap<>();
    private static final Map<BakedModel, BakedModel> FLAT_LIT_MODELS = new WeakHashMap<>();
    private static final Set<BakedModel> FLAT = Collections.newSetFromMap(new WeakHashMap<>());

    public static ModelTransformations.Type transform;
    public static boolean flat;

    private LegacyItemRendering() {
    }

    public static boolean isFlat(final BakedModel model) {
        if (!OverflowAnimations.isEnabled() || model == null || model.isGui3d()) {
            return false;
        }

        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return (transform == ModelTransformations.Type.GROUND && items.itemDrops2D) || (transform == ModelTransformations.Type.FIXED && items.itemFramed2D);
    }

    public static BakedModel flatModel(final BakedModel model) {
        final boolean lit = OverflowAnimationsConfig.instance().items.itemDrops2DColors;
        return (lit ? FLAT_LIT_MODELS : FLAT_MODELS).computeIfAbsent(model, key -> {
            final BakedModel flatModel = LegacyGlint.transform(key, quad -> quad.getFace() != Direction.SOUTH ? null : lit ? new BakedQuad(quad.getVertices(), quad.getTintIndex(), Direction.UP) : quad);
            FLAT.add(flatModel);
            return flatModel;
        });
    }

    public static boolean isFlatModel(final BakedModel model) {
        return FLAT.contains(model);
    }

    public static boolean hideGlint(final BakedModel model) {
        if (!isFlat(model)) {
            return false;
        }

        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return transform == ModelTransformations.Type.GROUND ? !items.glintOnItemDrops2D : !items.glintOnItemFramed2D;
    }

    public static String guiUseModel(final ItemStack item) {
        final LocalClientPlayerEntity player = Minecraft.getInstance().player;
        if (!OverflowAnimations.isEnabled() || OverflowAnimationsConfig.instance().items.usingTextureInGUI.isLegacy() || player == null) {
            return null;
        }

        if (item.getItem() == Items.FISHING_ROD && player.fishingBobber != null && item == player.getItemInHand()) {
            return "fishing_rod_cast";
        }
        if (item.getItem() == Items.BOW && item == player.getItemInUse()) {
            final int useTicks = item.getUseDuration() - player.getItemUseTimer();
            if (useTicks >= 18) {
                return "bow_pulling_2";
            } else if (useTicks > 13) {
                return "bow_pulling_1";
            } else if (useTicks > 0) {
                return "bow_pulling_0";
            }
        }
        return null;
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
