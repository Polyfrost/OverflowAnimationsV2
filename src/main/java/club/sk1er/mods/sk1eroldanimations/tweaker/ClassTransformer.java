package club.sk1er.mods.sk1eroldanimations.tweaker;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.asm.*;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class ClassTransformer implements IClassTransformer {

    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();
    public static boolean developmentEnvironment;
    public static final boolean outputBytecode = Boolean.parseBoolean(System.getProperty("debugBytecode", "false"));

    public ClassTransformer() {
        developmentEnvironment = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
        if (developmentEnvironment) {
            registerTransformer(new RenderFishTransformer());
            registerTransformer(new ItemRendererTransformer());
            registerTransformer(new LayerArmorBaseTransformer());
            registerTransformer(new GuiIngameForgeTransformer());
            registerTransformer(new LayerHeldItemTransformer());
            registerTransformer(new ModelBipedTransformer());
        }
        registerTransformer(new EntityPlayerTransformer());
    }

    private void registerTransformer(ITransformer transformer) {
        for (String cls : transformer.getClassName()) {
            transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        return createTransformer(transformedName, bytes, transformerMap, outputBytecode);
    }

    public static byte[] createTransformer(String transformedName, byte[] bytes, Multimap<String, ITransformer> transformerMap, boolean outputBytecode) {
        if (bytes == null) return null;

        Collection<ITransformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        Sk1erOldAnimations.LOGGER.info("Found {} transformers for {}", transformers.size(), transformedName);

        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        for (ITransformer transformer : transformers) {
            Sk1erOldAnimations.LOGGER.info("Applying transformer {} on {}...", transformer.getClass().getName(), transformedName);
            transformer.transform(classNode, transformedName);
        }

        ClassWriter classWriter =
                new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        try {
            classNode.accept(classWriter);
        } catch (Throwable e) {
            Sk1erOldAnimations.LOGGER.error("Exception when transforming " + transformedName + " : " + e.getClass().getSimpleName());
            e.printStackTrace();
        }

        if (outputBytecode) {
            File bytecodeDirectory = new File("bytecode");
            String transformedClassName;

            // anonymous classes
            if (transformedName.contains("$")) {
                transformedClassName = transformedName.replace('$', '.') + ".class";
            } else {
                transformedClassName = transformedName + ".class";
            }

            File bytecodeOutput = new File(bytecodeDirectory, transformedClassName);

            try (FileOutputStream os = new FileOutputStream(bytecodeOutput)) {
                os.write(classWriter.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return classWriter.toByteArray();
    }
}
