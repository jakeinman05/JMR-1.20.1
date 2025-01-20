package com.notvergin.jmr.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.notvergin.jmr.entity.mobs.JohnEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class RenderJohn extends MobRenderer<JohnEntity, JohnModel<JohnEntity>>
{
    public RenderJohn(EntityRendererProvider.Context pContext) {
        super(pContext, new JohnModel<>(pContext.bakeLayer(ModModelLayers.JOHN_LAYER)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(JohnEntity pEntity) {
        return new ResourceLocation(MODID + "textures/entity/john.png");
    }

}
