package org.polyfrost.overflowanimations.mixin.interfaces;

import net.minecraft.client.renderer.entity.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderItem.class)
public interface RenderItemInvoker {

    @Invoker void invokeSetupGuiTransform(int xPosition, int yPosition, boolean isGui3d);

}
