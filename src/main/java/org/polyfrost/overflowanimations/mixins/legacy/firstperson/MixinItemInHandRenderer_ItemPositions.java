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

package org.polyfrost.overflowanimations.mixins.legacy.firstperson;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.SnowLayerBlock;
import net.minecraft.client.render.ItemInHandRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CarrotOnAStickItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SwordItem;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.firstperson.LegacyFirstPerson;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_ItemPositions {
    @Unique
    private static final float overflowanimations$OFFSET_MULTIPLIER = 0.05F / 0.4F;

    @Unique
    private static final FloatBuffer overflowanimations$MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);

    @Shadow
    private ItemStack itemInHand;

    @Shadow
    @Final
    private ItemRenderer renderer;

    @Unique
    private boolean overflowanimations$customizesItem() {
        return OverflowAnimations.isEnabled() && this.itemInHand != null && (LegacyFirstPerson.items().applyCustomizationToBlockItems || !LegacyFirstPerson.isBlockLike(this.renderer.getModelShaper().getModel(this.itemInHand)));
    }

    @Unique
    private static void overflowanimations$applyOffsetAndScale(final ItemsConfigCategory items) {
        GlStateManager.translatef(items.itemOffsetX * overflowanimations$OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$OFFSET_MULTIPLIER);
        GlStateManager.scalef(items.itemScaleX, items.itemScaleY, items.itemScaleZ);
    }

    @Unique
    private static Matrix4f overflowanimations$readModelView() {
        overflowanimations$MATRIX_BUFFER.clear();
        GlStateManager.getFloat(GL11.GL_MODELVIEW_MATRIX, overflowanimations$MATRIX_BUFFER);
        return (Matrix4f) new Matrix4f().load(overflowanimations$MATRIX_BUFFER);
    }

    // Offsets and scales around the item's resting position before any animation runs, so the swing, block-hit and eating motion shrinks and moves with the item.
    @Inject(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER))
    private void overflowanimations$scaleAnimationsWithItem(final float tickDelta, final CallbackInfo ci) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (!items.scaleAnimationsWithItem || !this.overflowanimations$customizesItem() || this.itemInHand.getItem() == Items.FILLED_MAP) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        if (items.lunarItemPositions) {
            this.overflowanimations$applyLunarItemPosition();
        }
        GlStateManager.translatef(0.56F, -0.52F, -0.71999997F);
        GlStateManager.rotatef(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scalef(0.4F, 0.4F, 0.4F);
        final Matrix4f rest = overflowanimations$readModelView();
        overflowanimations$applyOffsetAndScale(items);
        final Matrix4f customized = overflowanimations$readModelView();
        GlStateManager.popMatrix();
        Matrix4f.mul(customized, Matrix4f.invert(rest, rest), customized);
        overflowanimations$MATRIX_BUFFER.clear();
        customized.store(overflowanimations$MATRIX_BUFFER);
        overflowanimations$MATRIX_BUFFER.flip();
        GlStateManager.multMatrix(overflowanimations$MATRIX_BUFFER);
    }

    @Inject(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;renderHand(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FF)V"))
    private void overflowanimations$scaleHand(final float tickDelta, final CallbackInfo ci) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (!OverflowAnimations.isEnabled() || !items.scaleHandWithItem) {
            return;
        }
        GlStateManager.translatef(0.56F, -0.52F, -0.71999997F);
        GlStateManager.rotatef(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scalef(0.4F, 0.4F, 0.4F);
        overflowanimations$applyOffsetAndScale(items);
        GlStateManager.scalef(2.5F, 2.5F, 2.5F);
        GlStateManager.rotatef(-45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(-0.56F, 0.52F, 0.71999997F);
    }

    @Inject(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resource/model/ModelTransformations$Type;)V"))
    private void overflowanimations$itemCustomization(final float tickDelta, final CallbackInfo ci) {
        if (!this.overflowanimations$customizesItem()) {
            return;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (!items.scaleAnimationsWithItem) {
            overflowanimations$applyOffsetAndScale(items);
        }
        GlStateManager.rotatef(items.itemRotationX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(items.itemRotationY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(items.itemRotationZ, 0.0F, 0.0F, 1.0F);
    }

    @Inject(method = "applyFirstPersonTransform", at = @At("HEAD"))
    private void overflowanimations$lunarItemPositions(final float equipProgress, final float swingProgress, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && LegacyFirstPerson.items().lunarItemPositions && this.itemInHand != null) {
            this.overflowanimations$applyLunarItemPosition();
        }
    }

    @Unique
    private void overflowanimations$applyLunarItemPosition() {
        final Item item = this.itemInHand.getItem();
        if (item instanceof SwordItem) {
            GlStateManager.translatef(0.0F, 0.0F, -0.02F);
            GlStateManager.rotatef(1.0F, 0.0F, 0.0F, -0.1F);
        } else if (item instanceof PotionItem) {
            GlStateManager.translatef(-0.0225F, -0.02F, 0.0F);
            GlStateManager.rotatef(1.0F, 0.0F, 0.0F, 0.1F);
        } else if (item instanceof FishingRodItem || item instanceof CarrotOnAStickItem) {
            GlStateManager.translatef(0.08F, -0.0275F, -0.33F);
            GlStateManager.scalef(0.95F, 1.0F, 1.0F);
        } else if (item instanceof BowItem) {
            GlStateManager.rotatef(0.9F, 0.0F, 0.001F, 0.0F);
        } else if (item instanceof BlockItem) {
            final Block block = ((BlockItem) item).getBlock();
            if (block instanceof CarpetBlock || block instanceof SnowLayerBlock) {
                GlStateManager.translatef(0.0F, -0.25F, 0.0F);
            }
        }
    }

    @Inject(method = "applyFirstPersonTransform", at = @At("TAIL"))
    private void overflowanimations$swapPosition(final float equipProgress, final float swingProgress, final CallbackInfo ci) {
        if (equipProgress != 0.0F && OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = LegacyFirstPerson.items();
            GlStateManager.translatef(equipProgress * items.swapPositionX, equipProgress * items.swapPositionY, equipProgress * items.swapPositionZ);
        }
    }

    @Inject(method = "applyConsuming", at = @At("HEAD"))
    private void overflowanimations$consumePosition(final CallbackInfo ci) {
        if (!OverflowAnimations.isEnabled()) {
            return;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        GlStateManager.translatef(items.consumePositionX, items.consumePositionY, items.consumePositionZ);
    }

    @WrapOperation(method = "applyConsuming", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;translatef(FFF)V", ordinal = 0))
    private void overflowanimations$consumeIntensity(final float x, final float y, final float z, final Operation<Void> original) {
        if (!OverflowAnimations.isEnabled()) {
            original.call(x, y, z);
            return;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        final float scale = items.scaleConsumeWithItem && !items.scaleAnimationsWithItem ? items.itemScaleY : 1.0F;
        original.call(x, y * (1.0F + items.consumeIntensity) * scale, z);
    }

    @ModifyArg(method = "applyConsuming", at = @At(value = "INVOKE", target = "Ljava/lang/Math;pow(DD)D"), index = 1)
    private double overflowanimations$consumeSpeed(final double exponent) {
        return OverflowAnimations.isEnabled() ? exponent * (1.0F + LegacyFirstPerson.items().consumeSpeed) : exponent;
    }

    @WrapOperation(method = "applyConsuming", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;translatef(FFF)V", ordinal = 1))
    private void overflowanimations$consumeRotation(final float x, final float y, final float z, final Operation<Void> original) {
        if (!OverflowAnimations.isEnabled()) {
            original.call(x, y, z);
            return;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (items.scaleConsumeWithItem && !items.scaleAnimationsWithItem) {
            original.call(x * items.itemScaleX, y * items.itemScaleY, z * items.itemScaleZ);
        } else {
            original.call(x, y, z);
        }
        GlStateManager.rotatef(items.consumeRotationX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(items.consumeRotationY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(items.consumeRotationZ, 0.0F, 0.0F, 1.0F);
    }

    @ModifyExpressionValue(method = "applyConsuming", at = @At(value = "CONSTANT", args = "floatValue=0.6F"))
    private float overflowanimations$consumeCenteringX(final float original) {
        if (!OverflowAnimations.isEnabled()) {
            return original;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        final float centering = items.lunarItemPositions ? 0.66F : original;
        return items.disableConsumeCentering ? centering + 0.68F : centering;
    }

    @ModifyExpressionValue(method = "applyConsuming", at = @At(value = "CONSTANT", args = "floatValue=10.0F"))
    private float overflowanimations$lunarConsumePitch(final float original) {
        return OverflowAnimations.isEnabled() && LegacyFirstPerson.items().lunarItemPositions ? 5.0F : original;
    }

    @ModifyExpressionValue(method = "applyConsuming", at = @At(value = "CONSTANT", args = "floatValue=30.0F"))
    private float overflowanimations$lunarConsumeRoll(final float original) {
        return OverflowAnimations.isEnabled() && LegacyFirstPerson.items().lunarItemPositions ? 28.0F : original;
    }

    @Inject(
            method = "renderInFirstPerson",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;applyFirstPersonTransform(FF)V", ordinal = 0, shift = At.Shift.AFTER),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;applyConsuming(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;F)V"))
    )
    private void overflowanimations$consumeScale(final float tickDelta, final CallbackInfo ci) {
        if (!OverflowAnimations.isEnabled()) {
            return;
        }
        final float scale = 1.0F + LegacyFirstPerson.items().consumeScale;
        GlStateManager.scalef(scale, scale, scale);
    }

    @ModifyArg(
            method = "renderInFirstPerson",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;applyFirstPersonTransform(FF)V", ordinal = 1),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;applyConsuming(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;F)V")),
            index = 0
    )
    private float overflowanimations$lunarBlockHitEquip(final float equipProgress) {
        return OverflowAnimations.isEnabled() && LegacyFirstPerson.items().lunarBlockHitPosition ? 0.2F : equipProgress;
    }

    @WrapOperation(method = "applySwordBlocking", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;translatef(FFF)V"))
    private void overflowanimations$blockingPosition(final float x, final float y, final float z, final Operation<Void> original) {
        if (!OverflowAnimations.isEnabled()) {
            original.call(x, y, z);
            return;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        original.call(x + items.blockingPositionX, y + items.blockingPositionY, z + items.blockingPositionZ);
        GlStateManager.rotatef(items.blockingRotationX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(items.blockingRotationY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(items.blockingRotationZ, 0.0F, 0.0F, 1.0F);
    }

    @Inject(method = "applySwordBlocking", at = @At("TAIL"))
    private void overflowanimations$blockingScale(final CallbackInfo ci) {
        if (!OverflowAnimations.isEnabled()) {
            return;
        }
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (items.lunarBlockHitPosition) {
            GlStateManager.translatef(-0.55F, 0.2F, 0.1F);
            GlStateManager.scalef(0.85F, 0.85F, 0.85F);
            GlStateManager.rotatef(1.0F, 0.0F, 0.0F, -1.0F);
            GlStateManager.rotatef(1.0F, 0.25F, 0.0F, 0.0F);
            GlStateManager.rotatef(2.0F, 0.0F, 2.0F, 0.0F);
        }
        final float scale = 1.0F + items.blockingScale;
        GlStateManager.scalef(scale, scale, scale);
    }

    @Inject(method = "applyBowNocking", at = @At("HEAD"))
    private void overflowanimations$lunarBowPosition(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && LegacyFirstPerson.items().lunarItemPositions) {
            GlStateManager.translatef(-0.2F, 0.0F, -0.175F);
            GlStateManager.rotatef(1.0F, 0.0F, 0.0F, -1.25F);
        }
    }

    @WrapOperation(method = "applyBowNocking", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;scalef(FFF)V"))
    private void overflowanimations$legacyBowPull(final float x, final float y, final float z, final Operation<Void> original) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        final boolean legacy = OverflowAnimations.isEnabled() && items.firstPersonItemPositions.isOld() && !items.lunarItemPositions;
        if (legacy) {
            GlStateManager.rotatef(-335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotatef(-50.0F, 0.0F, 1.0F, 0.0F);
        }
        original.call(x, y, z);
        if (legacy) {
            GlStateManager.rotatef(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(335.0F, 0.0F, 0.0F, 1.0F);
        }
    }
}
