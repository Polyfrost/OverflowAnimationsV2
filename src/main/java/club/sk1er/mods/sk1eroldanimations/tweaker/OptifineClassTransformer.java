package club.sk1er.mods.sk1eroldanimations.tweaker;

import club.sk1er.mods.sk1eroldanimations.asm.EntityPlayerTransformer;
import club.sk1er.mods.sk1eroldanimations.asm.ItemRendererTransformer;
import club.sk1er.mods.sk1eroldanimations.asm.RenderFishTransformer;
import club.sk1er.mods.sk1eroldanimations.asm.RendererLivingEntityTransformer;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.launchwrapper.IClassTransformer;

public class OptifineClassTransformer implements IClassTransformer {

    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();

    public OptifineClassTransformer() {
        if (!ClassTransformer.developmentEnvironment) {
            registerTransformer(new RenderFishTransformer());
            registerTransformer(new ItemRendererTransformer());
            registerTransformer(new EntityPlayerTransformer());
            registerTransformer(new RendererLivingEntityTransformer());
        }
    }

    private void registerTransformer(ITransformer transformer) {
        for (String cls : transformer.getClassName()) {
            transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        return ClassTransformer.createTransformer(transformedName, bytes, transformerMap, ClassTransformer.outputBytecode);
    }
}
