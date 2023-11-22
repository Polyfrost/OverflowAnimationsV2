package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.SwingHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"))
    private void entityThrow(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (OldAnimationsSettings.itemThrow && OldAnimationsSettings.INSTANCE.enabled && entityIn instanceof EntityThrowable) {
            SwingHook.swingItem();
        }
    }
}