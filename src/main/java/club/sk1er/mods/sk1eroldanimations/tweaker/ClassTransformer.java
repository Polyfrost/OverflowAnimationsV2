package club.sk1er.mods.sk1eroldanimations.tweaker;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.asm.ItemRendererTransformer;
import club.sk1er.mods.sk1eroldanimations.asm.RenderFishTransformer;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class ClassTransformer implements IClassTransformer {

    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();

    public ClassTransformer() {
    }

    private void registerTransformer(ITransformer transformer) {
        for (String cls : transformer.getClassName()) {
            transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;

        Collection<ITransformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        Sk1erOldAnimations.LOGGER.info("Found {} transformers for {}", transformers.size(), transformedName);

        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);

        transformers.forEach(transformer -> {
            Sk1erOldAnimations.LOGGER.info("Applying transformer {} on {}...", transformer.getClass().getName(), transformedName);
            transformer.transform(node, transformedName);
        });

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        try {
            node.accept(writer);
        } catch (Throwable t) {
            Sk1erOldAnimations.LOGGER.error("Exception when transforming " + transformedName + " : " + t.getClass().getSimpleName());
            t.printStackTrace();
        }
        File bytecodeDirectory = new File("bytecode");
        File bytecodeOutput = new File(bytecodeDirectory, transformedName.replace('$', '.') + ".class"); // inner classes suckkkk

        if (!bytecodeDirectory.exists()) bytecodeDirectory.mkdirs();
        if (!bytecodeOutput.exists()) {
            try {
                bytecodeOutput.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(bytecodeOutput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            os.write(writer.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }
}
