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

package org.polyfrost.overflowanimations.legacy.firstperson;

import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.block.SnowLayerBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.effect.StatusEffect;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.mixins.legacy.firstperson.MixinLivingEntity_SwingAccessor;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;

public final class LegacyFirstPerson {
    private LegacyFirstPerson() {
    }

    public static ItemsConfigCategory items() {
        return OverflowAnimationsConfig.instance().items;
    }

    public static void fakeSwing(LivingEntity entity) {
        final int duration = ((MixinLivingEntity_SwingAccessor) entity).overflowanimations$getArmSwingDuration();
        if (!entity.armSwinging || entity.armSwingingTicks >= duration / 2 || entity.armSwingingTicks < 0) {
            entity.armSwingingTicks = -1;
            entity.armSwinging = true;
        }
    }

    public static boolean cannotMine(PlayerEntity player, Block block) {
        if (player.canModifyWorld()) {
            return false;
        }
        final ItemStack stack = player.getItemInHand();
        return block == null || stack == null || !stack.hasMineBlockOverride(block);
    }

    public static int swingDuration(LivingEntity entity, int original) {
        final ItemsConfigCategory items = items();
        if (!items.customSwingSpeed) {
            return original;
        }
        if (entity.hasStatusEffect(StatusEffect.HASTE) && !items.ignoreHasteSpeed) {
            return scaleSwingDuration(6 - (1 + entity.getEffectInstance(StatusEffect.HASTE).getAmplifier()), items.hasteSwingSpeed);
        } else if (entity.hasStatusEffect(StatusEffect.MINING_FATIGUE) && !items.ignoreMiningFatigueSpeed) {
            return scaleSwingDuration(6 + (1 + entity.getEffectInstance(StatusEffect.MINING_FATIGUE).getAmplifier()) * 2, items.miningFatigueSwingSpeed);
        } else {
            return scaleSwingDuration(6, items.itemSwingSpeed);
        }
    }

    private static int scaleSwingDuration(int duration, float speed) {
        return Math.max((int) (duration * Math.exp(-speed)), 1);
    }

    public static boolean isThinBlock(Block block) {
        return block instanceof CarpetBlock || block instanceof SnowLayerBlock || block instanceof DaylightDetectorBlock || block instanceof TrapdoorBlock;
    }

    public static boolean isBlockLike(BakedModel model) {
        return model.isGui3d() || model.isCustomRenderer();
    }

    public static boolean applyFirstPersonTransform(ItemStack stack, BakedModel model) {
        final ItemsConfigCategory items = items();
        final Item item = stack.getItem();
        final Block block = item instanceof BlockItem ? ((BlockItem) item).getBlock() : null;
        if (model.isCustomRenderer()) {
            if (items.itemPositions && !items.lunarItemPositions && (block instanceof ChestBlock || block instanceof EnderChestBlock)) {
                GlStateManager.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }
            return false;
        }

        if (model.isGui3d()) {
            if (block == null || items.lunarItemPositions) {
                return false;
            }
            if (items.itemPositions) {
                final boolean frontOnSouth = block instanceof FurnaceBlock || block instanceof DispenserBlock || block instanceof PumpkinBlock;
                GlStateManager.rotatef(frontOnSouth ? 270.0F : 90.0F, 0.0F, 1.0F, 0.0F);
            }
            if (items.thinBlockPositions && isThinBlock(block)) {
                if (block instanceof TrapdoorBlock) {
                    GlStateManager.translatef(0.0F, 6.5F / 32.0F, 0.0F);
                }
                return true;
            }
            return false;
        }

        if (items.lunarItemPositions) {
            return false;
        }
        if (item.isFlippedWhenWielded()) {
            if (items.fishingRodVersion != FishingRodVersionSetting.V1_7) {
                return false;
            }
            GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
        } else if (!items.itemPositions || (items.lunarBlockHitPosition && item instanceof SwordItem)) {
            return false;
        }
        applyLegacyItemTransform();
        return true;
    }

    private static void applyLegacyItemTransform() {
        GlStateManager.translatef(0.0F, -0.3F, 0.0F);
        GlStateManager.scalef(1.5F, 1.5F, 1.5F);
        GlStateManager.rotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translatef(-0.9375F, -0.0625F, 0.0F);
        GlStateManager.translatef(0.5F, 0.5F, 0.25F);
        GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(0.0F, 0.0F, 0.28125F);
        GlStateManager.scalef(2.0F, 2.0F, 2.0F);
    }
}
