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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
//? if <1.21.9 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
*///?} else {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if <1.21.5 {
/*import org.joml.Quaternionf;
*///?} elif <26.3 {
/*import org.joml.Quaternionfc;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.ItemUtilKt;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;

@Mixin(ItemInHandLayer.class)
public abstract class MixinItemInHandLayer_ThirdPersonItemPositions<S extends ArmedEntityRenderState> {
    //? if <1.21.9 {
    /*@ModifyArgs(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    *///?} else {
    @ModifyArgs(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    //?}
    private void overflowanimations$oldTransformTranslation(
            final Args args,
            //? if <26.1 {
            /*@Local(argsOnly = true, ordinal = 0) final S state,
            @Local(argsOnly = true, ordinal = 0) final ItemStackRenderState item,
            @Local(argsOnly = true, ordinal = 0) final HumanoidArm arm
            *///?} else {
            @Local(argsOnly = true, name = "state") final S state,
            @Local(argsOnly = true, name = "item") final ItemStackRenderState item,
            @Local(argsOnly = true, name = "arm") final HumanoidArm arm
            //?}
    ) {
        final ItemStack stack = state.overflowanimations$getItemHeldByArm(arm);
        if (OverflowAnimations.isEnabled() && ItemUtilKt.shouldApplyItemPositionsInThirdPerson(state, stack, item.usesBlockLight()) && !ItemUtilKt.isItemBlacklisted(stack)) {
            args.setAll((float) args.get(0) * -1.0F, 0.4375F, (float) args.get(2) / 10 * -1.0F);
        }
    }

    //? if <1.21.5 {
    /*@WrapWithCondition(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V"))
    *///?} elif <1.21.9 {
    /*@WrapWithCondition(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V"))
    *///?} elif <26.3 {
    /*@WrapWithCondition(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V"))
    *///?} else {
    @WrapWithCondition(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;rotateDegrees(Lcom/mojang/math/Axis;F)V"))
    //?}
    private boolean overflowanimations$removeTransformMultiply(
            final PoseStack instance,
            //? if <1.21.5 {
            /*final Quaternionf by,
            *///?} elif <26.3 {
            /*final Quaternionfc by,
            *///?}
            //? if <26.1 {
            /*@Local(argsOnly = true, ordinal = 0) final S state,
            @Local(argsOnly = true, ordinal = 0) final ItemStackRenderState item,
            @Local(argsOnly = true, ordinal = 0) final HumanoidArm arm
            *///?} elif >=26.3 {
            final Axis axis,
            final float angle,
            //?}
            //? if >=26.1 {
            @Local(argsOnly = true, name = "state") final S state,
            @Local(argsOnly = true, name = "item") final ItemStackRenderState item,
            @Local(argsOnly = true, name = "arm") final HumanoidArm arm
            //?}
    ) {
        final ItemStack stack = state.overflowanimations$getItemHeldByArm(arm);
        return !OverflowAnimations.isEnabled() || !ItemUtilKt.shouldApplyItemPositionsInThirdPerson(state, stack, item.usesBlockLight()) || ItemUtilKt.isItemBlacklisted(stack);
    }

    //? if <1.21.9 {
    /*@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"))
    private void overflowanimations$itemPositionsThird(final S state, final ItemStackRenderState item, final HumanoidArm arm, final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final CallbackInfo ci) {
    *///?} elif <1.21.11 {
    /*@Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"))
    private void overflowanimations$itemPositionsThird(final S state, final ItemStackRenderState item, final HumanoidArm arm, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci) {
    *///?} else {
    @Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V"))
    private void overflowanimations$itemPositionsThird(final S state, final ItemStackRenderState item, final ItemStack itemStack, final HumanoidArm arm, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled()) {
            final int direction = EntityUtilKt.getArmMultiplier(arm);
            final ItemStack stack = state.overflowanimations$getItemHeldByArm(arm);
            if (!stack.isEmpty() && !ItemUtilKt.isItemBlacklisted(stack)) {
                final boolean isStickRod = OverflowAnimations.isEnabled() &&
                        OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 &&
                        stack.is(Items.FISHING_ROD) &&
                        //? if <1.21.9 {
                        /*(state instanceof PlayerRenderState && state.overflowanimations$isFishing());
                        *///?} else {
                        (state instanceof AvatarRenderState && state.overflowanimations$isFishing());
                        //?}
                final boolean usesBlockLight = item.usesBlockLight();
                if (ItemUtilKt.shouldApplyItemPositionsInThirdPerson(state, stack, usesBlockLight)) {
                    if (ItemUtilKt.isBlock3d(stack, usesBlockLight)) {
                        final float scale = 0.375F;
                        poseStack.translate(0.0F, 0.1875F, -0.3125F);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 45.0F));
                        *///?} else {
                        poseStack.rotate(Axis.XP.rotationDegrees(20.0F));
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * 45.0F));
                        //?}
                        poseStack.scale(-scale, -scale, scale);
                    } else if (stack.is(Items.BOW)) {
                        final float scale = 0.625F;
                        poseStack.translate(direction * 0.0F, 0.125F, 0.3125F);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * -20.0F));
                        *///?} else {
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * -20.0F));
                        //?}
                        poseStack.translate(direction * -0.0625F, 0.0F, 0.0F);
                        poseStack.scale(scale, scale, scale);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        poseStack.mulPose(Axis.XP.rotationDegrees(100.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -145.0F));
                        *///?} else {
                        poseStack.rotate(Axis.XP.rotationDegrees(180));
                        poseStack.rotate(Axis.XP.rotationDegrees(100.0F));
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * -145.0F));
                        //?}
                        poseStack.translate(-0.011765625F, 0.0F, 0.002125F);
                    } else if (ItemUtilKt.isHandheldItem(stack)) {
                        final float scale = 0.625F;
                        if (ItemUtilKt.isFishingRodItem(stack) && !isStickRod) {
                            //? if <26.3 {
                            /*poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 180.0F));
                            *///?} else {
                            poseStack.rotate(Axis.ZP.rotationDegrees(direction * 180.0F));
                            //?}
                            poseStack.translate(0.0F, -0.125F, 0.0F);
                        }

                        if (EntityUtilKt.isBlockingArm(arm, state)) {
                            poseStack.translate(direction * 0.05F, 0.0F, -0.1F);
                            //? if <26.3 {
                            /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * -50.0F));
                            poseStack.mulPose(Axis.XP.rotationDegrees(-10.0F));
                            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -60.0F));
                            *///?} else {
                            poseStack.rotate(Axis.YP.rotationDegrees(direction * -50.0F));
                            poseStack.rotate(Axis.XP.rotationDegrees(-10.0F));
                            poseStack.rotate(Axis.ZP.rotationDegrees(direction * -60.0F));
                            //?}
                        }

                        poseStack.translate(direction * -0.0625F, 0.1875F, 0.0F);
                        poseStack.scale(scale, scale, scale);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        poseStack.mulPose(Axis.XP.rotationDegrees(100));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -145));
                        *///?} else {
                        poseStack.rotate(Axis.XP.rotationDegrees(180));
                        poseStack.rotate(Axis.XP.rotationDegrees(100));
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * -145));
                        //?}
                        poseStack.translate(-0.011765625F, 0.0F, 0.002125F);
                    } else {
                        final float scale = 0.375F;
                        poseStack.translate(direction * 0.25F, 0.1875F, -0.1875F);
                        poseStack.scale(scale, scale, scale);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 60.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 20.0F));
                        *///?} else {
                        poseStack.rotate(Axis.ZP.rotationDegrees(direction * 60.0F));
                        poseStack.rotate(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.rotate(Axis.ZP.rotationDegrees(direction * 20.0F));
                        //?}
                    }

                    if (!ItemUtilKt.isBlock3d(stack, usesBlockLight)) {
                        poseStack.translate(0.0F, -0.3F, 0.0F);
                        poseStack.scale(1.5F, 1.5F, 1.5F);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * 50.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 335.0F));
                        *///?} else {
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * 50.0F));
                        poseStack.rotate(Axis.ZP.rotationDegrees(direction * 335.0F));
                        //?}
                        poseStack.translate(direction * -0.9375F, -0.0625F, 0.0F);

                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * 180.0F));
                        *///?} else {
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * 180.0F));
                        //?}
                        poseStack.translate(direction * -0.5F, 0.5F, 0.03125F);
                    }

                    if (ItemUtilKt.isBlock3d(stack, usesBlockLight)) {
                        poseStack.scale(1 / 0.375F, 1 / 0.375F, 1 / 0.375F);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * -45.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-75.0F));
                        *///?} else {
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * -45.0F));
                        poseStack.rotate(Axis.XP.rotationDegrees(-75.0F));
                        //?}
                        poseStack.translate(0.0F, -2.5F * 0.0625F, 0.0F);
                    } else if (stack.is(Items.BOW)) {
                        poseStack.scale(1 / 0.9F, 1 / 0.9F, 1 / 0.9F);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 40.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -260.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(80.0F));
                        *///?} else {
                        poseStack.rotate(Axis.ZP.rotationDegrees(direction * 40.0F));
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * -260.0F));
                        poseStack.rotate(Axis.XP.rotationDegrees(80.0F));
                        //?}
                        poseStack.translate(direction * 0.0625F, 2.0F * 0.0625F, -2.5F * 0.0625F);
                    } else if (ItemUtilKt.isHandheldItem(stack)) {
                        final boolean isRod = ItemUtilKt.isFishingRodItem(stack) && !isStickRod;
                        poseStack.scale(1 / 0.85F, 1 / 0.85F, 1 / 0.85F);
                        //? if <26.3 {
                        /*poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -55.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 90.0F));
                        *///?} else {
                        poseStack.rotate(Axis.ZP.rotationDegrees(direction * -55.0F));
                        poseStack.rotate(Axis.YP.rotationDegrees(direction * 90.0F));
                        //?}
                        if (isRod) {
                            //? if <26.3 {
                            /*poseStack.mulPose(Axis.YP.rotationDegrees(direction * -180.0F));
                            *///?} else {
                            poseStack.rotate(Axis.YP.rotationDegrees(direction * -180.0F));
                            //?}
                        }

                        poseStack.translate(0.0F, -4.0F * 0.0625F, -0.5F * 0.0625F);
                        if (isRod) {
                            poseStack.translate(0.0F, 0.0F, -2.0F * 0.0625F);
                        }
                    } else {
                        poseStack.scale(1 / 0.55F, 1 / 0.55F, 1 / 0.55F);
                        poseStack.translate(0.0F, -3.0F * 0.0625F, -1.0F * 0.0625F);
                    }
                }
            }
        }
    }
}
