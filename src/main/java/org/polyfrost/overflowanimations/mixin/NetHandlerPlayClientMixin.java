package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.network.NetHandlerPlayClient;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {

    //todo: figure out something better idk, EntityXPOrbMixin

    @ModifyConstant(
            method = "handleSpawnExperienceOrb",
            constant = @Constant(
                    doubleValue = 32.0D

            )
    )
    private double overflowAnimations$oldXPOrbs(double original) {
        if (MainModSettings.INSTANCE.getOldSettings().getOldXPOrbs() && MainModSettings.INSTANCE.getOldSettings().enabled) {
            return original / 32.0D;
        }
        return original;
    }

}
