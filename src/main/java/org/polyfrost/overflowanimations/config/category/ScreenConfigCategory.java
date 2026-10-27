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
//? if >1.8.9
import org.polyfrost.overflowanimations.util.UtilsKt;
import org.polyfrost.overflowanimations.util.enums.InventoryEffectsSetting;
import org.polyfrost.overflowanimations.util.enums.TabListSetting;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.polyfrost.overflowanimations.util.enums.CameraVersionSetting;
import org.polyfrost.overflowanimations.util.enums.ThirdPersonCrosshairSetting;
import org.polyfrost.overflowanimations.util.enums.HeartFlashingSetting;

public final class ScreenConfigCategory extends Category {
    public CameraVersionSetting cameraVersion = LEGACY ? CameraVersionSetting.V1_8 : CameraVersionSetting.V1_14_4;
    public ThirdPersonCrosshairSetting thirdPersonCrosshair = LEGACY ? ThirdPersonCrosshairSetting.V1_8 : ThirdPersonCrosshairSetting.V1_9;
    public HeartFlashingSetting healthBarFlashing = LEGACY ? HeartFlashingSetting.V1_7 : HeartFlashingSetting.V1_8;
    public boolean centerScrollableListWidgets = LEGACY;
    public boolean listWidgetSelectedBorderColor = LEGACY;
    public boolean legacyWidgetHoverTextColor = LEGACY;
    public boolean disableDebugHudBackground = LEGACY;
    public boolean debugHudTextShadow = LEGACY;
    public boolean disableCameraTransparentPassthrough = LEGACY;
    public boolean tooltipStyleRendering = LEGACY;
    public boolean slotHoverStyleRendering = LEGACY;
    public boolean listBackgroundGradient = LEGACY;
    public InventoryEffectsSetting inventoryEffects = LEGACY ? InventoryEffectsSetting.V1_8 : InventoryEffectsSetting.V1_21_10;
    public boolean panoramaRendering = LEGACY;
    public boolean legacyLoadingScreen = LEGACY;
    public boolean legacyLoadingScreenProgressBar = false;
    public boolean oldChatPosition = LEGACY;
    public boolean oldCrosshairPosition = LEGACY;
    public boolean disconnectServerToTitleScreen = LEGACY;
    public boolean oldCraftingSlotsPosition = false;

    public DebugCrosshairSetting debugCrosshairStyle = DebugCrosshairSetting.VANILLA;
    public TabListSetting tabListStyle = TabListSetting.V1_8;
    public boolean legacyDebugScreen = false;

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "screen");

        bundle.tab("interaction")
                .enumEntry("cameraVersion", CameraVersionSetting.class);

        bundle.tab("hud")
                .enumEntry("healthBarFlashing", HeartFlashingSetting.class)
                .enumEntry("thirdPersonCrosshair", ThirdPersonCrosshairSetting.class)
                .enumEntry("inventoryEffects", InventoryEffectsSetting.class)
                .enumEntry("tabListStyle", TabListSetting.class)
                .enumEntry("debugCrosshairStyle", DebugCrosshairSetting.class);

        bundle.booleanEntry("centerScrollableListWidgets");
        bundle.booleanEntry("listWidgetSelectedBorderColor");
        bundle.booleanEntry("legacyWidgetHoverTextColor");
        bundle.booleanEntry("disableDebugHudBackground");
        bundle.booleanEntry("debugHudTextShadow");
        bundle.booleanEntry("disableCameraTransparentPassthrough");
        bundle.booleanEntry("tooltipStyleRendering");
        bundle.booleanEntry("slotHoverStyleRendering");
        bundle.booleanEntry("listBackgroundGradient");
        bundle.booleanEntry("panoramaRendering");
        bundle.booleanEntry("legacyLoadingScreen");
        bundle.booleanEntry("legacyLoadingScreenProgressBar");
        bundle.booleanEntry("oldChatPosition");
        bundle.booleanEntry("oldCrosshairPosition");
        bundle.booleanEntry("disconnectServerToTitleScreen");
        //? if >1.8.9 {
        bundle.booleanEntry("oldCraftingSlotsPosition", event -> UtilsKt.reinitializeInventorySlots());
        //?} else
        //bundle.booleanEntry("oldCraftingSlotsPosition");

        bundle.group("legacy_hud")
                .booleanEntry("legacyDebugScreen");

        return bundle;
    }
}
