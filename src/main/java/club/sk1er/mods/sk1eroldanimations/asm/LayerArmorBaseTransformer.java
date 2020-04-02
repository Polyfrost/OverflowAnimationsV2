package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class LayerArmorBaseTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.layers.LayerArmorBase"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("shouldCombineTextures") || methodName.equals("func_177142_b")) {
                methodNode.instructions = new InsnList();
                methodNode.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "redArmor", "Z"));
                methodNode.instructions.add(new InsnNode(Opcodes.IRETURN));
                break;
            }
        }
    }
}
