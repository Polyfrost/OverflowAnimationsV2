package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.handlers.SneakHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Shadow
    private Minecraft mc;
    @Unique
    private float partialTicks;

    @Inject(method = "orientCamera", at = @At("HEAD"))
    public void orientCamera(float partialTicks, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @ModifyVariable(method = "orientCamera", at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    public float modifyEyeHeight_orientCamera(float eyeHeight) {
        if (mc.getRenderViewEntity() != mc.thePlayer) return eyeHeight;
        return SneakHandler.getInstance().getEyeHeight(partialTicks);
    }

    @Redirect(method = "renderWorldDirections", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float modifyEyeHeight_renderWorldDirections(Entity entity) {
        if (mc.getRenderViewEntity() != mc.thePlayer) return entity.getEyeHeight();
        return SneakHandler.getInstance().getEyeHeight(partialTicks);
    }

}
