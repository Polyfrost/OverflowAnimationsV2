package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.Iterator;
import java.util.ListIterator;

@HotReloadable
public class ItemRendererTransformer implements ITransformer {

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.ItemRenderer"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            switch (methodName) {
                case "transformFirstPersonItem":
                case "func_178096_b":
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), doOldTransformations());
                    break;
                case "performDrinking":
                case "func_178104_a":
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), doOldDrinking());
                    break;
                case "renderItemInFirstPerson":
                case "func_78440_a":
                    int f1Index = -1;
                    int fIndex = -1;
                    for (LocalVariableNode variableNode : methodNode.localVariables) {
                        switch (variableNode.name) {
                            case "f1":
                            case "var4":
                                f1Index = variableNode.index;
                                break;
                            case "f":
                            case "var2":
                                fIndex = variableNode.index;
                                break;
                        }
                    }
                    Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                    while (iterator.hasNext()) {
                        AbstractInsnNode node = iterator.next();
                        if (node.getOpcode() == Opcodes.INVOKESPECIAL) {
                            String nodeName = mapMethodNameFromNode(node);
                            switch (nodeName) {
                                case "doBowTransformations":
                                case "func_178098_a": {
                                    LabelNode labelNode = new LabelNode();
                                    AbstractInsnNode start = node;
                                    for (int i = 0; i < 7; i++) {
                                        start = start.getPrevious();
                                    }
                                    methodNode.instructions.insertBefore(start, swingProgressIfNecessary(labelNode, f1Index));
                                    methodNode.instructions.insert(start, labelNode);
                                    methodNode.instructions.insert(node, moveIfOldBow());
                                    break;
                                }
                                case "performDrinking":
                                case "func_178104_a": {
                                    LabelNode veryEnd = new LabelNode();
                                    AbstractInsnNode endNode = node;
                                    for (int i = 0; i < 6; i++) {
                                        endNode = endNode.getNext();
                                    }
                                    methodNode.instructions.insert(node, moveIfOldEat(veryEnd, fIndex, f1Index));
                                    methodNode.instructions.insert(endNode, veryEnd);
                                    break;
                                }
                                case "doBlockTransformations":
                                case "func_178103_d": {
                                    AbstractInsnNode start = node;
                                    for (int i = 0; i < 5; i++) {
                                        start = start.getPrevious();
                                    }
                                    LabelNode labelNode = new LabelNode();
                                    methodNode.instructions.insertBefore(start, blockhitSwingProgressIfNecessary(labelNode, f1Index));
                                    methodNode.instructions.insert(start, labelNode);
                                    methodNode.instructions.insert(node, moveIfBlocking());
                                    break;
                                }
                            }
                        } else if (node.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                            String nodeName = mapMethodNameFromNode(node);
                            if (nodeName.equals("func_71052_bv") || nodeName.equals("getItemInUseCount")) {
                                AbstractInsnNode pos = node.getNext().getNext().getNext().getNext();
                                methodNode.instructions.insertBefore(pos, new MethodInsnNode(Opcodes.INVOKESTATIC, getHookClass(), "swingIfNecessary", "()V", false));
                            }
                        }
                    }
                    break;
                case "updateEquippedItem":
                case "func_78441_a":
                    boolean foundJump = false;
                    LabelNode end = new LabelNode();
                    for (ListIterator<AbstractInsnNode> it = methodNode.instructions.iterator(); it.hasNext(); ) {
                        AbstractInsnNode node = it.next();
                        if (!foundJump && node.getOpcode() == Opcodes.IFNULL && node.getPrevious().getOpcode() == Opcodes.GETFIELD) {
                            FieldInsnNode fieldInsnNode = (FieldInsnNode) node.getPrevious();
                            String fieldName = mapFieldNameFromNode(fieldInsnNode);
                            if (fieldName.equals("itemToRender") || fieldName.equals("field_78453_b")) {
                                foundJump = true;
                                methodNode.instructions.insertBefore(fieldInsnNode.getPrevious(), handleOldItemSwitch(end));
                            }
                        } else if (node instanceof LdcInsnNode && ((LdcInsnNode) node).cst.equals(0.4f)) {
                            methodNode.instructions.insertBefore(node, end);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    private InsnList doOldTransformations() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78453_b", "Lnet/minecraft/item/ItemStack;"));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getHookClass(), "doOldItemTransformation", "(Lnet/minecraft/item/ItemStack;)V", false));
        return list;
    }

    public InsnList doOldDrinking() {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "oldEating", "Z"));
        LabelNode after = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
        list.add(new VarInsnNode(Opcodes.FLOAD, 2));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78453_b", "Lnet/minecraft/item/ItemStack;"));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getHookClass(), "doOldEat", "(Lnet/minecraft/client/entity/AbstractClientPlayer;FLnet/minecraft/item/ItemStack;)V", false));
        list.add(new InsnNode(Opcodes.RETURN));
        list.add(after);
        return list;
    }

    public InsnList swingProgressIfNecessary(LabelNode labelNode, int f1Index) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "punching", "Z"));
        LabelNode after = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new JumpInsnNode(Opcodes.GOTO, labelNode));
        list.add(after);
        return list;
    }

    public InsnList blockhitSwingProgressIfNecessary(LabelNode labelNode, int f1Index) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "oldBlockhitting", "Z"));
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "punching", "Z"));
        list.add(new InsnNode(Opcodes.IOR));
        LabelNode after = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new JumpInsnNode(Opcodes.GOTO, labelNode));
        list.add(after);
        return list;
    }

    public InsnList moveIfOldBow() {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "oldBowPosition", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new LdcInsnNode(0f));
        list.add(new LdcInsnNode(0.1f));
        list.add(new LdcInsnNode(-0.15f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false)); // translate
        list.add(after);
        return list;
    }

    public InsnList moveIfOldEat(LabelNode veryEnd, int fIndex, int f1Index) {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "punching", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.FLOAD, fIndex));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178096_b", "(FF)V", false)); // transformFirstPersonItem
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }

    public InsnList moveIfBlocking() {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "oldBlockhitting", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new LdcInsnNode(-0.3f));
        list.add(new LdcInsnNode(0.1f));
        list.add(new LdcInsnNode(0f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false)); // translate
        list.add(new LdcInsnNode(0.83f));
        list.add(new LdcInsnNode(0.88f));
        list.add(new LdcInsnNode(0.85f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179152_a", "(FFF)V", false)); // scale
        list.add(after);
        return list;
    }

    private InsnList handleOldItemSwitch(LabelNode end) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, getConfigClass(), "itemSwitch", "Z"));
        LabelNode optionIsOff = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, optionIsOff));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
        list.add(new VarInsnNode(Opcodes.ALOAD, 2));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, getHookClass(), "oldUpdateEquippedItem", "(Lnet/minecraft/client/renderer/ItemRenderer;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)Z", false));
        list.add(new VarInsnNode(Opcodes.ISTORE, 3));
        list.add(new JumpInsnNode(Opcodes.GOTO, end));
        list.add(optionIsOff);
        return list;
    }
}
