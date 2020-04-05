package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class LayerHeldItemTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.layers.LayerHeldItem"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("doRenderLayer") || methodName.equals("func_177141_a")) {
                LabelNode afterScale = new LabelNode();
                LabelNode veryEnd = new LabelNode();
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                boolean foundFirst = false;
                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();

                    if (node.getOpcode() == Opcodes.INVOKESTATIC) {
                        String nodeName = mapMethodNameFromNode(node);
                        if (!foundFirst && (nodeName.equals("scale") || nodeName.equals("func_179152_a"))) {
                            foundFirst = true;
                            AbstractInsnNode pos = node.getNext().getNext().getNext().getNext();
                            while (true) {
                                if (pos.getNext().getOpcode() == Opcodes.INVOKESTATIC) {
                                    methodNode.instructions.remove(pos.getNext());
                                    break;
                                } else {
                                    methodNode.instructions.remove(pos.getNext());
                                }
                            }
                            methodNode.instructions.insertBefore(pos, callTheHook());
                            methodNode.instructions.remove(pos);
                        }
                    }
                }
            }
        }
    }

    public InsnList callTheHook() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/entity/layers/LayerHeldItem", "field_177206_a", // livingEntityRenderer
                "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;"));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Sk1erOldAnimations.getHookClass(), "doOldTransformations", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/entity/RendererLivingEntity;)V", false));
        return list;
    }
}
