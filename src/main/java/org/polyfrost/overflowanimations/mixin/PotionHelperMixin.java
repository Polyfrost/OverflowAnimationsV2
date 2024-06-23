package org.polyfrost.overflowanimations.mixin;

import net.minecraft.potion.*;
import net.minecraft.util.IntegerCache;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.PotionColors;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

import static net.minecraft.potion.PotionHelper.calcPotionLiquidColor;
import static net.minecraft.potion.PotionHelper.getPotionEffects;

@Mixin(value = PotionHelper.class)
public class PotionHelperMixin {

    @Shadow @Final private static Map<Integer, Integer> DATAVALUE_COLORS;

    @Redirect(
            method = "calcPotionLiquidColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/potion/Potion;getLiquidColor()I"

            ))
    private static int overflowAnimations$recolorPotions(Potion instance, Collection<PotionEffect> collection) {
        if (MainModSettings.INSTANCE.getOldSettings().getModernPotColors() && MainModSettings.INSTANCE.getOldSettings().enabled) {
            for (PotionEffect potionEffect : collection) {
                return PotionColors.POTION_COLORS.get(potionEffect.getPotionID());
            }
        }
        return instance.getLiquidColor();
    }

    @Inject(
            method = "getLiquidColor",
            at = @At(
                    value = "HEAD"
            )
    )
    private static void overflowAnimations$checkColor(int dataValue, boolean bypassCache, CallbackInfoReturnable<Integer> cir) {
        if (PotionColors.shouldReload) {
            PotionColors.shouldReload = false;
            DATAVALUE_COLORS.clear();
            for (int i : PotionColors.POTION_COLORS.values()) {
                int color = calcPotionLiquidColor(getPotionEffects(i, false));
                Integer integer = IntegerCache.getInteger(i);
                DATAVALUE_COLORS.put(integer, color);
            }
        }
    }

}
