package net.notvergin.jmresurrected.entity.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.notvergin.jmresurrected.entity.animations.JohnAnimationDefinition;
import net.notvergin.jmresurrected.entity.mobs.JohnEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class JohnModel<T extends JohnEntity> extends HierarchicalModel<T> {
	private final ModelPart john;
	private final ModelPart UpperBody;
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart Arms;
	private final ModelPart Larm;
	private final ModelPart Rarm;
	private final ModelPart Legs;
	private final ModelPart Rleg;
	private final ModelPart Lleg;

	public JohnModel(ModelPart root) {
		this.john = root.getChild("john");
		this.UpperBody = this.john.getChild("UpperBody");
		this.Body = this.UpperBody.getChild("Body");
		this.Head = this.UpperBody.getChild("Head");
		this.Arms = this.UpperBody.getChild("Arms");
		this.Larm = this.Arms.getChild("Larm");
		this.Rarm = this.Arms.getChild("Rarm");
		this.Legs = this.john.getChild("Legs");
		this.Rleg = this.Legs.getChild("Rleg");
		this.Lleg = this.Legs.getChild("Lleg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition john = partdefinition.addOrReplaceChild("john", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition UpperBody = john.addOrReplaceChild("UpperBody", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, -1.0F));

		PartDefinition Body = UpperBody.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(71, 33).addBox(-8.0F, -19.0F, -1.5F, 17.0F, 23.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Head = UpperBody.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(-1.0F, -18.0F, 2.0F));

		PartDefinition Head_r1 = Head.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(5, 6).addBox(-8.5937F, -23.1979F, -6.7332F, 20.0F, 24.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4334F, -0.3014F, -0.1472F));

		PartDefinition Arms = UpperBody.addOrReplaceChild("Arms", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 1.0F));

		PartDefinition Larm = Arms.addOrReplaceChild("Larm", CubeListBuilder.create(), PartPose.offset(16.0F, -45.0F, 3.0F));

		PartDefinition Larm_r1 = Larm.addOrReplaceChild("Larm_r1", CubeListBuilder.create().texOffs(96, 0).addBox(-0.2403F, -19.8254F, -4.5254F, 8.0F, 21.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, -3.0F, 1.1493F, 0.7149F, -0.7005F));

		PartDefinition Rarm = Arms.addOrReplaceChild("Rarm", CubeListBuilder.create(), PartPose.offset(-10.0F, -39.0F, -1.0F));

		PartDefinition Rarm_r1 = Rarm.addOrReplaceChild("Rarm_r1", CubeListBuilder.create().texOffs(96, 0).addBox(-8.2286F, -20.8366F, -4.2008F, 8.0F, 21.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 1.5977F, -1.0646F, 0.5979F));

		PartDefinition Legs = john.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Rleg = Legs.addOrReplaceChild("Rleg", CubeListBuilder.create().texOffs(51, 0).addBox(-3.5F, -1.0F, -3.0F, 7.0F, 21.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -20.0F, 1.0F));

		PartDefinition Lleg = Legs.addOrReplaceChild("Lleg", CubeListBuilder.create().texOffs(69, 0).addBox(-3.5F, -1.0F, -3.0F, 7.0F, 21.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -20.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(pNetHeadYaw, pHeadPitch, pAgeInTicks);

		this.animateWalk(JohnAnimationDefinition.animation_walk, pLimbSwing, pLimbSwingAmount, 1.5f, 1.5f);
		this.animate(((JohnEntity)pEntity).idleAnimationState, JohnAnimationDefinition.animation_idle, pAgeInTicks, 1.0f);
	}

	private void applyHeadRotation(float netHeadYaw, float netHeadPitch, float ageInTicks)
	{
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		netHeadPitch = Mth.clamp(netHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.Head.xRot = netHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		john.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return john;
	}
}