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

package org.polyfrost.overflowanimations.mixins.v1.gui.debug;

//? if <1.21.6 {
/*import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.joml.Matrix4fStack;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_LegacyDebugCrosshair {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private Camera mainCamera;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lnet/minecraft/client/Camera;FLorg/joml/Matrix4f;)V", shift = At.Shift.AFTER))
    private void overflowanimations$legacyDebugCrosshair(final DeltaTracker deltaTracker, final CallbackInfo ci) {
        if (!OverflowAnimations.isEnabled() || OverflowAnimationsConfig.instance().screen.debugCrosshairStyle != DebugCrosshairSetting.V1_8) {
            return;
        }
        if (!this.minecraft.getDebugOverlay().showDebugScreen() || this.minecraft.options.hideGui || !this.minecraft.options.getCameraType().isFirstPerson() || this.minecraft.player.isReducedDebugInfo() || this.minecraft.options.reducedDebugInfo().get()) {
            return;
        }
        final Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.translate(0.0F, 0.0F, -1.0F);
        modelViewStack.rotateX(this.mainCamera.getXRot() * Mth.DEG_TO_RAD);
        modelViewStack.rotateY(this.mainCamera.getYRot() * Mth.DEG_TO_RAD);
        modelViewStack.scale(-1.0F, 1.0F, -1.0F);
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(CoreShaders.RENDERTYPE_LINES);
        RenderSystem.lineWidth(1.0F);
        final BufferBuilder builder = RenderSystem.renderThreadTesselator().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        overflowanimations$line(builder, 0.05F, 0.0F, 0.0F, -65536);
        overflowanimations$line(builder, 0.0F, 0.033F, 0.0F, -16711936);
        overflowanimations$line(builder, 0.0F, 0.0F, 0.05F, -16776961);
        BufferUploader.drawWithShader(builder.buildOrThrow());
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        modelViewStack.popMatrix();
    }

    @Unique
    private static void overflowanimations$line(final BufferBuilder builder, final float x, final float y, final float z, final int color) {
        builder.addVertex(0.0F, 0.0F, 0.0F).setColor(color).setNormal(Math.signum(x), Math.signum(y), Math.signum(z));
        builder.addVertex(x, y, z).setColor(color).setNormal(Math.signum(x), Math.signum(y), Math.signum(z));
    }
}
*///?}
