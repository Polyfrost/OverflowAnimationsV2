package org.polyfrost.overflowanimations.mixin.compat;

import io.github.moulberry.notenoughupdates.miscfeatures.ItemCustomizeManager;
import net.minecraft.client.Minecraft;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;

@Pseudo
@Mixin(value = ItemCustomizeManager.class, remap = false)
public class ItemCustomizeManagerMixin {

    @Dynamic("Adds glint compatibility with OverflowAnimations!")
    @Inject(method = "renderEffect", at = @At("HEAD"))
    private static void overflowAnimations$glintCompat(Consumer<Integer> renderModelCallback, int color, CallbackInfo ci) {
        /* help */
    }

//    @Dynamic("Adds glint compatibility with OverflowAnimations!")
//    @ModifyArg(
//            method = "renderEffect",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"
//            ),
//            index = 0
//    )
//    private float overflowAnimations$modifySpeed(float x) {
//        if (OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled) {
//            x *= 64.0F;
//        }
//        return x;
//    }

//    @Dynamic("Adds glint compatibility with OverflowAnimations!")
//    @ModifyArgs(
//            method = "renderEffect",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"
//            )
//    )
//    public void overflowAnimations$modifyScale(Args args) {
//        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return;
//        System.out.println("scaling syssy");
//        for (int i : new int[]{0, 1, 2}) {
//            args.set(i, 1 / (float) args.get(i));
//        }
//        args.setAll();
//    }

}
