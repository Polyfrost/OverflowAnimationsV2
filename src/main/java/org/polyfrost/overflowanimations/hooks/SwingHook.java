package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.polyfrost.overflowanimations.mixin.interfaces.EntityLivingBaseInvoker;

public final class SwingHook {
    private SwingHook() {
    }

    public static boolean isNotSwinging(EntityPlayer player) {
        return !player.isSwingInProgress ||
                player.swingProgressInt >= ((EntityLivingBaseInvoker) player).getArmSwingAnimation() / 2 ||
                player.swingProgressInt < 0;
    }

    public static void swingItem() {
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (isNotSwinging(player)) {
            player.swingProgressInt = -1;
            player.isSwingInProgress = true;
        }
    }
}
