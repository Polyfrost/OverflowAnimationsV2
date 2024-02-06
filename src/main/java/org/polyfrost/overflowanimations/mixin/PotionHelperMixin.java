package org.polyfrost.overflowanimations.mixin;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.PotionColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Mixin(value = PotionHelper.class)
public class PotionHelperMixin {

    @Redirect(method = "calcPotionLiquidColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/Potion;getLiquidColor()I"))
    private static int recolorPotions(Potion instance, Collection<PotionEffect> collection) {
        if (OldAnimationsSettings.potionGlint && OldAnimationsSettings.INSTANCE.enabled) {
            for (PotionEffect potionEffect : collection) {
                return PotionColors.POTION_COLORS.get(potionEffect.getPotionID());
            }
        }
        return instance.getLiquidColor();
    }

}