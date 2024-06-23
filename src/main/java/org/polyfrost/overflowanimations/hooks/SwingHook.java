package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.Minecraft;
import org.polyfrost.overflowanimations.mixin.interfaces.EntityLivingBaseInvoker;

public class SwingHook {

    public static void swingItem() {
        final Minecraft mc = Minecraft.getMinecraft();
        if (!mc.thePlayer.isSwingInProgress ||
                mc.thePlayer.swingProgressInt >= ((EntityLivingBaseInvoker) mc.thePlayer).getArmSwingAnimation() / 2 ||
                mc.thePlayer.swingProgressInt < 0) {
            mc.thePlayer.swingProgressInt = -1;
            mc.thePlayer.isSwingInProgress = true;
        }
    }
}
