package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.SneakHandler;
import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Unique
    private float partialTicks;

    @Inject(method="orientCamera", at=@At("HEAD"))
    public void orientCamera(float partialTicks, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @ModifyVariable(method="orientCamera", at=@At(value = "STORE", ordinal = 0), ordinal = 1)
    public float modifyEyeHeight(float eyeHeight) {
        return SneakHandler.getInstance().getEyeHeight(partialTicks);
    }

}
