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

package org.polyfrost.overflowanimations.mixins.v1.entity.fishing;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
//? if >=1.21.6 <1.21.11 {
/*import net.minecraft.client.renderer.rendertype.RenderType;
import org.polyfrost.overflowanimations.handler.rendering.pipeline.OverflowAnimationsRenderTypes;
*///?}
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.mixins.accessor.CameraAccessor;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;

@Mixin(FishingHookRenderer.class)
public abstract class MixinFishingHookRenderer extends EntityRenderer<FishingHook, FishingHookRenderState> {
    protected MixinFishingHookRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @ModifyArgs(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera$NearPlane;getPointOnPlane(FF)Lnet/minecraft/world/phys/Vec3;"))
    private void overflowanimations$moveCastLineY(final Args args) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.fishingRodVersion != FishingRodVersionSetting.VANILLA) {
            final FishingRodVersionSetting version = OverflowAnimationsConfig.instance().items.fishingRodVersion;
            if (version == FishingRodVersionSetting.V1_8) {
                overflowanimations$modifyPlanarScale(args, 0);
            }

            if (version.ordinal() <= FishingRodVersionSetting.V1_8.ordinal()) {
                overflowanimations$modifyPlanarScale(args, 1);
            }
        }
    }

    // Before 1.21.11 the line width is part of the render type; older than 1.21.6 is not supported
    //? if >=1.21.6 <1.21.9 {
    /*@WrapOperation(method = "render(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderType;lineStrip()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType overflowanimations$fishingRodLineThickness(final Operation<RenderType> original) {
        if (OverflowAnimationsConfig.instance().items.thinFishingRodLineThickness) {
            return OverflowAnimationsRenderTypes.FISHING_LINE_THIN;
        } else if (OverflowAnimationsConfig.instance().items.fishingRodVersion.ordinal() <= FishingRodVersionSetting.V1_13.ordinal()) {
            return OverflowAnimationsRenderTypes.FISHING_LINE_LEGACY;
        } else {
            return original.call();
        }
    }
    *///?} elif >=1.21.9 <1.21.11 {
    /*@WrapOperation(method = "submit(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderType;lines()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType overflowanimations$fishingRodLineThickness(final Operation<RenderType> original) {
        if (OverflowAnimationsConfig.instance().items.thinFishingRodLineThickness) {
            return OverflowAnimationsRenderTypes.FISHING_LINE_THIN;
        } else if (OverflowAnimationsConfig.instance().items.fishingRodVersion.ordinal() <= FishingRodVersionSetting.V1_13.ordinal()) {
            return OverflowAnimationsRenderTypes.FISHING_LINE_LEGACY;
        } else {
            return original.call();
        }
    }
    *///?} elif >=1.21.11 <26.1 {
    /*@ModifyArg(method = "method_72983", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;stringVertex(FFFLcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFF)V"), index = 7)
    *///?} elif >=26.1 {
    @ModifyArg(method = "lambda$submit$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;stringVertex(FFFLcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;FFF)V"), index = 7)
    //?}
    //? if >=1.21.11 {
    private static float overflowanimations$fishingRodLineThickness(final float lineWidth) {
        if (OverflowAnimationsConfig.instance().items.thinFishingRodLineThickness) {
            return 1.0F;
        } else if (OverflowAnimationsConfig.instance().items.fishingRodVersion.ordinal() <= FishingRodVersionSetting.V1_13.ordinal()) {
            return 2.0F;
        } else {
            return lineWidth;
        }
    }
    //?}


    @ModifyExpressionValue(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 overflowanimations$customRodLine(final Vec3 original, @Local(argsOnly = true) final Player player, @Local(argsOnly = true, ordinal = 1) final float partialTicks, @Local final int invert) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        if (OverflowAnimations.isEnabled() && items.customRodLine) {
            return original.add(new Vec3(items.rodLinePositionX * invert, items.rodLinePositionY, items.rodLinePositionZ).xRot(-player.getViewXRot(partialTicks) * Mth.DEG_TO_RAD).yRot(-player.getViewYRot(partialTicks) * Mth.DEG_TO_RAD));
        } else {
            return original;
        }
    }

    @WrapOperation(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 overflowanimations$fishingRodLineInterpolation(final Player instance, final float tickDelta, final Operation<Vec3> original) {
        final Vec3 originalPos = original.call(instance, tickDelta);
        if (OverflowAnimations.isEnabled()) {
            CameraAccessor cameraAccessor = (CameraAccessor) entityRenderDispatcher.camera;
            float eyeHeight;
            if (OverflowAnimationsConfig.instance().items.fishingRodVersion.ordinal() <= FishingRodVersionSetting.V1_13.ordinal()) {
                eyeHeight = Mth.lerp(tickDelta, cameraAccessor.overflowanimations$getOldEyeHeight(), cameraAccessor.overflowanimations$getEyeHeight());
            } else if (OverflowAnimationsConfig.instance().movement.fakeOldSneakEyeHeight) {
                // Non-lerped eyeheight trick
                eyeHeight = cameraAccessor.overflowanimations$getEyeHeight();
            } else {
                return originalPos;
            }

            return EntityUtilKt.getPosWithEyeHeight(instance, tickDelta, eyeHeight);
        } else {
            return originalPos;
        }
    }

    @ModifyExpressionValue(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isCrouching()Z"))
    private boolean overflowanimations$noMoveFishingRodLine(final boolean original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7) {
            return false;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "getPlayerHandPos", at = @At(value = "CONSTANT", args = "doubleValue=0.8"))
    private double overflowanimations$fishingRodLinePositionThirdPerson(final double original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7) {
            return original + 0.05;
        } else {
            return original;
        }
    }

    @WrapOperation(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;getHoldingArm(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/HumanoidArm;"))
    private HumanoidArm overflowanimations$fixCastLineCheck(final Player owner, final Operation<HumanoidArm> original) {
        final HumanoidArm value = original.call(owner);
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.fixCastLineCheck && value != owner.getMainArm() && !(owner.getOffhandItem().getItem() instanceof FishingRodItem)) {
            return value.getOpposite();
        } else {
            return value;
        }
    }

    @ModifyArg(method = "extractRenderState(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;getPlayerHandPos(Lnet/minecraft/world/entity/player/Player;FF)Lnet/minecraft/world/phys/Vec3;"), index = 1)
    private float overflowanimations$fixCastLineSwing(final float original, @Local(argsOnly = true, ordinal = 0) final FishingHook entity) {
        final Player player = entity.getPlayerOwner();
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.fixCastLineSwing && player != null) {
            return original * EntityUtilKt.getHandMultiplier(player);
        } else {
            return original;
        }
    }

    @Unique
    private void overflowanimations$modifyPlanarScale(final Args args, final int ordinal) {
        args.set(ordinal, ((float) args.get(ordinal)) + 0.15F);
    }
}
