package net.notvergin.jmresurrected.entity.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.notvergin.jmresurrected.entity.animations.BabyJohnAnimationDefinition;
import net.notvergin.jmresurrected.entity.mobs.BabyJohnEntity;

public class BabyJohnModel<T extends BabyJohnEntity> extends HierarchicalModel<T> {
	private final ModelPart babyjohn;
	private final ModelPart upperbody;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart arms;
	private final ModelPart larm;
	private final ModelPart rarm;
	private final ModelPart legs;
	private final ModelPart lleg;
	private final ModelPart rleg;

	public BabyJohnModel(ModelPart root) {
		this.babyjohn = root.getChild("babyjohn");
		this.upperbody = this.babyjohn.getChild("upperbody");
		this.head = this.upperbody.getChild("head");
		this.body = this.upperbody.getChild("body");
		this.arms = this.upperbody.getChild("arms");
		this.larm = this.arms.getChild("larm");
		this.rarm = this.arms.getChild("rarm");
		this.legs = this.babyjohn.getChild("legs");
		this.lleg = this.legs.getChild("lleg");
		this.rleg = this.legs.getChild("rleg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition babyjohn = partdefinition.addOrReplaceChild("babyjohn", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition upperbody = babyjohn.addOrReplaceChild("upperbody", CubeListBuilder.create(), PartPose.offset(0.75F, -6.0F, 0.0F));

		PartDefinition head = upperbody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition body = upperbody.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-2.45F, -4.0F, -1.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, -1.0F, 0.0F));

		PartDefinition arms = upperbody.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(-0.75F, 6.0F, 0.0F));

		PartDefinition larm = arms.addOrReplaceChild("larm", CubeListBuilder.create().texOffs(14, 8).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.75F, -11.0F, 0.0F));

		PartDefinition rarm = arms.addOrReplaceChild("rarm", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.25F, -11.0F, 0.0F));

		PartDefinition legs = babyjohn.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition lleg = legs.addOrReplaceChild("lleg", CubeListBuilder.create().texOffs(8, 17).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -5.0F, 0.0F));

		PartDefinition rleg = legs.addOrReplaceChild("rleg", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(pNetHeadYaw, pHeadPitch, pAgeInTicks);

		this.animateWalk(BabyJohnAnimationDefinition.animation_walk, pLimbSwing, pLimbSwingAmount, 4.5F, 1.5f);
		this.animate(((BabyJohnEntity)pEntity).jumpAnimationState, BabyJohnAnimationDefinition.animation_jump, pAgeInTicks, 1.0F);
		this.animate(((BabyJohnEntity)pEntity).idleAnimationState, BabyJohnAnimationDefinition.animation_idle, pAgeInTicks, 1.0f);
	}

	private void applyHeadRotation(float netHeadYaw, float netHeadPitch, float ageInTicks)
	{
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		netHeadPitch = Mth.clamp(netHeadPitch, -25.0F, 45.0F);

		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = netHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		babyjohn.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return babyjohn;
	}
}