package org.polyfrost.overflowanimations;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.RenderEvent;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import dulkirmod.config.Config;
import dulkirmod.config.DulkirConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.polyfrost.overflowanimations.command.OldAnimationsCommand;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.gui.PleaseMigrateDulkirModGui;
import org.polyfrost.overflowanimations.init.CustomModelBakery;

@Mod(modid = OverflowAnimations.MODID, name = OverflowAnimations.NAME, version = OverflowAnimations.VERSION)
public class OverflowAnimations {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    public static boolean isPatcherPresent = false;
    private static boolean doTheFunnyDulkirThing = false;
    public static boolean oldDulkirMod = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CustomModelBakery.Companion.preload();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        OldAnimationsSettings.INSTANCE.preload();
        CommandManager.INSTANCE.registerCommand(new OldAnimationsCommand());
        EventManager.INSTANCE.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("dulkirmod")) {
            doTheFunnyDulkirThing = true;
        }
        isPatcherPresent = Loader.isModLoaded("patcher");
    }

    @Subscribe
    private void onTick(RenderEvent event) {
        if (event.stage == Stage.START && Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null && doTheFunnyDulkirThing && !OldAnimationsSettings.didTheFunnyDulkirThingElectricBoogaloo) {
            try {
                Class.forName("dulkirmod.config.DulkirConfig");
                if (DulkirConfig.INSTANCE.getCustomAnimations()) {
                    dulkirTrollage();
                }
            } catch (ClassNotFoundException e) {
                oldDulkirMod = true;
                if (Config.INSTANCE.getCustomAnimations()) {
                    dulkirTrollage();
                }
            }
        }
    }

    private void dulkirTrollage() {
        GuiUtils.displayScreen(new PleaseMigrateDulkirModGui());
        doTheFunnyDulkirThing = false;
        OldAnimationsSettings.didTheFunnyDulkirThingElectricBoogaloo = true;
    }

}
