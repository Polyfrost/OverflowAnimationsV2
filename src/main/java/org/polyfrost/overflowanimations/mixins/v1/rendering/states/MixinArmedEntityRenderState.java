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

package org.polyfrost.overflowanimations.mixins.v1.rendering.states;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.util.states.UtilityRenderState;

@Mixin(ArmedEntityRenderState.class)
public abstract class MixinArmedEntityRenderState implements UtilityRenderState {
    @Shadow
    public ItemStack leftHandItemStack;

    @Shadow
    public ItemStack rightHandItemStack;

    @Unique
    private boolean overflowanimations$isFishing = false;

    @Unique
    private boolean overflowanimations$isSleeping = false;

    @Unique
    private EntityDimensions overflowanimations$standingDimensions = null;

    @Inject(method = "extractArmedEntityRenderState", at = @At("TAIL"))
    private static void overflowanimations$storeData(final LivingEntity entity, final ArmedEntityRenderState state, final ItemModelResolver itemModelResolver, final float partialTicks, final CallbackInfo ci) {
        if (entity instanceof Player player && player.fishing != null) {
            state.overflowanimations$setFishing();
        }

        if (entity.isSleeping()) {
            state.overflowanimations$setSleeping();
        }

        if (entity instanceof Avatar avatar) {
            state.overflowanimations$setStandingDimensions(avatar.getDefaultDimensions(Pose.STANDING));
        }
    }

    @Override
    public ItemStack overflowanimations$getItemHeldByArm(final HumanoidArm arm) {
        if (arm == HumanoidArm.LEFT) {
            return this.leftHandItemStack;
        } else if (arm == HumanoidArm.RIGHT) {
            return this.rightHandItemStack;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean overflowanimations$isFishing() {
        return overflowanimations$isFishing;
    }

    @Override
    public void overflowanimations$setFishing() {
        overflowanimations$isFishing = true;
    }

    @Override
    public boolean overflowanimations$isSleeping() {
        return overflowanimations$isSleeping;
    }

    @Override
    public void overflowanimations$setSleeping() {
        overflowanimations$isSleeping = true;
    }

    @Override
    public EntityDimensions overflowanimations$getStandingDimensions() {
        return overflowanimations$standingDimensions;
    }

    @Override
    public void overflowanimations$setStandingDimensions(final EntityDimensions entityDimensions) {
        overflowanimations$standingDimensions = entityDimensions;
    }
}
