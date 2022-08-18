package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.handlers.GlintHandler;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin_Glint {

    @Inject(method = "renderEffect", at = @At("HEAD"), cancellable = true)
    private void renderOldEffect(IBakedModel model, CallbackInfo ci) {
        if (GlintHandler.renderGlint(((RenderItem) (Object) this), model)) {
            ci.cancel();
        }
    }
}