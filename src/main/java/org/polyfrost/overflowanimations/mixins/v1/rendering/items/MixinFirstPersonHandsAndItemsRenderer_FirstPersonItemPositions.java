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

//? if >=26.3 {
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.FirstPersonUtilKt;
import org.polyfrost.overflowanimations.util.ItemUtilKt;
import org.polyfrost.overflowanimations.util.duck.FirstPersonHandsAndItemsRenderStateExt;
import org.polyfrost.overflowanimations.util.enums.EquipAnimationVersionSetting;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;
//?}

//? if >=26.3 {
// TODO/NOTE: Why 500?
@Mixin(value = FirstPersonHandsAndItemsRenderer.class, priority = 500)
public abstract class MixinFirstPersonHandsAndItemsRenderer_FirstPersonItemPositions {
    @Shadow
    @Final
    private Minecraft minecraft;
//?}

    //? if >=26.3 {
    @Shadow
    protected abstract void applyItemArmAttackTransform(final PoseStack poseStack, final HumanoidArm arm, final float attackValue);
    //?}

    //? if >=26.3 {
    @Unique
    private static final float overflowanimations$TRANSLATE_OFFSET_MULTIPLIER = 0.05F;
    //?}

    //? if >=26.3 {
    @Unique
    private ItemStack overflowanimations$renderingItem = ItemStack.EMPTY;
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "swingArm", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
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
    //?}

