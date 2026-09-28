/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.rendering.items;

//? if <26.3 {
/*import com.llamalad7.mixinextras.expression.Definition;
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
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModelResolver;
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
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.config.category.ExtrasConfigCategory;
import org.visuals.legacy.animatium.util.EntityUtilKt;
import org.visuals.legacy.animatium.util.ItemUtilKt;
import org.visuals.legacy.animatium.util.enums.EquipAnimationVersionSetting;
import org.visuals.legacy.animatium.util.enums.FishingRodVersionSetting;
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
    private int animatium$currentSlot = -1;
    *///?}

    //? if <26.3 {
    /*@Unique
    private ItemStack animatium$mainHandItem = ItemStack.EMPTY;
    *///?}

    //? if <26.3 {
    /*@Unique
    private static final float animatium$TRANSLATE_OFFSET_MULTIPLIER = 0.05F;
    *///?}

    //? if <26.3 {
    /*@SuppressWarnings({"MixinAnnotationTarget"})
    *///?}
    //? if <26.2 {
    /*@ModifyExpressionValue(method = {"renderOneHandedMap", "renderTwoHandedMap", "renderArmWithItem"}, at = {
    *///?} elif 26.2 {
    /*@ModifyExpressionValue(method = {"renderOneHandedMap", "renderTwoHandedMap", "submitArmWithItem"}, at = {
    *///?}
    //? if <26.3 {
            /*@At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isInvisible()Z"),
            @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInvisible()Z")
    })
    private boolean animatium$showArmWhileInvisible(final boolean original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.showArmWhileInvisible) {
            return false;
        } else {
            return original;
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapWithCondition(method = "swingArm", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private boolean animatium$disableSwingTranslate(final PoseStack instance, final float x, final float y, final float z) {
        if (Animatium.isEnabled()) {
            return !AnimatiumConfig.instance().extras.disableSwingTranslate;
        } else {
            return true;
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapMethod(method = "applyItemArmAttackTransform")
    private void animatium$modifySwingPivot(final PoseStack poseStack, final HumanoidArm arm, final float attackValue, final Operation<Void> original) {
        final ExtrasConfigCategory extras = AnimatiumConfig.instance().extras;
        final boolean shouldApply = Animatium.isEnabled() && extras.disableSwingPivot;
        if (shouldApply) {
            poseStack.translate(extras.itemOffsetX * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * animatium$TRANSLATE_OFFSET_MULTIPLIER);
        }
    *///?}

    //? if <26.3 {
        /*original.call(poseStack, arm, attackValue);
        if (shouldApply) {
            poseStack.translate(extras.itemOffsetX * -animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * -animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * -animatium$TRANSLATE_OFFSET_MULTIPLIER);
        }
    }
    *///?}

    //? if <26.2 {
    /*@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z"))
    *///?} elif 26.2 {
    /*@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isUsingItem()Z"))
    *///?}
    //? if <26.3 {
    /*private boolean animatium$fixDoubleBlockingVisual$itemUsageVisualInGUI(final AbstractClientPlayer instance, final Operation<Boolean> original) {
        final boolean value = original.call(instance);
        if (Animatium.isEnabled()) {
    *///?}
            //? if <26.2 {
            /*if (AnimatiumConfig.instance().fixes.fixItemUsageVisualInGUI && this.minecraft.screen != null) {
            *///?} elif 26.2 {
            /*if (AnimatiumConfig.instance().fixes.fixItemUsageVisualInGUI && this.minecraft.gui.screen() != null) {
            *///?}
        //? if <26.3 {
                /*return false;
            } else if (AnimatiumConfig.instance().fixes.fixDoubleUsageVisual) {
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
    //? if 1.21.11 {
    /*private void animatium$postBowTransform(final PoseStack instance, final float xScale, final float yScale, final float zScale, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final AbstractClientPlayer player, @Local(argsOnly = true, ordinal = 0) final InteractionHand hand) {
    *///?} elif 26.2 {
    /*@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
    *///?}
    //? if >=26.1 <26.3 {
    /*private void animatium$postBowTransform(final PoseStack instance, final float xScale, final float yScale, final float zScale, final Operation<Void> original, @Local(argsOnly = true, name = "player") final AbstractClientPlayer player, @Local(argsOnly = true, name = "hand") final InteractionHand hand) {
    *///?}
        //? if <26.3 {
        /*final int direction = EntityUtilKt.getHandMultiplier(player, hand);
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.itemPositions) {
            instance.mulPose(Axis.ZP.rotationDegrees(direction * -335));
            instance.mulPose(Axis.YP.rotationDegrees(direction * -50.0F));
        }
        *///?}

    //? if <26.3 {
        /*original.call(instance, xScale, yScale, zScale);
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.itemPositions) {
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
    //? if 1.21.11 {
    /*private boolean animatium$oldFirstPersonSwordBlock(final boolean original, @Local(argsOnly = true, ordinal = 0) final AbstractClientPlayer player, @Local(argsOnly = true, ordinal = 0) final InteractionHand hand, @Local(argsOnly = true, ordinal = 0) final ItemStack itemStack, @Local(argsOnly = true, ordinal = 0) final PoseStack poseStack) {
    *///?} elif 26.2 {
    /*@ModifyExpressionValue(method = "submitArmWithItem", at = @At("MIXINEXTRAS:EXPRESSION"))
    *///?}
    //? if >=26.1 <26.3 {
    /*private boolean animatium$oldFirstPersonSwordBlock(final boolean original, @Local(argsOnly = true, name = "player") final AbstractClientPlayer player, @Local(argsOnly = true, name = "hand") final InteractionHand hand, @Local(argsOnly = true, name = "itemStack") final ItemStack itemStack, @Local(argsOnly = true, name = "poseStack") final PoseStack poseStack) {
    *///?}
    //? if <26.3 {
        /*if (Animatium.isEnabled() && AnimatiumConfig.instance().items.itemPositions && !(itemStack.getItem() instanceof ShieldItem)) {
            final int direction = EntityUtilKt.getHandMultiplier(player, hand);
            // We do this to fix a rounding error in Mojangs code.
            ItemUtilKt.applyLegacyFirstPersonTransforms(poseStack, direction, () -> {
                poseStack.translate(direction * -0.5F, 0.2F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 30.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 60.0F));
            });
            return true; // Cancels the vanilla blocking code
        } else {
            return original;
        }
    }
    *///?}

    //? if <26.2 {
    /*@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V"))
    *///?} elif 26.2 {
    /*@Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V"))
    *///?}
    //? if <26.3 {
    /*private void animatium$itemPositions(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci) {
        final int direction = EntityUtilKt.getHandMultiplier(player, hand);
        if (Animatium.isEnabled()) {
            if (AnimatiumConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
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
                    player.level(),
                    player,
                    lightCoords
            ); // TODO/NOTE: Might be wrong
            *///?}

            //? if <26.3 {
            /*final boolean isNotBlock3d = !ItemUtilKt.isBlock3d(itemStack, itemStackRenderState.usesBlockLight());
            if (AnimatiumConfig.instance().items.itemPositions && isNotBlock3d && !ItemUtilKt.isItemBlacklisted(itemStack)) {
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
            /*final ExtrasConfigCategory extras = AnimatiumConfig.instance().extras;
            if (AnimatiumConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !AnimatiumConfig.instance().items.mobHeadIcons) {
                if (extras.applyCustomizationToBlockItems) {
                    poseStack.translate(extras.itemOffsetX * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * animatium$TRANSLATE_OFFSET_MULTIPLIER);
                }
            *///?}

                //? if <26.3 {
                /*poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
                poseStack.scale(0.4F, 0.4F, 0.4F);
                *///?}

                //? if <26.3 {
                /*// TODO: This is not quite right... (@Mixces)
                poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
                if (!extras.applyCustomizationToBlockItems) {
                    poseStack.translate(0.0F, 0.25F, 0.0F);
                }
                *///?}

            //? if <26.3 {
                /*poseStack.scale(1.125F, 1.125F, 1.125F);
            }
            *///?}

    //? if <26.3 {
            /*if (isNotBlock3d || extras.applyCustomizationToBlockItems) {
                if (AnimatiumConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 && ItemUtilKt.isFishingRodItem(itemStack)) {
                    poseStack.translate(extras.itemOffsetX * -animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * animatium$TRANSLATE_OFFSET_MULTIPLIER);
                } else if (!(AnimatiumConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(itemStack) && !AnimatiumConfig.instance().items.mobHeadIcons)) {
                    poseStack.translate(extras.itemOffsetX * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetY * animatium$TRANSLATE_OFFSET_MULTIPLIER, extras.itemOffsetZ * animatium$TRANSLATE_OFFSET_MULTIPLIER);
                }
                poseStack.scale(extras.itemScaleX, extras.itemScaleY, extras.itemScaleZ);
                poseStack.mulPose(Axis.XP.rotationDegrees(direction * extras.itemRotationX));
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * extras.itemRotationY));
                poseStack.mulPose(Axis.ZP.rotationDegrees(direction * extras.itemRotationZ));
            }
        }
    }
    *///?}

    //? if <26.2 {
    /*@Inject(method = "renderArmWithItem",
    *///?} elif 26.2 {
    /*@Inject(method = "submitArmWithItem",
    *///?}
            //? if <26.3 {
            /*at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 4)
            ))
            *///?}
    //? if 1.21.11 {
    /*private void animatium$itemUsageSwinging(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci, @Local(ordinal = 0) final HumanoidArm arm) {
    *///?} elif >=26.1 <26.3 {
    /*private void animatium$itemUsageSwinging(final AbstractClientPlayer player, final float tickDelta, final float pitch, final InteractionHand hand, final float swingProgress, final ItemStack itemStack, final float equippedProgress, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci, @Local(name = "arm") final HumanoidArm arm) {
    *///?}
    //? if <26.3 {
        /*if (Animatium.isEnabled() && AnimatiumConfig.instance().items.itemUsageSwinging) {
            this.applyItemArmAttackTransform(poseStack, arm, swingProgress);
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
    private boolean animatium$heldItemVisibilityInBoat(final LocalPlayer instance, final Operation<Boolean> original) {
        return (!Animatium.isEnabled() || !AnimatiumConfig.instance().items.heldItemVisibilityInBoat) && original.call(instance);
    }
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getItemSwapScale(F)F"))
    private float animatium$legacySwingAnimation(final LocalPlayer instance, final float delta, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.legacySwingAnimation) {
            return 1.0F;
        } else {
            return original.call(instance, delta);
        }
    }
    *///?}

    // Equip Animation Stuff
    //? if <26.2 {
    /*@ModifyArg(method = "renderHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", ordinal = 0), index = 5)
    *///?} elif 26.2 {
    /*@ModifyArg(method = "submitHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;submitArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", ordinal = 0), index = 5)
    *///?}
    //? if <26.3 {
    /*private ItemStack animatium$useCopyStackFieldForRender(final ItemStack original) {
        // TODO/NOTE: 26.2 makes the item persist in hand even when empty (temp check added)
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.equipAnimationVersion.useStackForRendering() && !original.isEmpty()) {
            // Use our copied stack field for hand animations
            return this.animatium$mainHandItem;
        } else {
            return original;
        }
    }
    *///?}

    //? if <26.3 {
    /*@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;shouldInstantlyReplaceVisibleItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 0))
    private boolean animatium$disableEquipConstraint(final boolean original) {
        return (!Animatium.isEnabled() || !AnimatiumConfig.instance().items.equipAnimationVersion.useStackForRendering()) && original;
    }
    *///?}

    //? if <26.3 {
    /*@Inject(method = "shouldInstantlyReplaceVisibleItem", at = @At("HEAD"), cancellable = true)
    private void animatium$skipEquipAnimation(final ItemStack currentlyVisibleItem, final ItemStack expectedItem, final CallbackInfoReturnable<Boolean> cir) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.equipAnimationVersion == EquipAnimationVersionSetting.DISABLED && this.minecraft.player != null) {
            cir.setReturnValue(true);
        }
    }
    *///?}

    //? if <26.3 {
    /*// Fixes MC-262560
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 2), index = 0)
    *///?}
    //? if 1.21.11 {
    /*private float animatium$handleEquipLogic(final float original, @Local(ordinal = 0) float attackAnim) {
    *///?} elif >=26.1 <26.3 {
    /*private float animatium$handleEquipLogic(final float original, @Local(name = "attackAnim") float attackAnim) {
    *///?}
        //? if <26.3 {
        /*final LocalPlayer player = this.minecraft.player;
        final EquipAnimationVersionSetting setting = AnimatiumConfig.instance().items.equipAnimationVersion;
        if (Animatium.isEnabled() && setting != EquipAnimationVersionSetting.VANILLA && setting != EquipAnimationVersionSetting.DISABLED && player != null) {
            final float scale = (float) Math.pow(attackAnim, 3);
            final ItemStack stackCopy = player.getInventory().getSelectedItem().copy();
        *///?}

            //? if <26.3 {
            /*float mainHandTargetHeight = stackCopy == this.animatium$mainHandItem ? scale : 0;
            if (this.animatium$mainHandItem.isEmpty() && stackCopy.isEmpty()) {
                mainHandTargetHeight = scale;
            }
            *///?}

            //? if <26.3 {
            /*if (!stackCopy.isEmpty() && !this.animatium$mainHandItem.isEmpty() &&
                    stackCopy != this.animatium$mainHandItem && stackCopy.getItem() == this.animatium$mainHandItem.getItem() &&
                    stackCopy.getDamageValue() == this.animatium$mainHandItem.getDamageValue()) {
                this.animatium$mainHandItem = stackCopy;
                mainHandTargetHeight = scale;
            }
            *///?}

            //? if <26.3 {
            /*if (setting == EquipAnimationVersionSetting.V1_7 && this.animatium$currentSlot != player.getInventory().getSelectedSlot()) {
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
    private void animatium$updateFakeItem(final CallbackInfo ci) {
        final LocalPlayer player = this.minecraft.player;
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.VANILLA && AnimatiumConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.DISABLED && player != null && this.mainHandHeight < 0.1F) {
            this.animatium$mainHandItem = this.mainHandItem.copy();
            this.animatium$currentSlot = this.minecraft.player.getInventory().getSelectedSlot();
        }
    }
}
*///?}
