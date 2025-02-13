package net.notvergin.jmresurrected.entity.client;

import net.notvergin.jmresurrected.entity.mobs.JohnEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.notvergin.jmresurrected.entity.models.entity.JohnModel;
import net.notvergin.jmresurrected.entity.models.JMModelLayers;
import org.jetbrains.annotations.NotNull;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class RenderJohn extends MobRenderer<JohnEntity, JohnModel<JohnEntity>>
{
    public RenderJohn(EntityRendererProvider.Context pContext) {
        super(pContext, new JohnModel<>(pContext.bakeLayer(JMModelLayers.JOHN_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull JohnEntity pEntity)
    {
        return new ResourceLocation(MODID, "textures/entity/john.png");
    }
}
