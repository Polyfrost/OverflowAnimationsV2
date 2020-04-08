package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class RenderFishTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.RenderFish"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("doRender") || methodName.equals("func_76986_a")) {
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();

                    if (node.getOpcode() == Opcodes.LDC && ((LdcInsnNode) node).cst.equals(-0.36d)) {
                        LabelNode afterFirst = new LabelNode();
                        LabelNode afterSecond = new LabelNode();
                        AbstractInsnNode startNode = node.getPrevious().getPrevious();
                        AbstractInsnNode endNode = node.getNext().getNext().getNext();
                        methodNode.instructions.insertBefore(startNode, checkAndJump(afterFirst));
                        methodNode.instructions.insert(endNode, makeVec(afterFirst, afterSecond));
                        break;
                    }
                }
                break;
            }
        }
    }

    public InsnList checkAndJump(LabelNode toJump) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "oldRodPosition", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFNE, toJump));
        return list;
    }

    public InsnList makeVec(LabelNode atStart, LabelNode atEnd) {
        InsnList list = new InsnList();
        list.add(new JumpInsnNode(Opcodes.GOTO, atEnd));
        list.add(atStart);
        list.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/util/Vec3"));
        list.add(new InsnNode(Opcodes.DUP));
        list.add(new LdcInsnNode(-0.5D));
        list.add(new LdcInsnNode(0.03D));
        list.add(new LdcInsnNode(0.8D));
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/util/Vec3", "<init>", "(DDD)V", false));
        list.add(atEnd);
        return list;
    }
}
