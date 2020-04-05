package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.Sk1erOldAnimations;
import club.sk1er.mods.sk1eroldanimations.config.OldAnimationsSettings;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
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
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), moveIfOldSwing(veryEnd));
            } else if (methodName.equals("renderItemInFirstPerson") || methodName.equals("func_78440_a")) {
                int f1Index = -1;
                int fIndex = -1;
                for (LocalVariableNode variableNode : methodNode.localVariables) {
                    switch (variableNode.name) {
                        case "f1":
                            f1Index = variableNode.index;
                            break;
                        case "f":
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
                                methodNode.instructions.insert(node, moveIfOldBow(labelNode));
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
                                methodNode.instructions.insert(node, moveIfBlocking(fIndex, f1Index));
                                break;
                            }
                        }
                    }
                    else if (node.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        String nodeName = mapMethodNameFromNode(node);
                        if (nodeName.equals("func_71052_bv") || nodeName.equals("getItemInUseCount")) {
                            AbstractInsnNode pos = node.getNext().getNext().getNext().getNext();
                            methodNode.instructions.insertBefore(pos, new MethodInsnNode(Opcodes.INVOKESTATIC, "club/sk1er/mods/sk1eroldanimations/asm/ItemRendererTransformer", "swingIfNecessary", "()V", false));
                        }
                    }
                }
            }
        }
    }

    public InsnList swingProgressIfNecessary(LabelNode labelNode, int f1Index) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "punching", "Z"));
        LabelNode after = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new JumpInsnNode(Opcodes.GOTO, labelNode));
        list.add(after);
        return list;
    }

    public InsnList blockhitSwingProgressIfNecessary(LabelNode labelNode, int f1Index) {
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldBlockhitting", "Z"));
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "punching", "Z"));
        list.add(new InsnNode(Opcodes.IOR));
        LabelNode after = new LabelNode();
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new JumpInsnNode(Opcodes.GOTO, labelNode));
        list.add(after);
        return list;
    }

    public static void swingIfNecessary() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (!OldAnimationsSettings.punching || !Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() || Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        if (!player.isSwingInProgress || player.swingProgressInt >= player.getArmSwingAnimationEnd() / 2 || player.swingProgressInt < 0) {
            player.swingProgressInt = -1;
            player.isSwingInProgress = true;
        }
    }

    public InsnList moveIfHoldingRod(LabelNode veryEnd) {
        LabelNode after = new LabelNode();
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldRodPosition", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78453_b", "Lnet/minecraft/item/ItemStack;")); // itemToRender
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false)); // getItem
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/item/Item", "func_150891_b", "(Lnet/minecraft/item/Item;)I", false)); // getIdFromItem
        list.add(new IntInsnNode(Opcodes.SIPUSH, 346));
        list.add(new JumpInsnNode(Opcodes.IF_ICMPNE, after));
        list.add(new LdcInsnNode(0.08f));
        list.add(new LdcInsnNode(-0.027f));
        list.add(new LdcInsnNode(-0.33f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false)); // translate
        list.add(new LdcInsnNode(0.93f));
        list.add(new LdcInsnNode(1f));
        list.add(new LdcInsnNode(1f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179152_a", "(FFF)V", false)); // scale
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
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78453_b", "Lnet/minecraft/item/ItemStack;")); // itemToRender
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "func_77973_b", "()Lnet/minecraft/item/Item;", false)); // getItem
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/item/Item", "func_150891_b", "(Lnet/minecraft/item/Item;)I", false)); // getIdFromItem
        list.add(new IntInsnNode(Opcodes.SIPUSH, 261));
        list.add(new JumpInsnNode(Opcodes.IF_ICMPNE, after));
        list.add(new LdcInsnNode(0f));
        list.add(new LdcInsnNode(0.05f));
        list.add(new LdcInsnNode(0.04f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false)); // translate
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }

    public InsnList moveIfOldBow(LabelNode labelNode) {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldBowPosition", "Z"));
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
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "punching", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new VarInsnNode(Opcodes.FLOAD, fIndex));
        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178096_b", "(FF)V", false)); // transformFirstPersonItem
        list.add(new JumpInsnNode(Opcodes.GOTO, veryEnd));
        list.add(after);
        return list;
    }

    public InsnList moveIfOldSwing(LabelNode veryEnd) {
        LabelNode after = new LabelNode();
        InsnList list = new InsnList();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldSwing", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78455_a", "Lnet/minecraft/client/Minecraft;")); // mc
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/Minecraft", "field_71439_g", "Lnet/minecraft/client/entity/EntityPlayerSP;")); // thePlayer
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/EntityLivingBase", "field_82175_bq", "Z")); // isSwingInProgress
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78455_a", "Lnet/minecraft/client/Minecraft;")); // mc
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/Minecraft", "field_71439_g", "Lnet/minecraft/client/entity/EntityPlayerSP;")); // thePlayer
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/player/EntityPlayer", "func_71045_bC", "()Lnet/minecraft/item/ItemStack;", false)); // getCurrentEquippedItem
        list.add(new JumpInsnNode(Opcodes.IFNULL, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78455_a", "Lnet/minecraft/client/Minecraft;")); // mc
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/Minecraft", "field_71439_g", "Lnet/minecraft/client/entity/EntityPlayerSP;")); // thePlayer
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/player/EntityPlayer", "func_70632_aY", "()Z", false)); // isBlocking
        list.add(new JumpInsnNode(Opcodes.IFNE, after));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/ItemRenderer", "field_78455_a", "Lnet/minecraft/client/Minecraft;")); // mc
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/Minecraft", "field_71439_g", "Lnet/minecraft/client/entity/EntityPlayerSP;")); // thePlayer
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/Entity", "func_70113_ah", "()Z", false)); // isEating
        list.add(new JumpInsnNode(Opcodes.IFNE, after));
        list.add(new LdcInsnNode(-0.078f));
        list.add(new LdcInsnNode(0.003f));
        list.add(new LdcInsnNode(0.05f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179109_b", "(FFF)V", false)); // translate
        list.add(new LdcInsnNode(0.85f));
        list.add(new LdcInsnNode(0.85f));
        list.add(new LdcInsnNode(0.85f));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/minecraft/client/renderer/GlStateManager", "func_179152_a", "(FFF)V", false)); // scale
        list.add(after);
        return list;
    }

    public InsnList moveIfBlocking(int fIndex, int f1Index) {
        InsnList list = new InsnList();
        LabelNode after = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, Sk1erOldAnimations.getConfigClass(), "oldBlockhitting", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, after));
//        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
//        list.add(new VarInsnNode(Opcodes.FLOAD, fIndex));
//        list.add(new VarInsnNode(Opcodes.FLOAD, f1Index));
//        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178096_b", "(FF)V", false)); // transformFirstPersonItem
//        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
//        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/client/renderer/ItemRenderer", "func_178103_d", "()V", false)); // doBlockTransformations
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
}
