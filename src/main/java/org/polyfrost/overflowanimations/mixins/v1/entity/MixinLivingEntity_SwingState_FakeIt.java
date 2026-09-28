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

package org.polyfrost.overflowanimations.mixins.v1.entity;

//? if >=26.3 {
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SwingAnimation;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.polyfrost.overflowanimations.util.duck.SwingStateExt;
//?}

//? if >=26.3 {
@Mixin(LivingEntity.SwingState.class)
public abstract class MixinLivingEntity_SwingState_FakeIt implements SwingStateExt {
    @Shadow
    protected abstract void start(final InteractionHand hand, final SwingAnimation animation, final int durationTicks);
//?}

    //? if >=26.3 {
    @Shadow
    private LivingEntity.@Nullable SwingDescription currentSwing;
    //?}

    //? if >=26.3 {
    @Shadow
    private int ticks;
    //?}

//? if >=26.3 {
    @Override
    public void overflowanimations$forceSwing(final @NotNull InteractionHand hand, final @NotNull SwingAnimation animation, final int duration) {
        if (this.overflowanimations$canStartSwing()) {
            this.start(hand, animation, duration);
        }
    }

    @Override
    public boolean overflowanimations$canStartSwing() {
        return this.currentSwing == null || this.ticks > this.currentSwing.durationTicks() / 2 || this.ticks <= 0;
    }
}
//?}
