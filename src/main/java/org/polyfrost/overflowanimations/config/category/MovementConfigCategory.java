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

package org.polyfrost.overflowanimations.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.polyfrost.overflowanimations.handler.config.bundle.EntryBundle;
import org.polyfrost.overflowanimations.handler.config.category.Category;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;

public final class MovementConfigCategory extends Category {
    // (Movement) Cape
    public boolean oldCapeMovement = false;
    public boolean disableCapeLean = false;
    public boolean disableCapeSwingRotation = false;
    public boolean capeChestplateTranslation = false;
    public boolean capeSneakPosition = false;
    // (Movement) Other
    public SneakAnimationSetting sneakAnimation = SneakAnimationSetting.VANILLA;
    public boolean longUnsneak = false;
    public boolean fakeOldSneakEyeHeight = false;
    public boolean rotateBackwardsWalking = false;
    public boolean uncapBlockingHeadRotation = false;
    public boolean disableHeadRotationInterpolation = false;
    public boolean handViewBobbingMovement = false;
    public boolean deathLimbs = false;
    public boolean bowArmMovement = false;
    public boolean legacyDamageTilt = false;
    public boolean offsetHurtTiltTime = false;

    public static ConfigCategory create(final MovementConfigCategory defaults, final MovementConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("overflowanimations.category.movement"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "movement");

        bundle.group("cape")
                .booleanEntry("oldCapeMovement")
                .booleanEntry("disableCapeLean")
                .booleanEntry("disableCapeSwingRotation")
                .booleanEntry("capeChestplateTranslation")
                .booleanEntry("capeSneakPosition");

        bundle.group("other")
                .enumEntry("sneakAnimation", SneakAnimationSetting.class)
                .booleanEntry("longUnsneak")
                .booleanEntry("fakeOldSneakEyeHeight")
                .booleanEntry("rotateBackwardsWalking")
                .booleanEntry("uncapBlockingHeadRotation")
                .booleanEntry("disableHeadRotationInterpolation")
                .booleanEntry("handViewBobbingMovement")
                .booleanEntry("deathLimbs")
                .booleanEntry("bowArmMovement")
                .booleanEntry("legacyDamageTilt")
                .booleanEntry("offsetHurtTiltTime");

        return bundle;
    }
}
