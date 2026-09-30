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

package org.polyfrost.overflowanimations.mixins.v1.rendering.items;

//? if <26.3 {
/*import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
*///?}
//? if <1.21.9 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?} elif <26.3 {
/*import net.minecraft.client.renderer.SubmitNodeCollector;
*///?}
//? if <26.3 {
/*import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.FirstPersonUtilKt;
import org.polyfrost.overflowanimations.util.ItemUtilKt;
import org.polyfrost.overflowanimations.util.enums.EquipAnimationVersionSetting;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;
*///?}

//? if <26.3 {
/*// TODO/NOTE: Why 500?
@Mixin(value = ItemInHandRenderer.class, priority = 500)
public abstract class MixinItemInHandRenderer_FirstPersonItemPositions {
    @Shadow
    private float mainHandHeight;
*///?}

    //? if <26.3 {
    /*@Shadow
    private ItemStack mainHandItem;
    *///?}

    //? if <26.3 {
    /*@Shadow
    @Final
    private Minecraft minecraft;
    *///?}

    //? if <26.3 {
    /*@Shadow
    @Final
    private ItemModelResolver itemModelResolver;
    *///?}

    //? if <26.3 {
    /*@Shadow
    protected abstract void applyItemArmAttackTransform(final PoseStack poseStack, final HumanoidArm arm, final float attackValue);
    *///?}

    //? if <26.3 {
    /*@Unique
    private int overflowanimations$currentSlot = -1;
    *///?}

    //? if <26.3 {
    /*@Unique
    private ItemStack overflowanimations$mainHandItem = ItemStack.EMPTY;
    *///?}

    //? if <26.3 {
    /*@Unique
    private static final float overflowanimations$TRANSLATE_OFFSET_MULTIPLIER = 0.05F;
    *///?}

    //? if <26.3 {
    /*@Unique
    private ItemStack overflowanimations$renderingItem = ItemStack.EMPTY;
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "swingArm", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void overflowanimations$swingTranslate(final PoseStack instance, final float x, final float y, final float z, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            if (items.disableSwingTranslate) {
                return;
            }
            float scaleX = 1.0F + items.swingPositionX;
            float scaleY = 1.0F - items.swingPositionY;
            float scaleZ = 1.0F + items.swingPositionZ;
            if (items.smartSwingScaling) {
                scaleX *= items.itemScaleX;
                scaleY *= items.itemScaleY;
                scaleZ *= items.itemScaleZ;
            }
            original.call(instance, x * scaleX, y * scaleY, z * scaleZ);
        } else {
            original.call(instance, x, y, z);
        }
    }
    *///?}

    //? if <26.2 {
    /*@ModifyExpressionValue(method = "renderHandsWithItems", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
    *///?} elif 26.2 {
    /*@ModifyExpressionValue(method = "submitHandsWithItems", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
    *///?}
    //? if <26.3 {
    /*private float overflowanimations$disableHandSway(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableHandSway ? 0.0F : original;
    }
    *///?}

    //? if <26.2 {
    /*@Inject(method = "renderArmWithItem", at = @At("HEAD"))
    *///?} elif 26.2 {
    /*@Inject(method = "submitArmWithItem", at = @At("HEAD"))
    *///?}
    //? if <26.3 {
    /*private void overflowanimations$captureRenderingItem(final CallbackInfo ci, @Local(argsOnly = true) final ItemStack itemStack) {
        this.overflowanimations$renderingItem = itemStack;
    }
    *///?}

    //? if <26.3 {
    /*@Inject(method = "applyItemArmTransform", at = @At("HEAD"))
    private void overflowanimations$lunarItemPositions(final PoseStack poseStack, final HumanoidArm arm, final float inverseArmHeight, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions) {
            FirstPersonUtilKt.applyLunarItemPosition(poseStack, this.overflowanimations$renderingItem, EntityUtilKt.getArmMultiplier(arm));
        }
    }
    *///?}

    //? if <26.2 {
    /*@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "floatValue=-0.2785682F")))
    *///?} elif 26.2 {
    /*@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "floatValue=-0.2785682F")))
    *///?}
    //? if <26.3 {
    /*private void overflowanimations$lunarBowPosition(final PoseStack instance, final float x, final float y, final float z, final Operation<Void> original, @Local(ordinal = 0) final HumanoidArm arm) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions) {
            FirstPersonUtilKt.applyLunarBowPosition(instance, EntityUtilKt.getArmMultiplier(arm));
        }
        original.call(instance, x, y, z);
    }
    *///?}

    //? if <26.3 {
    /*@Inject(method = "applyEatTransform", at = @At("HEAD"))
    private void overflowanimations$consumePosition(final CallbackInfo ci, @Local(argsOnly = true) final PoseStack poseStack, @Local(argsOnly = true) final HumanoidArm arm) {
        if (OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            poseStack.translate(EntityUtilKt.getArmMultiplier(arm) * items.consumePositionX, items.consumePositionY, items.consumePositionZ);
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "applyEatTransform", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0))
    private void overflowanimations$consumeIntensity(final PoseStack instance, final float x, final float y, final float z, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            final float scale = items.scaleConsumeWithItem ? items.itemScaleY : 1.0F;
            original.call(instance, x, y * (1.0F + items.consumeIntensity) * scale, z);
        } else {
            original.call(instance, x, y, z);
        }
    }
    *///?}

    //? if <26.3 {
    /*@ModifyArg(method = "applyEatTransform", at = @At(value = "INVOKE", target = "Ljava/lang/Math;pow(DD)D"), index = 1)
    private double overflowanimations$consumeSpeed(final double exponent) {
        return OverflowAnimations.isEnabled() ? exponent * (1.0F + OverflowAnimationsConfig.instance().items.consumeSpeed) : exponent;
    }
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "applyEatTransform", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 1))
    private void overflowanimations$consumeRotation(final PoseStack instance, final float x, final float y, final float z, final Operation<Void> original, @Local(argsOnly = true) final HumanoidArm arm) {
        if (OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            if (items.scaleConsumeWithItem) {
                original.call(instance, x * items.itemScaleX, y * items.itemScaleY, z * items.itemScaleZ);
            } else {
                original.call(instance, x, y, z);
            }
            FirstPersonUtilKt.applyMirroredRotation(instance, EntityUtilKt.getArmMultiplier(arm), items.consumeRotationX, items.consumeRotationY, items.consumeRotationZ);
        } else {
            original.call(instance, x, y, z);
        }
    }
    *///?}

    //? if <26.3 {
    /*@ModifyExpressionValue(method = "applyEatTransform", at = @At(value = "CONSTANT", args = "floatValue=0.6F"))
    private float overflowanimations$lunarConsumeX(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions ? 0.66F : original;
    }
    *///?}

    //? if <26.3 {
    /*@ModifyExpressionValue(method = "applyEatTransform", at = @At(value = "CONSTANT", args = "floatValue=10.0F"))
    private float overflowanimations$lunarConsumePitch(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions ? 5.0F : original;
    }
    *///?}

    //? if <26.3 {
    /*@ModifyExpressionValue(method = "applyEatTransform", at = @At(value = "CONSTANT", args = "floatValue=30.0F"))
    private float overflowanimations$lunarConsumeRoll(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions ? 28.0F : original;
    }
    *///?}

    //? if <26.2 {
    /*@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 0, shift = At.Shift.AFTER), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyEatTransform(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/world/entity/HumanoidArm;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)V")))
    *///?} elif 26.2 {
    /*@Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 0, shift = At.Shift.AFTER), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyEatTransform(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/world/entity/HumanoidArm;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)V")))
    *///?}
    //? if <26.3 {
    /*private void overflowanimations$consumeScale(final CallbackInfo ci, @Local(argsOnly = true) final PoseStack poseStack) {
        if (OverflowAnimations.isEnabled()) {
            final float scale = 1.0F + OverflowAnimationsConfig.instance().items.consumeScale;
            poseStack.scale(scale, scale, scale);
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapMethod(method = "applyItemArmAttackTransform")
    private void overflowanimations$modifySwingPivot(final PoseStack poseStack, final HumanoidArm arm, final float attackValue, final Operation<Void> original) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        final boolean shouldApply = OverflowAnimations.isEnabled() && items.disableSwingPivot;
        if (shouldApply) {
            poseStack.translate(items.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
        }
    *///?}

    //? if <26.3 {
        /*original.call(poseStack, arm, attackValue);
        if (shouldApply) {
            poseStack.translate(items.itemOffsetX * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
        }
    }
    *///?}

    //? if <26.2 {
    /*@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z"))
    *///?} elif 26.2 {
    /*@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z"))
    *///?}
    //? if <26.3 {
    /*private boolean overflowanimations$fixDoubleBlockingVisual$itemUsageVisualInGUI(final AbstractClientPlayer instance, final Operation<Boolean> original) {
        final boolean value = original.call(instance);
        if (OverflowAnimations.isEnabled()) {
    *///?}
            //? if <26.2 {
            /*if (OverflowAnimationsConfig.instance().fixes.fixItemUsageVisualInGUI && this.minecraft.screen != null) {
            *///?} elif 26.2 {
            /*if (OverflowAnimationsConfig.instance().fixes.fixItemUsageVisualInGUI && this.minecraft.gui.screen() != null) {
            *///?}
        //? if <26.3 {
                /*return false;
            } else if (OverflowAnimationsConfig.instance().fixes.fixDoubleUsageVisual) {
                return value && this.minecraft.options.keyUse.isDown();
            }
        }
        *///?}

    //? if <26.3 {
        /*return value;
    }
    *///?}

    //? if <26.2 {
    /*@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
    *///?}
    //? if <26.1 {
    /*private void overflowanimations$postBowTransform(final PoseStack instance, final float xScale, final float yScale, final float zScale, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final AbstractClientPlayer player, @Local(argsOnly = true, ordinal = 0) final InteractionHand hand) {
    *///?} elif 26.2 {
    /*@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
    *///?}
    //? if >=26.1 <26.3 {
    /*private void overflowanimations$postBowTransform(final PoseStack instance, final float xScale, final float yScale, final float zScale, final Operation<Void> original, @Local(argsOnly = true, name = "player") final AbstractClientPlayer player, @Local(argsOnly = true, name = "hand") final InteractionHand hand) {
    *///?}
        //? if <26.3 {
        /*final int direction = EntityUtilKt.getHandMultiplier(player, hand);
        final boolean legacy = OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemPositions && !OverflowAnimationsConfig.instance().items.lunarItemPositions;
        if (legacy) {
            instance.mulPose(Axis.ZP.rotationDegrees(direction * -335));
            instance.mulPose(Axis.YP.rotationDegrees(direction * -50.0F));
        }
        *///?}

    //? if <26.3 {
        /*original.call(instance, xScale, yScale, zScale);
        if (legacy) {
            instance.mulPose(Axis.YP.rotationDegrees(direction * 50.0F));
            instance.mulPose(Axis.ZP.rotationDegrees(direction * 335));
        }
    }
    *///?}

    //? if <26.3 {
    /*@Definition(id = "item", local = @Local(type = ItemStack.class, argsOnly = true))
    @Definition(id = "getItem", method = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;")
    @Definition(id = "ShieldItem", type = ShieldItem.class)
    @Expression("item.getItem() instanceof ShieldItem")
    *///?}
    //? if <26.2 {
    /*@ModifyExpressionValue(method = "renderArmWithItem", at = @At("MIXINEXTRAS:EXPRESSION"))
    *///?}
    //? if <26.1 {
    /*private boolean overflowanimations$oldFirstPersonSwordBlock(final boolean original, @Local(argsOnly = true, ordinal = 0) final AbstractClientPlayer player, @Local(argsOnly = true, ordinal = 0) final InteractionHand hand, @Local(argsOnly = true, ordinal = 0) final ItemStack itemStack, @Local(argsOnly = true, ordinal = 0) final PoseStack poseStack, @Local(argsOnly = true, ordinal = 3) final float inverseArmHeight) {
    *///?} elif 26.2 {
    /*@ModifyExpressionValue(method = "submitArmWithItem", at = @At("MIXINEXTRAS:EXPRESSION"))
    *///?}
    //? if >=26.1 <26.3 {
    /*private boolean overflowanimations$oldFirstPersonSwordBlock(final boolean original, @Local(argsOnly = true, name = "player") final AbstractClientPlayer player, @Local(argsOnly = true, name = "hand") final InteractionHand hand, @Local(argsOnly = true, name = "itemStack") final ItemStack itemStack, @Local(argsOnly = true, name = "poseStack") final PoseStack poseStack, @Local(argsOnly = true, name = "inverseArmHeight") final float inverseArmHeight) {
    *///?}
    //? if <26.3 {
        /*final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        if (OverflowAnimations.isEnabled() && (items.itemPositions || items.lunarBlockHitPosition) && !(itemStack.getItem() instanceof ShieldItem)) {
            final int direction = EntityUtilKt.getHandMultiplier(player, hand);
            if (items.lunarBlockHitPosition) {
                poseStack.translate(0.0F, (0.2F - inverseArmHeight) * -0.6F, 0.0F);
            }
            // We do this to fix a rounding error in Mojangs code.
            ItemUtilKt.applyLegacyFirstPersonTransforms(poseStack, direction, () -> {
                poseStack.translate(direction * (-0.5F + items.blockingPositionX), 0.2F + items.blockingPositionY, items.blockingPositionZ);
                FirstPersonUtilKt.applyMirroredRotation(poseStack, direction, items.blockingRotationX, items.blockingRotationY, items.blockingRotationZ);
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 30.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 60.0F));
                if (items.lunarBlockHitPosition) {
                    FirstPersonUtilKt.applyLunarBlockHitPosition(poseStack, direction);
                }
                final float scale = 1.0F + items.blockingScale;
                poseStack.scale(scale, scale, scale);
            });
            return true; // Cancels the vanilla blocking code
        } else {
            return original;
        }
    }
    *///?}

    //? if <1.21.5 {
    /*@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    *///?} elif <1.21.9 {
    /*@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    *///?} elif <26.2 {
    /*@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V"))
    *///?} elif 26.2 {
    /*@Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V"))
    *///?}
    //? if <1.21.9 {
    /*private void overflowanimations$itemPositions(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final CallbackInfo ci) {
    *///?} elif <26.3 {
    /*private void overflowanimations$itemPositions(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci) {
    *///?}
    //? if <26.3 {
    /*final int direction = EntityUtilKt.getHandMultiplier(player, hand);
        if (OverflowAnimations.isEnabled()) {
            if (OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 180.0F));
            }
    *///?}

            //? if <26.3 {
            /*final ItemDisplayContext displayContext = hand == InteractionHand.MAIN_HAND ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
            final ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(
                    itemStackRenderState,
                    itemStack,
                    displayContext,
                    //? if <1.21.5
                    //displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    player.level(),
                    player,
                    lightCoords
            ); // TODO/NOTE: Might be wrong
            *///?}

            //? if <26.3 {
            /*final boolean isNotBlock3d = !ItemUtilKt.isBlock3d(itemStack, itemStackRenderState.usesBlockLight());
            if (OverflowAnimationsConfig.instance().items.itemPositions && !OverflowAnimationsConfig.instance().items.lunarItemPositions && !(OverflowAnimationsConfig.instance().items.lunarBlockHitPosition && ItemUtilKt.isSwordItem(itemStack)) && isNotBlock3d && !ItemUtilKt.isItemBlacklisted(itemStack)) {
                final float radians = 0.4363323129985824F;
            *///?}

                //? if <26.3 {
                /*poseStack.scale(0.6F, 0.6F, 0.6F);
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 275.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 25.0F));
                poseStack.translate(direction * (-0.2F * Math.sin(radians) + 0.4375F), -0.2F * Math.cos(radians) + 0.4375F, 0.03125F);
                *///?}

            //? if <26.3 {
                /*poseStack.scale(1 / 0.68F, 1 / 0.68F, 1 / 0.68F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -25.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 90.0F));
                poseStack.translate(direction * -1.13 * 0.0625F, -3.2 * 0.0625F, -1.13 * 0.0625F);
            }
            *///?}

            //? if <26.3 {
            /*final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            if (OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons) {
                if (items.applyCustomizationToBlockItems) {
                    poseStack.translate(items.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                }
            *///?}

                //? if <26.3 {
                /*poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
                poseStack.scale(0.4F, 0.4F, 0.4F);
                *///?}

                //? if <26.3 {
                /*// TODO: This is not quite right... (@Mixces)
                poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                if (!items.applyCustomizationToBlockItems) {
                    poseStack.translate(0.0F, 0.25F, 0.0F);
                }
                *///?}

            //? if <26.3 {
                /*poseStack.scale(1.125F, 1.125F, 1.125F);
            }
            *///?}

    //? if <26.3 {
            /*if (isNotBlock3d || items.applyCustomizationToBlockItems) {
                if (OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
                    poseStack.translate(items.itemOffsetX * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                } else if (!(OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons)) {
                    poseStack.translate(items.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                }
                poseStack.scale(items.itemScaleX, items.itemScaleY, items.itemScaleZ);
                poseStack.mulPose(Axis.XP.rotationDegrees(direction * items.itemRotationX));
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * items.itemRotationY));
                poseStack.mulPose(Axis.ZP.rotationDegrees(direction * items.itemRotationZ));
            }
        }
    }
    *///?}

    //? if <26.2 {
    /*@Inject(method = "renderArmWithItem",
    *///?} elif 26.2 {
    /*@Inject(method = "submitArmWithItem",
    *///?}
            //? if <1.21.11 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 4)
            ))
            *///?} elif <26.3 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 2)
            ))
            *///?}
    //? if <1.21.9 {
    /*private void overflowanimations$itemUsageSwinging(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final CallbackInfo ci, @Local(ordinal = 0) final HumanoidArm arm) {
    *///?} elif <26.1 {
    /*private void overflowanimations$itemUsageSwinging(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci, @Local(ordinal = 0) final HumanoidArm arm) {
    *///?} elif >=26.1 <26.3 {
    /*private void overflowanimations$itemUsageSwinging(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci, @Local(name = "arm") final HumanoidArm arm) {
    *///?}
    //? if <26.3 {
        /*if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemUsageSwinging) {
            this.applyItemArmAttackTransform(poseStack, arm, swingProgress);
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
    private boolean overflowanimations$heldItemVisibilityInBoat(final LocalPlayer instance, final Operation<Boolean> original) {
        return (!OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().items.heldItemVisibilityInBoat) && original.call(instance);
    }
    *///?}

    //? if <26.3 {
    /*@ModifyExpressionValue(method = "tick", at = {@At(value = "CONSTANT", args = "floatValue=0.4F"), @At(value = "CONSTANT", args = "floatValue=-0.4F")})
    private float overflowanimations$reequipSpeed(final float original) {
        return OverflowAnimations.isEnabled() ? Math.copySign(OverflowAnimationsConfig.instance().items.reequipSpeed, original) : original;
    }
    *///?}

    //? if <1.21.11 {
    /*@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    private float overflowanimations$legacySwingAnimation(final LocalPlayer instance, final float delta, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.legacySwingAnimation) {
            return 1.0F;
        } else {
            return original.call(instance, delta);
        }
    }
    *///?}

    //? if >=1.21.11 <26.3 {
    /*@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getItemSwapScale(F)F"))
    private float overflowanimations$legacySwingAnimation(final LocalPlayer instance, final float delta, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.legacySwingAnimation) {
            return 1.0F;
        } else {
            return original.call(instance, delta);
        }
    }
    *///?}

    // Equip Animation Stuff
    //? if <1.21.9 {
    /*@ModifyArg(method = "renderHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", ordinal = 0), index = 5)
    *///?} elif <26.2 {
    /*@ModifyArg(method = "renderHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", ordinal = 0), index = 5)
    *///?} elif 26.2 {
    /*@ModifyArg(method = "submitHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;submitArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", ordinal = 0), index = 5)
    *///?}
    //? if <26.3 {
    /*private ItemStack overflowanimations$useCopyStackFieldForRender(final ItemStack original) {
        // TODO/NOTE: 26.2 makes the item persist in hand even when empty (temp check added)
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.equipAnimationVersion.useStackForRendering() && !original.isEmpty()) {
            // Use our copied stack field for hand animations
            return this.overflowanimations$mainHandItem;
        } else {
            return original;
        }
    }
    *///?}

    //? if <26.3 {
    /*@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;shouldInstantlyReplaceVisibleItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 0))
    private boolean overflowanimations$disableEquipConstraint(final boolean original) {
        return (!OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().items.equipAnimationVersion.useStackForRendering()) && original;
    }
    *///?}

    //? if <26.3 {
    /*@Inject(method = "shouldInstantlyReplaceVisibleItem", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$skipEquipAnimation(final ItemStack currentlyVisibleItem, final ItemStack expectedItem, final CallbackInfoReturnable<Boolean> cir) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.equipAnimationVersion == EquipAnimationVersionSetting.DISABLED && this.minecraft.player != null) {
            cir.setReturnValue(true);
        }
    }
    *///?}

    //? if <26.3 {
    /*// Fixes MC-262560
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 2), index = 0)
    *///?}
    //? if <26.1 {
    /*private float overflowanimations$handleEquipLogic(final float original, @Local(ordinal = 0) float attackAnim) {
    *///?} elif >=26.1 <26.3 {
    /*private float overflowanimations$handleEquipLogic(final float original, @Local(name = "attackAnim") float attackAnim) {
    *///?}
        //? if <26.3 {
        /*final LocalPlayer player = this.minecraft.player;
        final EquipAnimationVersionSetting setting = OverflowAnimationsConfig.instance().items.equipAnimationVersion;
        if (OverflowAnimations.isEnabled() && setting != EquipAnimationVersionSetting.VANILLA && setting != EquipAnimationVersionSetting.DISABLED && player != null) {
            final float scale = (float) Math.pow(attackAnim, 3);
            final ItemStack stackCopy = player.getInventory().getSelectedItem().copy();
        *///?}

            //? if <26.3 {
            /*float mainHandTargetHeight = stackCopy == this.overflowanimations$mainHandItem ? scale : 0;
            if (this.overflowanimations$mainHandItem.isEmpty() && stackCopy.isEmpty()) {
                mainHandTargetHeight = scale;
            }
            *///?}

            //? if <26.3 {
            /*if (!stackCopy.isEmpty() && !this.overflowanimations$mainHandItem.isEmpty() &&
                    stackCopy != this.overflowanimations$mainHandItem && stackCopy.getItem() == this.overflowanimations$mainHandItem.getItem() &&
                    stackCopy.getDamageValue() == this.overflowanimations$mainHandItem.getDamageValue()) {
                this.overflowanimations$mainHandItem = stackCopy;
                mainHandTargetHeight = scale;
            }
            *///?}

            //? if <26.3 {
            /*if (setting == EquipAnimationVersionSetting.V1_7 && this.overflowanimations$currentSlot != player.getInventory().getSelectedSlot()) {
                mainHandTargetHeight = 0;
            }
            *///?}

    //? if <26.3 {
            /*return mainHandTargetHeight - this.mainHandHeight;
        } else {
            return original;
        }
    }
    *///?}

//? if <26.3 {
    /*@Inject(method = "tick", at = @At("TAIL"))
    private void overflowanimations$updateFakeItem(final CallbackInfo ci) {
        final LocalPlayer player = this.minecraft.player;
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.VANILLA && OverflowAnimationsConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.DISABLED && player != null && this.mainHandHeight < 0.1F) {
            this.overflowanimations$mainHandItem = this.mainHandItem.copy();
            this.overflowanimations$currentSlot = this.minecraft.player.getInventory().getSelectedSlot();
        }
    }
}
*///?}
