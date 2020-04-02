package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class GuiIngameForgeTransformer implements ITransformer {

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraftforge.client.GuiIngameForge"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("renderHealth")) {
                int highlightIndex = -1;
                for (LocalVariableNode localVariableNode : methodNode.localVariables) {
                    if (localVariableNode.name.equals("highlight")) {
                        highlightIndex = localVariableNode.index;
                        break;
                    }
                }
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                LabelNode veryEnd = new LabelNode();
                boolean foundFirst = false;
                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();

                    if (node.getOpcode() == Opcodes.INVOKESTATIC) {
                        String nodeName = mapMethodNameFromNode(node);
                        if (!foundFirst && (nodeName.equals("ceiling_float_int") || nodeName.equals("func_76123_f"))) {
                            foundFirst = true;
                            AbstractInsnNode pos = node.getNext().getNext().getNext();
                            methodNode.instructions.insert(pos, jumpIfOldHealth(veryEnd));
                        }
                    } else if (node.getOpcode() == Opcodes.ISTORE && ((VarInsnNode) node).var == highlightIndex) {
                        methodNode.instructions.insertBefore(node, veryEnd);
                    }
                }
                break;
            }
        }
    }

    public InsnList jumpIfOldHealth(LabelNode veryEnd) {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldHealth", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new InsnNode(Opcodes.ICONST_0));
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }
}
