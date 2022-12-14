package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ModelBiped.class)
public class ModelBipedMixin extends ModelBase {
    @ModifyConstant(method = "setRotationAngles", constant = @Constant(floatValue = -0.5235988F))
    private float cancelRotation(float original) {
        return OldAnimationsSettings.oldSwordBlock3 && OverflowAnimations.oldAnimationsSettings.enabled ? 0 : original;
    }
}
