package cc.polyfrost.overflowanimations.mixin;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerControllerMP.class)
public interface PlayerControllerMPInvoker {

    @Accessor("blockHitDelay")
    void setBlockHitDelay(int blockHitDelay);

}
