package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {

    @Redirect(method = "moveEntity", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/Entity;noClip:Z"))
    private boolean enableNoClip(Entity instance) {
        if (OldAnimationsSettings.particleNoClip && OldAnimationsSettings.INSTANCE.enabled && instance instanceof EntityFX) {
            return true;
        }
        return instance.noClip;
    }

}
