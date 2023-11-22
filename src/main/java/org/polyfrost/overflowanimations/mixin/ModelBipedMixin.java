package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ModelBiped.class)
public class ModelBipedMixin extends ModelBase {
    @ModifyConstant(method = "setRotationAngles", constant = @Constant(floatValue = -0.5235988F))
    private float cancelRotation(float original) {
        return OldAnimationsSettings.oldSwordBlock3 && OldAnimationsSettings.INSTANCE.enabled ? 0.0f : original;
    }
}
