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

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.block.SnowLayerBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.model.ModelTransformation;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;

public final class ThirdPersonItemTransforms {
    private static final float BLOCK_TRANSLATION_Y = 1.5F / 16.0F;

    private ThirdPersonItemTransforms() {
    }

    public static void apply(final LivingEntity entity, final ItemStack stack) {
        final BakedModel model = Minecraft.getInstance().getItemRenderer().getModelShaper().getModel(stack);
        if (model.isCustomRenderer()) {
            return;
        }

        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        final Item item = stack.getItem();
        final boolean isBlock = model.isGui3d() && item instanceof BlockItem;
        final boolean swordBlocking = OverflowAnimationsConfig.instance().other.thirdPersonSwordBlockingPosition &&
                entity instanceof PlayerEntity && ((PlayerEntity) entity).getItemUseTimer() > 0 && stack.getUseAction() == UseAction.BLOCK;
        final ModelTransformation transformation = model.getTransformations().get(ModelTransformations.Type.THIRD_PERSON);
        final boolean legacy = items.itemPositionsInThirdPerson && (entity instanceof PlayerEntity || items.entityItemPositions) &&
                (!items.strictItemPositionsInThirdPerson || isBlock || item == Items.BOW || isHandheld(item)) &&
                transformation.scale.x != 0.0F && transformation.scale.y != 0.0F && transformation.scale.z != 0.0F;

        if (!legacy) {
            if (swordBlocking) {
                applySwordBlocking();
            }

            if (items.thinBlockPositions && isBlock && isThinBlock(Block.byItem(item)) && transformation.translation.y < BLOCK_TRANSLATION_Y) {
                GlStateManager.translatef(0.0F, 2.0F * (BLOCK_TRANSLATION_Y - transformation.translation.y), 0.0F);
            }

            return;
        }

        if (isBlock) {
            GlStateManager.translatef(0.0F, 0.1875F, -0.3125F);
            GlStateManager.rotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scalef(-0.375F, -0.375F, 0.375F);
        } else if (item == Items.BOW) {
            GlStateManager.translatef(0.0F, 0.125F, 0.3125F);
            GlStateManager.rotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scalef(0.625F, -0.625F, 0.625F);
            GlStateManager.rotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(45.0F, 0.0F, 1.0F, 0.0F);
        } else if (isHandheld(item)) {
            if (item.isFlippedWhenWielded()) {
                GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translatef(0.0F, -0.125F, 0.0F);
            }

            if (swordBlocking) {
                applySwordBlocking();
            }

            GlStateManager.translatef(0.0F, 0.1875F, 0.0F);
            GlStateManager.scalef(0.625F, -0.625F, 0.625F);
            GlStateManager.rotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(45.0F, 0.0F, 1.0F, 0.0F);
        } else {
            GlStateManager.translatef(0.25F, 0.1875F, -0.1875F);
            GlStateManager.scalef(0.375F, 0.375F, 0.375F);
            GlStateManager.rotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(20.0F, 0.0F, 0.0F, 1.0F);
        }

        if (isBlock) {
            GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
            final Block block = Block.byItem(item);
            if (block instanceof FurnaceBlock || block instanceof DispenserBlock || block instanceof PumpkinBlock) {
                GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.scalef(2.0F, 2.0F, 2.0F);
            undo(transformation);
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        } else {
            GlStateManager.translatef(0.0F, -0.3F, 0.0F);
            GlStateManager.scalef(1.5F, 1.5F, 1.5F);
            GlStateManager.rotatef(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translatef(-0.9375F, -0.0625F, 0.0F);
            GlStateManager.translatef(0.5F, 0.5F, -0.03125F);
            GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scalef(2.0F, 2.0F, 2.0F);
            undo(transformation);
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        }
    }

    private static void applySwordBlocking() {
        GlStateManager.translatef(0.05F, 0.0F, -0.1F);
        GlStateManager.rotatef(-50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(-10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(-60.0F, 0.0F, 0.0F, 1.0F);
    }

    private static void undo(final ModelTransformation transformation) {
        if (transformation == ModelTransformation.NONE) {
            return;
        }

        GlStateManager.scalef(1.0F / transformation.scale.x, 1.0F / transformation.scale.y, 1.0F / transformation.scale.z);
        GlStateManager.rotatef(-transformation.rotation.z, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotatef(-transformation.rotation.x, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(-transformation.rotation.y, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(-transformation.translation.x, -transformation.translation.y, -transformation.translation.z);
    }

    private static boolean isHandheld(final Item item) {
        return item.isWielded() && item != Items.BLAZE_ROD;
    }

    private static boolean isThinBlock(final Block block) {
        return block instanceof CarpetBlock || block instanceof TrapdoorBlock || block instanceof AbstractPressurePlateBlock ||
                block instanceof SnowLayerBlock || block instanceof DaylightDetectorBlock;
    }
}
