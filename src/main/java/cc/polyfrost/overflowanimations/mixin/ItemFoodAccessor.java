package cc.polyfrost.overflowanimations.mixin;

import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemFood.class)
public interface ItemFoodAccessor {

    @Accessor("alwaysEdible")
    boolean getAlwaysEdible();

}
