package club.sk1er.oldanimations.mixins;

import club.sk1er.oldanimations.FishingLineHandler;
import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderFish.class, priority = 1001)
public class RenderFishMixin {
    @Redirect(method = "doRender", at = @At(value = "NEW", target = "net/minecraft/util/Vec3", ordinal = 0))
    private Vec3 oldFishingLine(double x, double y, double z) {
        return !OldAnimationsSettings.oldRod ? new Vec3(x, y, z) : FishingLineHandler.getInstance().getOffset();
    }
}
