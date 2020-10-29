package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import java.util.ListIterator;

public class EntityRendererTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.RenderManager"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            String methodName = mapMethodName(classNode, method);

            if (methodName.equals("renderDebugBoundingBox") || methodName.equals("func_85094_b")) {
                final ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();

                final LabelNode ifne = new LabelNode();
                while (iterator.hasNext()) {
                    final AbstractInsnNode next = iterator.next();

                    if (next instanceof TypeInsnNode && next.getOpcode() == Opcodes.INSTANCEOF) {
                        method.instructions.insertBefore(next.getPrevious(), checkHitboxSetting(ifne));
                    } else if (next instanceof MethodInsnNode && next.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        final String methodInsnName = mapMethodNameFromNode(next);

                        if (methodInsnName.equals("draw") || methodInsnName.equals("func_78381_a")) {
                            method.instructions.insertBefore(next.getNext(), ifne);
                        }
                    }
                }

                break;
            }
        }
    }

    private InsnList checkHitboxSetting(LabelNode ifne) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "oldDebugHitbox", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFNE, ifne));
        return list;
    }
}
