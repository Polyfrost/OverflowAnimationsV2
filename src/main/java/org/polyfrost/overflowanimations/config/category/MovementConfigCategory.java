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

import org.jspecify.annotations.NonNull;
import org.polyfrost.overflowanimations.handler.config.bundle.EntryBundle;
import org.polyfrost.overflowanimations.handler.config.category.Category;
import org.polyfrost.overflowanimations.util.enums.CapeMovementSetting;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;
import org.polyfrost.overflowanimations.util.enums.SneakBobbingSetting;
import org.polyfrost.overflowanimations.util.enums.BackwardsWalkingSetting;
import org.polyfrost.overflowanimations.util.enums.DamageTiltSetting;
import org.polyfrost.overflowanimations.util.enums.HeadRotationInterpolationSetting;
import org.polyfrost.overflowanimations.util.enums.SlimArmPositionSetting;
import org.polyfrost.overflowanimations.util.enums.SneakEyeHeightSetting;

public final class MovementConfigCategory extends Category {
    // (Movement) Cape
    public CapeMovementSetting capeMovement = LEGACY ? CapeMovementSetting.V1_12 : CapeMovementSetting.V1_13;
    public boolean disableCapeLean = false;
    public boolean disableCapeSwingRotation = LEGACY;
    public boolean capeChestplateTranslation = LEGACY;
    public boolean capeSneakPosition = LEGACY;
    // (Movement) Other
    public SneakAnimationSetting sneakAnimation = LEGACY ? SneakAnimationSetting.V1_7 : SneakAnimationSetting.VANILLA;
    public boolean longUnsneak = LEGACY;
    public SneakEyeHeightSetting sneakEyeHeight = SneakEyeHeightSetting.VANILLA;
    public BackwardsWalkingSetting backwardsWalking = LEGACY ? BackwardsWalkingSetting.V1_8 : BackwardsWalkingSetting.VANILLA;
    public boolean uncapBlockingHeadRotation = LEGACY;
    public HeadRotationInterpolationSetting headRotationInterpolation = LEGACY ? HeadRotationInterpolationSetting.V1_8 : HeadRotationInterpolationSetting.VANILLA;
    public SneakBobbingSetting sneakBobbing = LEGACY ? SneakBobbingSetting.V1_21_1 : SneakBobbingSetting.VANILLA;
    public boolean deathLimbs = LEGACY;
    public boolean bowArmMovement = LEGACY;
    public DamageTiltSetting damageTilt = LEGACY ? DamageTiltSetting.V1_8 : DamageTiltSetting.VANILLA;
    public boolean offsetHurtTiltTime = LEGACY;
    public SlimArmPositionSetting slimArmPosition = LEGACY ? SlimArmPositionSetting.V1_8 : SlimArmPositionSetting.VANILLA;

    public boolean disableHurtCamera = false;
    public boolean dinnerboneMode = false;
    public boolean dinnerboneModeEntities = false;
    public boolean wavyArms = false;

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "movement");

        bundle.group("other")
                .enumEntry("sneakAnimation", SneakAnimationSetting.class)
                .booleanEntry("longUnsneak")
                .enumEntry("sneakEyeHeight", SneakEyeHeightSetting.class)
                .enumEntry("sneakBobbing", SneakBobbingSetting.class)
                .enumEntry("backwardsWalking", BackwardsWalkingSetting.class)
                .enumEntry("headRotationInterpolation", HeadRotationInterpolationSetting.class)
                .booleanEntry("uncapBlockingHeadRotation")
                .booleanEntry("bowArmMovement")
                .enumEntry("slimArmPosition", SlimArmPositionSetting.class)
                .booleanEntry("deathLimbs");

        bundle.group("modern")
                .enumEntry("damageTilt", DamageTiltSetting.class)
                .booleanEntry("offsetHurtTiltTime")
                .booleanEntry("disableHurtCamera");

        bundle.group("cape")
                .enumEntry("capeMovement", CapeMovementSetting.class)
                .booleanEntry("disableCapeLean")
                .booleanEntry("disableCapeSwingRotation")
                .booleanEntry("capeChestplateTranslation")
                .booleanEntry("capeSneakPosition");

        bundle.group("fun")
                .booleanEntry("dinnerboneMode")
                .booleanEntry("dinnerboneModeEntities")
                .booleanEntry("wavyArms");

        return bundle;
    }
}