    //? if >=26.3 {
    @ModifyExpressionValue(method = "submitHandsWithItems", at = @At(value = "CONSTANT", args = "floatValue=0.1F"))
    private float overflowanimations$disableHandSway(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableHandSway ? 0.0F : original;
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "submitArmWithItem", at = @At("HEAD"))
    private void overflowanimations$captureRenderingItem(final CallbackInfo ci, @Local(argsOnly = true) final ItemStack itemStack) {
        this.overflowanimations$renderingItem = itemStack;
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "applyItemArmTransform", at = @At("HEAD"))
    private void overflowanimations$lunarItemPositions(final PoseStack poseStack, final HumanoidArm arm, final float inverseArmHeight, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions) {
            FirstPersonUtilKt.applyLunarItemPosition(poseStack, this.overflowanimations$renderingItem, EntityUtilKt.getArmMultiplier(arm));
        }
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "floatValue=-0.2785682F")))
    private void overflowanimations$lunarBowPosition(final PoseStack instance, final float x, final float y, final float z, final Operation<Void> original, @Local(ordinal = 0) final HumanoidArm arm) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions) {
            FirstPersonUtilKt.applyLunarBowPosition(instance, EntityUtilKt.getArmMultiplier(arm));
        }
        original.call(instance, x, y, z);
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "applyEatTransform", at = @At("HEAD"))
    private void overflowanimations$consumePosition(final CallbackInfo ci, @Local(argsOnly = true) final PoseStack poseStack, @Local(argsOnly = true) final HumanoidArm arm) {
        if (OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            poseStack.translate(EntityUtilKt.getArmMultiplier(arm) * items.consumePositionX, items.consumePositionY, items.consumePositionZ);
        }
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "applyEatTransform", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0))
    private void overflowanimations$consumeIntensity(final PoseStack instance, final float x, final float y, final float z, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled()) {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            final float scale = items.scaleConsumeWithItem ? items.itemScaleY : 1.0F;
            original.call(instance, x, y * (1.0F + items.consumeIntensity) * scale, z);
        } else {
            original.call(instance, x, y, z);
        }
    }
    //?}

    //? if >=26.3 {
    @ModifyArg(method = "applyEatTransform", at = @At(value = "INVOKE", target = "Ljava/lang/Math;pow(DD)D"), index = 1)
    private double overflowanimations$consumeSpeed(final double exponent) {
        return OverflowAnimations.isEnabled() ? exponent * (1.0F + OverflowAnimationsConfig.instance().items.consumeSpeed) : exponent;
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "applyEatTransform", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 1))
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
    //?}

    //? if >=26.3 {
    @ModifyExpressionValue(method = "applyEatTransform", at = @At(value = "CONSTANT", args = "floatValue=0.6F"))
    private float overflowanimations$lunarConsumeX(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions ? 0.66F : original;
    }
    //?}

    //? if >=26.3 {
    @ModifyExpressionValue(method = "applyEatTransform", at = @At(value = "CONSTANT", args = "floatValue=10.0F"))
    private float overflowanimations$lunarConsumePitch(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions ? 5.0F : original;
    }
    //?}

    //? if >=26.3 {
    @ModifyExpressionValue(method = "applyEatTransform", at = @At(value = "CONSTANT", args = "floatValue=30.0F"))
    private float overflowanimations$lunarConsumeRoll(final float original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.lunarItemPositions ? 28.0F : original;
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 0, shift = At.Shift.AFTER), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyEatTransform(Lcom/mojang/blaze3d/vertex/PoseStack;FLnet/minecraft/world/entity/HumanoidArm;FI)V")))
    private void overflowanimations$consumeScale(final CallbackInfo ci, @Local(argsOnly = true) final PoseStack poseStack) {
        if (OverflowAnimations.isEnabled()) {
            final float scale = 1.0F + OverflowAnimationsConfig.instance().items.consumeScale;
            poseStack.scale(scale, scale, scale);
        }
    }
    //?}

    //? if >=26.3 {
    @WrapMethod(method = "applyItemArmAttackTransform")
    private void overflowanimations$modifySwingPivot(final PoseStack poseStack, final HumanoidArm arm, final float attackValue, final Operation<Void> original) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        final boolean shouldApply = OverflowAnimations.isEnabled() && items.disableSwingPivot;
        if (shouldApply) {
            poseStack.translate(items.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
        }
    //?}

    //? if >=26.3 {
        original.call(poseStack, arm, attackValue);
        if (shouldApply) {
            poseStack.translate(items.itemOffsetX * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
        }
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "submitArmWithItem", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;isUsingItem:Z", opcode = Opcodes.GETFIELD))
    private boolean overflowanimations$fixDoubleBlockingVisual$itemUsageVisualInGUI(final AvatarRenderState instance, final Operation<Boolean> original) {
        final boolean value = original.call(instance);
        if (OverflowAnimations.isEnabled()) {
            if (OverflowAnimationsConfig.instance().fixes.fixItemUsageVisualInGUI && this.minecraft.gui.screen() != null) {
                return false;
            } else if (OverflowAnimationsConfig.instance().fixes.fixDoubleUsageVisual) {
                return value && this.minecraft.options.keyUse.isDown();
            }
        }
    //?}

    //? if >=26.3 {
        return value;
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
    private void overflowanimations$postBowTransform(final PoseStack instance, final float xScale, final float yScale, final float zScale, final Operation<Void> original, @Local(argsOnly = true, name = "playerState") final PlayerRenderState playerState, @Local(argsOnly = true, name = "hand") final InteractionHand hand) {
        final int direction = playerState.avatarRenderState != null ? EntityUtilKt.getHandMultiplier(playerState.avatarRenderState, hand) : 1;
        final boolean legacy = OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemPositions && !OverflowAnimationsConfig.instance().items.lunarItemPositions;
        if (legacy) {
            instance.rotate(Axis.ZP.rotationDegrees(direction * -335));
            instance.rotate(Axis.YP.rotationDegrees(direction * -50.0F));
        }
    //?}

    //? if >=26.3 {
        original.call(instance, xScale, yScale, zScale);
        if (legacy) {
            instance.rotate(Axis.YP.rotationDegrees(direction * 50.0F));
            instance.rotate(Axis.ZP.rotationDegrees(direction * 335));
        }
    }
    //?}

    //? if >=26.3 {
    @Definition(id = "item", local = @Local(type = ItemStack.class, argsOnly = true))
    @Definition(id = "getItem", method = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;")
    @Definition(id = "ShieldItem", type = ShieldItem.class)
    @Expression("item.getItem() instanceof ShieldItem")
    @ModifyExpressionValue(method = "submitArmWithItem", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean overflowanimations$oldFirstPersonSwordBlock(final boolean original, @Local(argsOnly = true, name = "playerState") final PlayerRenderState playerState, @Local(argsOnly = true, name = "hand") final InteractionHand hand, @Local(argsOnly = true, name = "itemStack") final ItemStack itemStack, @Local(argsOnly = true, name = "poseStack") final PoseStack poseStack, @Local(argsOnly = true, name = "inverseArmHeight") final float inverseArmHeight) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        if (OverflowAnimations.isEnabled() && (items.itemPositions || items.lunarBlockHitPosition) && !(itemStack.getItem() instanceof ShieldItem) && playerState.avatarRenderState != null) {
            final int direction = EntityUtilKt.getHandMultiplier(playerState.avatarRenderState, hand);
            if (items.lunarBlockHitPosition) {
                poseStack.translate(0.0F, (0.2F - inverseArmHeight) * -0.6F, 0.0F);
            }
            // We do this to fix a rounding error in Mojangs code.
            ItemUtilKt.applyLegacyFirstPersonTransforms(poseStack, direction, () -> {
                poseStack.translate(direction * (-0.5F + items.blockingPositionX), 0.2F + items.blockingPositionY, items.blockingPositionZ);
                FirstPersonUtilKt.applyMirroredRotation(poseStack, direction, items.blockingRotationX, items.blockingRotationY, items.blockingRotationZ);
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 30.0F));
                poseStack.rotate(Axis.XP.rotationDegrees(-80.0F));
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 60.0F));
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
    //?}

    //? if >=26.3 {
    @Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"))
    private void overflowanimations$itemPositions(final PlayerRenderState playerState, final FirstPersonHandsAndItemsRenderState state, final float tickDelta, final float xRot, final InteractionHand hand, final float attack, final ItemStack itemStack, final float inverseArmHeight, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && playerState.avatarRenderState != null) {
            final int direction = EntityUtilKt.getHandMultiplier(playerState.avatarRenderState, hand);
            if (OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 180.0F));
            }
    //?}

            //? if >=26.3 {
            final ItemStackRenderState itemStackRenderState = hand == InteractionHand.MAIN_HAND ? state.mainHandRenderState : state.offHandRenderState;
            //?}

            //? if >=26.3 {
            final boolean isNotBlock3d = !ItemUtilKt.isBlock3d(itemStack, itemStackRenderState.usesBlockLight());
            if (OverflowAnimationsConfig.instance().items.itemPositions && !OverflowAnimationsConfig.instance().items.lunarItemPositions && !(OverflowAnimationsConfig.instance().items.lunarBlockHitPosition && ItemUtilKt.isSwordItem(itemStack)) && isNotBlock3d && !ItemUtilKt.isItemBlacklisted(itemStack)) {
                final float radians = 0.4363323129985824F;
            //?}

                //? if >=26.3 {
                poseStack.scale(0.6F, 0.6F, 0.6F);
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 275.0F));
                poseStack.rotate(Axis.ZP.rotationDegrees(direction * 25.0F));
                poseStack.translate(direction * (-0.2F * Math.sin(radians) + 0.4375F), -0.2F * Math.cos(radians) + 0.4375F, 0.03125F);
                //?}

            //? if >=26.3 {
                poseStack.scale(1 / 0.68F, 1 / 0.68F, 1 / 0.68F);
                poseStack.rotate(Axis.ZP.rotationDegrees(direction * -25.0F));
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 90.0F));
                poseStack.translate(direction * -1.13 * 0.0625F, -3.2 * 0.0625F, -1.13 * 0.0625F);
            }
            //?}

            //? if >=26.3 {
            final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
            if (OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons) {
                if (items.applyCustomizationToBlockItems) {
                    poseStack.translate(items.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                }
            //?}

                //? if >=26.3 {
                poseStack.rotate(Axis.YP.rotationDegrees(45.0F));
                poseStack.scale(0.4F, 0.4F, 0.4F);
                //?}

                //? if >=26.3 {
                // TODO: This is not quite right... (@Mixces)
                poseStack.rotate(Axis.YP.rotationDegrees(-180.0F));
                if (!items.applyCustomizationToBlockItems) {
                    poseStack.translate(0.0F, 0.25F, 0.0F);
                }
                //?}

            //? if >=26.3 {
                poseStack.scale(1.125F, 1.125F, 1.125F);
            }
            //?}

            //? if >=26.3 {
            if (isNotBlock3d || items.applyCustomizationToBlockItems) {
                if (OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
                    poseStack.translate(items.itemOffsetX * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                } else if (!(OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons)) {
                    poseStack.translate(items.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, items.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                }
            //?}

    //? if >=26.3 {
                poseStack.scale(items.itemScaleX, items.itemScaleY, items.itemScaleZ);
                poseStack.rotateDegrees(Axis.XP, direction * items.itemRotationX);
                poseStack.rotateDegrees(Axis.YP, direction * items.itemRotationY);
                poseStack.rotateDegrees(Axis.ZP, direction * items.itemRotationZ);
            }
        }
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "submitArmWithItem",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 1, shift = At.Shift.AFTER),
                    @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 2, shift = At.Shift.AFTER)
            })
    private void overflowanimations$itemUsageSwinging(final PlayerRenderState playerState, final FirstPersonHandsAndItemsRenderState state, final float tickDelta, final float xRot, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float inverseArmHeight, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci, @Local(name = "arm") final HumanoidArm arm) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemUsageSwinging) {
            this.applyItemArmAttackTransform(poseStack, arm, swingProgress);
        }
    }
    //?}

//? if >=26.3 {
    // Equip Animation Stuff
    @ModifyArg(method = "submitHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;submitArmWithItem(Lnet/minecraft/client/renderer/state/level/PlayerRenderState;Lnet/minecraft/client/renderer/state/level/FirstPersonHandsAndItemsRenderState;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", ordinal = 0), index = 6)
    private ItemStack overflowanimations$useCopyStackFieldForRender(final ItemStack original, @Local(argsOnly = true, name = "state") final FirstPersonHandsAndItemsRenderState state) {
        // TODO/NOTE: 26.2 makes the item persist in hand even when empty (temp check added)
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.equipAnimationVersion.useStackForRendering() && !original.isEmpty()) {
            // Use our copied stack field for hand animations
            return ((FirstPersonHandsAndItemsRenderStateExt) state).overflowanimations$getMainHandItem();
        } else {
            return original;
        }
    }
}
//?}
