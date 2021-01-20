package club.sk1er.oldanimations.loader;

import club.sk1er.oldanimations.tweaker.AnimationsTweaker;
import net.modcore.loader.ModCoreSetupTweaker;

public class AnimationsTweak extends ModCoreSetupTweaker {
    public AnimationsTweak() {
        super(new String[]{AnimationsTweaker.class.getName()});
    }
}
