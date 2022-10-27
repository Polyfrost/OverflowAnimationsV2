package cc.polyfrost.overflowanimations.handlers;

import cc.polyfrost.oneconfigwrapper.OneConfigWrapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModDetectorOneConfigTweaker extends OneConfigWrapper {
    private final boolean alreadyLoaded;
    public ModDetectorOneConfigTweaker() {
        super();
        boolean someWeirdThingForJava = false;
        try {
            Class.forName("cc.polyfrost.oneconfigloader.OneConfigLoader");
            someWeirdThingForJava = true;
        } catch (Exception ignored) {
        }
        alreadyLoaded = someWeirdThingForJava;
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        try {
            Class.forName("club.sk1er.oldanimations.loader.AnimationsTweak");
            File modsFolder = new File(Launch.minecraftHome, "mods");
            File[] modFolder = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));
            if (modFolder != null) {
                final JsonParser PARSER = new JsonParser();
                File oldFile = null;
                for (File file : modFolder) {
                    try {
                        try (ZipFile mod = new ZipFile(file)) {
                            ZipEntry entry = mod.getEntry("mcmod.info");
                            if (entry != null) {
                                try (InputStream inputStream = mod.getInputStream(entry)) {
                                    byte[] availableBytes = new byte[inputStream.available()];
                                    inputStream.read(availableBytes, 0, inputStream.available());
                                    JsonObject modInfo = PARSER.parse(new String(availableBytes)).getAsJsonArray().get(0).getAsJsonObject();
                                    if (!modInfo.has("modid") || !modInfo.has("version")) {
                                        continue;
                                    }

                                    String modid = modInfo.get("modid").getAsString();
                                    if (!modid.equals("sk1er_old_animations")) {
                                        continue;
                                    } else {
                                        oldFile = file;
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (MalformedJsonException | IllegalStateException ignored) {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (oldFile != null) {
                    if (!oldFile.delete()) {
                        oldFile.deleteOnExit();
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        final JFrame frame = new JFrame();
                        frame.setUndecorated(true);
                        frame.setAlwaysOnTop(true);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                        JOptionPane.showMessageDialog(
                                frame,
                                "You have both Sk1er Old Animations and Overflow Animations installed.\n" +
                                        "Please remove Sk1er Old Animations from your mod folder, as OverflowAnimations replaces it.\n" +
                                "It is in " + oldFile.getAbsolutePath(),
                                "Sk1er Old Animations detected!", JOptionPane.ERROR_MESSAGE
                        );
                        try {
                            Method exit = Class.forName("java.lang.Shutdown").getDeclaredMethod("exit", int.class);
                            exit.setAccessible(true);
                            exit.invoke(null, 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                }
            }

        } catch (Exception ignored) {
            // we fine
        }
        if (alreadyLoaded) {
            return;
        }
        super.acceptOptions(args, gameDir, assetsDir, profile);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        if (alreadyLoaded) {
            return;
        }
        super.injectIntoClassLoader(classLoader);
    }
}
