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

public final class MovementConfigCategory extends Category {
    // (Movement) Cape
    public CapeMovementSetting capeMovement = CapeMovementSetting.MODERN;
    public boolean disableCapeLean = false;
    public boolean disableCapeSwingRotation = false;
    public boolean capeChestplateTranslation = false;
    public boolean capeSneakPosition = false;
    // (Movement) Other
    public SneakAnimationSetting sneakAnimation = SneakAnimationSetting.MODERN;
    public boolean longUnsneak = false;
    public boolean longUnsneakLegacyOnly = true;
    public boolean fakeOldSneakEyeHeight = false;
    public boolean rotateBackwardsWalking = false;
    public boolean uncapBlockingHeadRotation = false;
    public boolean disableHeadRotationInterpolation = false;
    public SneakBobbingSetting sneakBobbing = SneakBobbingSetting.VANILLA;
    public boolean deathLimbs = false;
    public boolean bowArmMovement = false;
    public boolean legacyDamageTilt = false;
    public boolean offsetHurtTiltTime = false;

    public boolean modernBackwardsWalking = true;
    public boolean smoothHeadYaw = true;
    public boolean modernViewBobbing = true;
    public boolean directionalDamageTilt = true;
    public boolean modernSneakEyeHeight = false;
    public boolean disableHurtCamera = false;
    public boolean dinnerboneMode = false;
    public boolean dinnerboneModeEntities = false;
    public boolean wavyArms = false;

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "movement");

        bundle.group("cape")
                .enumEntry("capeMovement", CapeMovementSetting.class)
                .booleanEntry("disableCapeLean")
                .booleanEntry("disableCapeSwingRotation")
                .booleanEntry("capeChestplateTranslation")
                .booleanEntry("capeSneakPosition");

        bundle.group("other")
                .enumEntry("sneakAnimation", SneakAnimationSetting.class)
                .booleanEntry("longUnsneak")
                .booleanEntry("longUnsneakLegacyOnly")
                .booleanEntry("fakeOldSneakEyeHeight")
                .booleanEntry("rotateBackwardsWalking")
                .booleanEntry("uncapBlockingHeadRotation")
                .booleanEntry("disableHeadRotationInterpolation")
                .enumEntry("sneakBobbing", SneakBobbingSetting.class)
                .booleanEntry("deathLimbs")
                .booleanEntry("bowArmMovement")
                .booleanEntry("legacyDamageTilt")
                .booleanEntry("offsetHurtTiltTime");

        bundle.group("modern")
                .booleanEntry("modernBackwardsWalking")
                .booleanEntry("smoothHeadYaw")
                .booleanEntry("modernViewBobbing")
                .booleanEntry("directionalDamageTilt")
                .booleanEntry("modernSneakEyeHeight")
                .booleanEntry("disableHurtCamera");

        bundle.group("fun")
                .booleanEntry("dinnerboneMode")
                .booleanEntry("dinnerboneModeEntities")
                .booleanEntry("wavyArms");

        return bundle;
    }
}
