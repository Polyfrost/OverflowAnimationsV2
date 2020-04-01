package club.sk1er.mods.sk1eroldanimations.tweaker.transformer;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public interface ITransformer {
    String[] getClassName();

    void transform(ClassNode classNode, String name);

    default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, methodNode.name, methodNode.desc);
    }

    default String mapMethodNameFromNode(AbstractInsnNode node) {
        MethodInsnNode methodInsnNode = (MethodInsnNode) node;
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
    }

    default String mapFieldNameFromNode(AbstractInsnNode node) {
        FieldInsnNode methodInsnNode = (FieldInsnNode) node;
        return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
    }

    default String mapMethodDesc(MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
    }
}
