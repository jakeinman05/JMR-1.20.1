package com.notvergin.jmr.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.notvergin.jmr.entity.mobs.JohnEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class RenderJohn extends MobRenderer<JohnEntity, JohnModel<JohnEntity>>
{
    public RenderJohn(EntityRendererProvider.Context pContext) {
        super(pContext, new JohnModel<>(pContext.bakeLayer(ModModelLayers.JOHN_LAYER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull JohnEntity pEntity)
    {
        return new ResourceLocation(MODID, "textures/entity/john.png");
    }

//    @Override
//    public boolean shouldRender(JohnEntity entity, Frustum frustum, double cameraX, double cameraY, double cameraZ) {
//        // Use the standard super check, but override the render distance based on custom logic
//        boolean shouldRender = super.shouldRender(entity, frustum, cameraX, cameraY, cameraZ);
//
//        // Dynamically adjust render distance using the player's settings
//        int renderDistance = Minecraft.getInstance().options.renderDistance().get();
//        double maxRenderDistance = renderDistance * 16.0;  // Max distance in blocks
//        double distanceSquared = entity.distanceToSqr(cameraX, cameraY, cameraZ);
//
//        // Custom check: distance is less than the squared max render distance
//        if (distanceSquared < maxRenderDistance * maxRenderDistance) {
//            shouldRender = true;
//        }
//
//        return shouldRender;
//    }
}
