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
import org.polyfrost.overflowanimations.handler.compatibility.ModsKt;
import org.polyfrost.overflowanimations.handler.config.bundle.EntryBundle;
import org.polyfrost.overflowanimations.handler.config.category.Category;
import org.polyfrost.overflowanimations.util.enums.UseEquipAnimationSetting;
import org.polyfrost.overflowanimations.util.enums.ViewBobbingTiltSetting;

public final class FixesConfigCategory extends Category {
    public boolean fixSneakingFeetPosition = true;
    public boolean fixMirrorArmSwing = true;
    public boolean fixOffHandUsingPose = true;
    public boolean fixCastLineCheck = true;
    public boolean fixCastLineSwing = true;
    public boolean fixFireballClientsideVisual = true;
    public boolean fixTextStrikethroughStyle = true;
    public boolean fixHighAttackSpeedIndicator = true;
    public ViewBobbingTiltSetting viewBobbingTilt = ViewBobbingTiltSetting.V1_14;
    public boolean upMinPixelTransparencyLimit = true;
    public UseEquipAnimationSetting useEquipAnimation = UseEquipAnimationSetting.V1_8;
    public boolean fixItemUsageVisualInGUI = true;
    public boolean fixDoubleUsageVisual = true;
    public boolean oldSkyRenderingCheck = true;
    //? if >=26.1 {
    public boolean smoothParticles = true;
    //?}

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "fixes");

        bundle.booleanEntry("fixSneakingFeetPosition");
        bundle.booleanEntry("fixMirrorArmSwing");
        bundle.booleanEntry("fixOffHandUsingPose");
        bundle.booleanEntry("fixCastLineCheck");
        bundle.booleanEntry("fixCastLineSwing");
        bundle.booleanEntry("fixFireballClientsideVisual");
        if (!ModsKt.HAS_VFP) {
            bundle.booleanEntry("fixTextStrikethroughStyle");
        }

        bundle.booleanEntry("fixHighAttackSpeedIndicator");
        bundle.enumEntry("viewBobbingTilt", ViewBobbingTiltSetting.class);
        bundle.booleanEntry("upMinPixelTransparencyLimit");
        bundle.enumEntry("useEquipAnimation", UseEquipAnimationSetting.class);
        bundle.booleanEntry("fixItemUsageVisualInGUI");
        bundle.booleanEntry("fixDoubleUsageVisual");
        bundle.booleanEntry("oldSkyRenderingCheck");
        //? if >=26.1 {
        bundle.booleanEntry("smoothParticles");
        //?}

        return bundle;
    }
}
