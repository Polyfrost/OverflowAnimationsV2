package org.polyfrost.overflowanimations.mixin.interfaces;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiPlayerTabOverlay.class)
public interface GuiPlayerTabOverlayInvoker {
    @Invoker("drawPing")
    void invokeDrawPing(int i, int j, int k, NetworkPlayerInfo networkPlayerInfoIn);
}
