package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class ModelBipedTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.model.ModelBiped"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("setRotationAngles") || methodName.equals("func_78087_a")) {
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();

                    if (node.getOpcode() == Opcodes.LDC && ((LdcInsnNode) node).cst.equals(-0.5235988f)) {
                        LabelNode ifne = new LabelNode();
                        methodNode.instructions.insertBefore(node.getPrevious().getPrevious(), new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldBlocking", "Z"));
                        methodNode.instructions.insertBefore(node.getPrevious().getPrevious(), new JumpInsnNode(Opcodes.IFNE, ifne));
                        methodNode.instructions.insert(node.getNext(), ifne);
                        break;
                    }
                }
                break;
            }
        }
    }
}
