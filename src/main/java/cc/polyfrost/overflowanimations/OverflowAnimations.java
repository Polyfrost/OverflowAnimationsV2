package cc.polyfrost.overflowanimations;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.overflowanimations.command.OldAnimationsCommand;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.handlers.AnimationHandler;
import cc.polyfrost.overflowanimations.handlers.SneakHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = OverflowAnimations.MODID, name = OverflowAnimations.NAME, version = OverflowAnimations.VERSION)
public class OverflowAnimations {

    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static OldAnimationsSettings oldAnimationsSettings;

    public static ItemStack renderingStack;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        oldAnimationsSettings = new OldAnimationsSettings();

        MinecraftForge.EVENT_BUS.register(AnimationHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(SneakHandler.getInstance());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CommandManager.INSTANCE.registerCommand(OldAnimationsCommand.class);
    }
}
