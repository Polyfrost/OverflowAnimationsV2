package club.sk1er.mods.sk1eroldanimations.asm;

import club.sk1er.mods.sk1eroldanimations.config.OldAnimationsSettings;
import club.sk1er.mods.sk1eroldanimations.tweaker.transformer.ITransformer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
                                }
                                else {
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
    public static void doOldTransformations(EntityLivingBase entityLivingBase, RendererLivingEntity<?> livingEntityRenderer) {
        if (entityLivingBase instanceof EntityPlayer) {
            if (OldAnimationsSettings.oldBlocking) {
                if (((EntityPlayer) entityLivingBase).isBlocking()) {
                    if (entityLivingBase.isSneaking()) {
                        ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0325f);
                        GlStateManager.scale(1.05f, 1.05f, 1.05f);
                        GlStateManager.translate(-0.58f, 0.32f, -0.07f);
                        GlStateManager
                                .rotate(-24405.0f, 137290.0f, -2009900.0f, -2654900.0f);
                    } else {
                        ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0325f);
                        GlStateManager.scale(1.05f, 1.05f, 1.05f);
                        GlStateManager.translate(-0.45f, 0.25f, -0.07f);
                        GlStateManager
                                .rotate(-24405.0f, 137290.0f, -2009900.0f, -2654900.0f);
                    }
                } else {
                    ((ModelBiped) livingEntityRenderer.getMainModel())
                            .postRenderArm(0.0625f);
                }
            } else {
                ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
            }

            if (!OldAnimationsSettings.oldItemHeld) {
                GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
            } else {
                if (!((EntityPlayer) entityLivingBase).isBlocking()) {
                    if (OldAnimationsSettings.oldItemHeld) {
                        GlStateManager.translate(-0.0855f, 0.4775f, 0.1585f);
                        GlStateManager.rotate(-19.0f, 20.0f, 0.0f, -6.0f);
                        return;
                    }
                }

                if (((EntityPlayer) entityLivingBase).isBlocking()) {
                    GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
                }
            }
        } else {
            ((ModelBiped) livingEntityRenderer.getMainModel()).postRenderArm(0.0625f);
            GlStateManager.translate(-0.0625f, 0.4375f, 0.0625f);
        }
    }
    public InsnList callTheHook() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 1));
        list.add(new VarInsnNode(Opcodes.ALOAD, 0));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/entity/layers/LayerHeldItem", "field_177206_a", // livingEntityRenderer
        "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;"));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "club/sk1er/mods/sk1eroldanimations/asm/LayerHeldItemTransformer", "doOldTransformations", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/entity/RendererLivingEntity;)V", false));
        return list;
    }
}
