package club.sk1er.oldanimations;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;

public class FishingLineHandler {

    private static FishingLineHandler INSTANCE = new FishingLineHandler();

    public static FishingLineHandler getInstance() {
        return INSTANCE;
    }

    public Vec3 getOffset() {
        double fov = Minecraft.getMinecraft().gameSettings.fovSetting;
        double decimalFov = fov/110;

        Vec3 fppOffset = new Vec3((-decimalFov + (decimalFov / 2.5) - (decimalFov / 8)) + 0.16, 0, 0.4D);
        return fppOffset;
    }

}
