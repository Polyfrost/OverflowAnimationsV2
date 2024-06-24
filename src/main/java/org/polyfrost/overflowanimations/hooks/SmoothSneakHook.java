package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.Minecraft;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

public class SmoothSneakHook {

//    public final SmoothSneakHook INSTANCE = new SmoothSneakHook();

    private static float sneakingHeight;

    public static void setSneakingHeight(float sneakingHeight) {
        SmoothSneakHook.sneakingHeight = sneakingHeight;
    }

    public static float getSneakingHeight() {
        return sneakingHeight;
    }

    public static float getSmoothSneak() {
        if (OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled) {
            return sneakingHeight;
        } else {
            return Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight();
        }
    }

}
