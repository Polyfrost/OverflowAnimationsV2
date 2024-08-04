package org.polyfrost.overflowanimations.mixin.compat;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.data.Mod;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dulkirmod.config.DulkirConfig")
public class DulkirConfigMixin extends Config {

    @Unique
    @Exclude
    private static final String overflow$USE_OVERFLOW = "Please use OverflowAnimations' Custom Item Positions instead of Dulkir's, as it is more compatible with old animations and has more features. You can find it in the OneConfig Mods menu. You can use the Dulkir export buttons and import it into OverflowAnimations directly. For more info, please join the Polyfrost Discord server: discord.gg/polyfrost.";

    public DulkirConfigMixin(Mod modData, String configFile) {
        super(modData, configFile);
    }

    @Dynamic("DulkirMod")
    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        addDependency("customAnimations", overflow$USE_OVERFLOW, () -> false);
        addDependency("customSize", overflow$USE_OVERFLOW, () -> false);
        addDependency("doesScaleSwing", overflow$USE_OVERFLOW, () -> false);
        addDependency("customX", overflow$USE_OVERFLOW, () -> false);
        addDependency("customY", overflow$USE_OVERFLOW, () -> false);
        addDependency("customZ", overflow$USE_OVERFLOW, () -> false);
        addDependency("customYaw", overflow$USE_OVERFLOW, () -> false);
        addDependency("customPitch", overflow$USE_OVERFLOW, () -> false);
        addDependency("customRoll", overflow$USE_OVERFLOW, () -> false);
        addDependency("customSpeed", overflow$USE_OVERFLOW, () -> false);
        addDependency("ignoreHaste", overflow$USE_OVERFLOW, () -> false);
        addDependency("drinkingSelector", overflow$USE_OVERFLOW, () -> false);
    }
}
