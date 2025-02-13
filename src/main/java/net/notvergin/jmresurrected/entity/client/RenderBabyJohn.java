package net.notvergin.jmresurrected.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.notvergin.jmresurrected.entity.mobs.BabyJohnEntity;
import net.notvergin.jmresurrected.entity.models.ModModelLayers;
import net.notvergin.jmresurrected.entity.models.entity.BabyJohnModel;
import org.jetbrains.annotations.NotNull;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class RenderBabyJohn extends MobRenderer<BabyJohnEntity, BabyJohnModel<BabyJohnEntity>>
{
    private static final ResourceLocation ALPHA = new ResourceLocation(MODID, "textures/entity/alphababyjohn.png");
    private static final ResourceLocation ALPHA_EYES = new ResourceLocation(MODID, "textures/entity/emissive/alphaeyes.png");
    private static final ResourceLocation NORMAL = new ResourceLocation(MODID, "textures/entity/babyjohn.png");

    public RenderBabyJohn(EntityRendererProvider.Context pContext) {
        super(pContext, new BabyJohnModel<>(pContext.bakeLayer(ModModelLayers.BABY_JOHN_LAYER)), 0.2F);
        this.addLayer(new EyesLayer<>(this)
        {
            @Override
            public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull BabyJohnEntity john, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
                if(john.isAlpha())
                    super.render(pPoseStack, pBuffer, pPackedLight, john, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            }

            @Override
            public @NotNull RenderType renderType() {
                return RenderType.eyes(ALPHA_EYES);
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BabyJohnEntity pEntity) {
        return pEntity.isAlpha() ? ALPHA : NORMAL;
    }
}
