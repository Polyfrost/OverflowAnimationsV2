package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class ItemRendererTransformer implements ITransformer {

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.ItemRenderer"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("transformFirstPersonItem") || methodName.equals("func_178096_b")) {
                LabelNode veryEnd = new LabelNode();
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), veryEnd);
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), moveIfHoldingRod(veryEnd));
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), moveIfHoldingBow(veryEnd));
            } else if (methodName.equals("renderItemInFirstPerson") || methodName.equals("func_78440_a")) {
                int f1Index = -1;
                int fIndex = -1;
                int abstractclientplayerIndex = -1;
                for (LocalVariableNode variableNode : methodNode.localVariables) {
                    if (variableNode.name.equals("f1")) {
                        f1Index = variableNode.index;
                    }
                    else if (variableNode.name.equals("f")) {
                        fIndex = variableNode.index;
                    }
                    else if (variableNode.name.equals("abstractclientplayer")) {
                        abstractclientplayerIndex = variableNode.index;
                    }
                }
                Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();
                    if (node.getOpcode() == Opcodes.INVOKESPECIAL) {
                        String nodeName = mapMethodNameFromNode(node);
                        if (nodeName.equals("doBowTransformations") || nodeName.equals("func_178098_a")) {
                            LabelNode veryEnd = new LabelNode();
                            AbstractInsnNode start = node;
                            for (int i = 0; i < 9; i++) {
                                start = start.getPrevious();
                            }
                            methodNode.instructions.insertBefore(start, moveIfOldBow(veryEnd, fIndex, f1Index, abstractclientplayerIndex));
                            methodNode.instructions.insert(node, veryEnd);
                        }
                        else if (nodeName.equals("performDrinking") || nodeName.equals("func_178104_a")) {
                            LabelNode veryEnd = new LabelNode();
                            AbstractInsnNode endNode = node;
                            for (int i = 0; i < 6; i++) {
                                endNode = endNode.getNext();
                            }
                            methodNode.instructions.insert(node, moveIfOldEat(veryEnd, fIndex, f1Index));
                            methodNode.instructions.insert(endNode, veryEnd);
                        }
                    }
                }
            }
        }
    }

    public InsnList moveIfHoldingRod(LabelNode veryEnd) {
        LabelNode after = new LabelNode();
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldRodPosition", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78453_b", "Lnet/minecraft/item/ItemStack;"));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/item/Item", "func_150891_b", "(Lnet/minecraft/item/Item;)I", false));
        list.add(new IntInsnNode(Opcodes.SIPUSH, 346));
        list.add(new JumpInsnNode(Opcodes.IF_ICMPNE, after));
        list.add(new LdcInsnNode(0.08f));
        list.add(new LdcInsnNode(-0.027f));
        list.add(new LdcInsnNode(-0.33f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false));
        list.add(new LdcInsnNode(0.93f));
        list.add(new LdcInsnNode(1f));
        list.add(new LdcInsnNode(1f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179152_a", "(FFF)V", false));
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }

    public InsnList moveIfHoldingBow(LabelNode veryEnd) {
        LabelNode after = new LabelNode();
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldBowPosition", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78453_b", "Lnet/minecraft/item/ItemStack;"));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/item/Item", "func_150891_b", "(Lnet/minecraft/item/Item;)I", false));
        list.add(new IntInsnNode(Opcodes.SIPUSH, 261));
        list.add(new JumpInsnNode(Opcodes.IF_ICMPNE, after));
        list.add(new LdcInsnNode(0f));
        list.add(new LdcInsnNode(0.05f));
        list.add(new LdcInsnNode(0.04f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false));
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }

    public InsnList moveIfOldBow(LabelNode veryEnd, int fIndex, int f1Index, int abstractclientplayerIndex) {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldBowPosition", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.FLOAD, fIndex));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178096_b", "(FF)V", false));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.FLOAD, 1));
        list.add(new VarInsnNode(Opcodes.ALOAD, abstractclientplayerIndex));
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178098_a", "(FLnet/minecraft/client/entity/AbstractClientPlayer;)V", false));
        list.add(new LdcInsnNode(0f));
        list.add(new LdcInsnNode(0.1f));
        list.add(new LdcInsnNode(-0.15f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false));
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }

    public InsnList moveIfOldEat(LabelNode veryEnd, int fIndex, int f1Index) {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldEating", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.FLOAD, fIndex));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178096_b", "(FF)V", false));
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }
}
