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

package org.polyfrost.overflowanimations.mixins.v1.entity.xp_orb;

//? if >=1.21.5 {
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
//?}
//? if >=1.21.9 <26.3 {
/*import java.util.Optional;
*///?} elif >=26.3 {
import net.minecraft.world.entity.PositionPath;
//?}

//? if >=1.21.5 {
@Mixin(Entity.class)
public abstract class MixinEntity_XpOrbPosition {
    //? if <1.21.9 {
    /*@Inject(method = "moveOrInterpolateTo(Lnet/minecraft/world/phys/Vec3;FF)V", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$snapLegacyXpOrb(final Vec3 position, final float yRot, final float xRot, final CallbackInfo ci) {
    *///?} elif <26.3 {
    /*@Inject(method = "moveOrInterpolateTo(Ljava/util/Optional;Ljava/util/Optional;Ljava/util/Optional;)V", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$snapLegacyXpOrb(final Optional<Vec3> target, final Optional<Float> yRot, final Optional<Float> xRot, final CallbackInfo ci) {
        final Vec3 position = target.orElse(null);
    *///?} else {
    @Inject(method = "moveOrInterpolateTo(Lnet/minecraft/world/entity/PositionPath;FFZ)V", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$snapLegacyXpOrb(final PositionPath path, final float yRot, final float xRot, final boolean hasRotation, final CallbackInfo ci) {
        final Vec3 position = path == null ? null : path.endPosition();
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.xpOrbPosition && position != null && (Object) this instanceof ExperienceOrb orb && orb.position().distanceToSqr(position) > 4096.0) {
            orb.snapTo(position);
            ci.cancel();
        }
    }
}
//?}
