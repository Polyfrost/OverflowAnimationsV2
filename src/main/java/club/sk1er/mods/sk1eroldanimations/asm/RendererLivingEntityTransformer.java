package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class RendererLivingEntityTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.RendererLivingEntity"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("renderLayers") || methodName.equals("func_177093_a")) {
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();

                    if (node.getOpcode() == Opcodes.INVOKEINTERFACE) {
                        String nodeName = mapMethodNameFromNode(node);
                        if (nodeName.equals("shouldCombineTextures") || nodeName.equals("func_177142_b")) {
                            LabelNode atEnd = new LabelNode();
                            methodNode.instructions.insertBefore(node.getPrevious(), setIfRedArmour(atEnd));
                            methodNode.instructions.insert(node, atEnd);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }
    public InsnList setIfRedArmour(LabelNode atEnd) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "redArmor", "Z"));
        LabelNode ifeq = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, ifeq));
        list.add(new InsnNode(Opcodes.ICONST_1));
        list.add(new JumpInsnNode(Opcodes.GOTO, atEnd));
        list.add(ifeq);
        return list;
    }
}
