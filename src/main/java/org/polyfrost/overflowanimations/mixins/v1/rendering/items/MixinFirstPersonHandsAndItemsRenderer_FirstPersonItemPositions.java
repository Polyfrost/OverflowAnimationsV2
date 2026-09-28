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
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
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
import org.polyfrost.overflowanimations.config.category.ExtrasConfigCategory;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
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
    @ModifyExpressionValue(method = {"renderOneHandedMap", "renderTwoHandedMap", "submitArmWithItem"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;isInvisible:Z", opcode = Opcodes.GETFIELD))
    private boolean overflowanimations$showArmWhileInvisible(final boolean original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().extras.showArmWhileInvisible) {
            return false;
        } else {
            return original;
        }
    }
    //?}

    //? if >=26.3 {
    @WrapWithCondition(method = "swingArm", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private boolean overflowanimations$disableSwingTranslate(final PoseStack instance, final float x, final float y, final float z) {
        if (OverflowAnimations.isEnabled()) {
            return !OverflowAnimationsConfig.instance().extras.disableSwingTranslate;
        } else {
            return true;
        }
    }
    //?}

    //? if >=26.3 {
    @WrapMethod(method = "applyItemArmAttackTransform")
    private void overflowanimations$modifySwingPivot(final PoseStack poseStack, final HumanoidArm arm, final float attackValue, final Operation<Void> original) {
        final ExtrasConfigCategory extras = OverflowAnimationsConfig.instance().extras;
        final boolean shouldApply = OverflowAnimations.isEnabled() && extras.disableSwingPivot;
        if (shouldApply) {
            poseStack.translate(extras.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
        }
    //?}

    //? if >=26.3 {
        original.call(poseStack, arm, attackValue);
        if (shouldApply) {
            poseStack.translate(extras.itemOffsetX * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
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
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemPositions) {
            instance.rotate(Axis.ZP.rotationDegrees(direction * -335));
            instance.rotate(Axis.YP.rotationDegrees(direction * -50.0F));
        }
    //?}

    //? if >=26.3 {
        original.call(instance, xScale, yScale, zScale);
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemPositions) {
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
    private boolean overflowanimations$oldFirstPersonSwordBlock(final boolean original, @Local(argsOnly = true, name = "playerState") final PlayerRenderState playerState, @Local(argsOnly = true, name = "hand") final InteractionHand hand, @Local(argsOnly = true, name = "itemStack") final ItemStack itemStack, @Local(argsOnly = true, name = "poseStack") final PoseStack poseStack) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemPositions && !(itemStack.getItem() instanceof ShieldItem) && playerState.avatarRenderState != null) {
            final int direction = EntityUtilKt.getHandMultiplier(playerState.avatarRenderState, hand);
            // We do this to fix a rounding error in Mojangs code.
            ItemUtilKt.applyLegacyFirstPersonTransforms(poseStack, direction, () -> {
                poseStack.translate(direction * -0.5F, 0.2F, 0.0F);
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 30.0F));
                poseStack.rotate(Axis.XP.rotationDegrees(-80.0F));
                poseStack.rotate(Axis.YP.rotationDegrees(direction * 60.0F));
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
            if (OverflowAnimationsConfig.instance().items.itemPositions && isNotBlock3d && !ItemUtilKt.isItemBlacklisted(itemStack)) {
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
            final ExtrasConfigCategory extras = OverflowAnimationsConfig.instance().extras;
            if (OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons) {
                if (extras.applyCustomizationToBlockItems) {
                    poseStack.translate(extras.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                }
            //?}

                //? if >=26.3 {
                poseStack.rotate(Axis.YP.rotationDegrees(45.0F));
                poseStack.scale(0.4F, 0.4F, 0.4F);
                //?}

                //? if >=26.3 {
                // TODO: This is not quite right... (@Mixces)
                poseStack.rotate(Axis.YP.rotationDegrees(-180.0F));
                if (!extras.applyCustomizationToBlockItems) {
                    poseStack.translate(0.0F, 0.25F, 0.0F);
                }
                //?}

            //? if >=26.3 {
                poseStack.scale(1.125F, 1.125F, 1.125F);
            }
            //?}

            //? if >=26.3 {
            if (isNotBlock3d || extras.applyCustomizationToBlockItems) {
                if (OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
                    poseStack.translate(extras.itemOffsetX * -overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                } else if (!(OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !OverflowAnimationsConfig.instance().items.mobHeadIcons)) {
                    poseStack.translate(extras.itemOffsetX * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * overflowanimations$TRANSLATE_OFFSET_MULTIPLIER);
                }
            //?}

    //? if >=26.3 {
                poseStack.scale(extras.itemScaleX, extras.itemScaleY, extras.itemScaleZ);
                poseStack.rotateDegrees(Axis.XP, direction * extras.itemRotationX);
                poseStack.rotateDegrees(Axis.YP, direction * extras.itemRotationY);
                poseStack.rotateDegrees(Axis.ZP, direction * extras.itemRotationZ);
            }
        }
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "submitArmWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 4)
            ))
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
