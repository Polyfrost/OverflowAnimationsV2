/**
 * Animatium
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

package org.visuals.legacy.animatium.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.visuals.legacy.animatium.handler.compatibility.ModsKt;
import org.visuals.legacy.animatium.handler.config.bundle.EntryBundle;
import org.visuals.legacy.animatium.handler.config.category.Category;

public final class FixesConfigCategory extends Category {
    public boolean fixSneakingFeetPosition = true;
    public boolean fixMirrorArmSwing = true;
    public boolean fixOffHandUsingPose = true;
    public boolean fixCastLineCheck = true;
    public boolean fixCastLineSwing = true;
    public boolean fixFireballClientsideVisual = true;
    public boolean fixTextStrikethroughStyle = true;
    public boolean fixHighAttackSpeedIndicator = true;
    public boolean fixVerticalBobbingTilt = true;
    public boolean upMinPixelTransparencyLimit = true;
    public boolean fixEquipAnimationOnItemUse = true;
    public boolean fixItemUsageVisualInGUI = true;
    public boolean fixDoubleUsageVisual = true;
    public boolean oldSkyRenderingCheck = true;
    //? if >=26.1 {
    public boolean smoothParticles = true;
    //?}

    public static ConfigCategory create(final FixesConfigCategory defaults, final FixesConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("animatium.category.fixes"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

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
        bundle.booleanEntry("fixVerticalBobbingTilt");
        bundle.booleanEntry("upMinPixelTransparencyLimit");
        bundle.booleanEntry("fixEquipAnimationOnItemUse");
        bundle.booleanEntry("fixItemUsageVisualInGUI");
        bundle.booleanEntry("fixDoubleUsageVisual");
        bundle.booleanEntry("oldSkyRenderingCheck");
        //? if >=26.1 {
        bundle.booleanEntry("smoothParticles");
        //?}

        return bundle;
    }
}
