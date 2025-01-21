package com.notvergin.jmr.entity.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class ModAnimationDefinition
{
    public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(2.375F).looping()
            .addAnimation("Larm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.7303F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Larm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.7344F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Rarm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.7303F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Rarm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.5477F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("UpperBody", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.426F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition walk = AnimationDefinition.Builder.withLength(0.5449F).looping()
            .addAnimation("Rarm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.8116F, 34.3034F, -382.1106F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1603F, KeyframeAnimations.degreeVec(-42.4777F, -19.7204F, -443.4975F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3526F, KeyframeAnimations.degreeVec(8.3853F, 41.0659F, -319.9736F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5449F, KeyframeAnimations.degreeVec(-32.81F, 34.3F, -382.11F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("Larm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.178F, -32.3612F, -5.9224F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1603F, KeyframeAnimations.degreeVec(-29.1258F, -15.0994F, 64.9448F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3526F, KeyframeAnimations.degreeVec(26.8535F, -13.5705F, -74.3406F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5449F, KeyframeAnimations.degreeVec(3.178F, -32.3612F, -5.9224F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Larm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-6.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.posVec(-6.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1603F, KeyframeAnimations.degreeVec(-14.9918F, -0.3134F, -2.9836F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2244F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2885F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3846F, KeyframeAnimations.degreeVec(-5.9918F, -0.3134F, -2.9836F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5449F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0962F, KeyframeAnimations.posVec(0.0F, 0.9F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2244F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3846F, KeyframeAnimations.posVec(0.0F, 0.9F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Lleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0641F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0962F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1923F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3205F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3526F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4487F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4808F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5128F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Lleg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.4F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0321F, KeyframeAnimations.posVec(0.0F, -1.4F, 3.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0641F, KeyframeAnimations.posVec(0.0F, -0.6F, 5.1F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0962F, KeyframeAnimations.posVec(0.0F, 0.1F, 7.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1282F, KeyframeAnimations.posVec(0.0F, 0.5F, 7.7F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1603F, KeyframeAnimations.posVec(0.0F, 0.7F, 7.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1923F, KeyframeAnimations.posVec(0.0F, -0.2F, 6.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2244F, KeyframeAnimations.posVec(0.0F, -1.2F, 3.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2564F, KeyframeAnimations.posVec(0.0F, -1.2F, 1.8F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2885F, KeyframeAnimations.posVec(0.0F, 1.5F, -2.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3205F, KeyframeAnimations.posVec(0.0F, 2.9F, -4.9F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3526F, KeyframeAnimations.posVec(0.0F, 3.1F, -7.2F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3846F, KeyframeAnimations.posVec(0.0F, 2.7F, -8.9F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 2.1F, -9.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4487F, KeyframeAnimations.posVec(0.0F, -1.2F, -8.8F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4808F, KeyframeAnimations.posVec(0.0F, -1.4F, -5.8F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5128F, KeyframeAnimations.posVec(0.0F, -1.4F, -1.2F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.posVec(0.0F, -1.4F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Rleg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0321F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0641F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0962F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1603F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1923F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2244F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2564F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3205F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3846F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4167F, KeyframeAnimations.degreeVec(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5128F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Rleg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0321F, KeyframeAnimations.posVec(0.0F, 1.5F, -2.4F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0641F, KeyframeAnimations.posVec(0.0F, 2.9F, -4.9F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0962F, KeyframeAnimations.posVec(0.0F, 3.1F, -7.2F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1603F, KeyframeAnimations.posVec(0.0F, 2.4F, -9.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1923F, KeyframeAnimations.posVec(0.0F, 1.5F, -8.8F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2244F, KeyframeAnimations.posVec(0.0F, -1.4F, -5.8F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2564F, KeyframeAnimations.posVec(0.0F, -1.4F, -1.2F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2885F, KeyframeAnimations.posVec(0.0F, -1.4F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3205F, KeyframeAnimations.posVec(0.0F, -0.6F, 5.1F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3526F, KeyframeAnimations.posVec(0.0F, 0.1F, 7.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3846F, KeyframeAnimations.posVec(0.0F, 0.5F, 7.7F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.7F, 7.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4487F, KeyframeAnimations.posVec(0.0F, -0.2F, 6.3F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.4808F, KeyframeAnimations.posVec(0.0F, -1.2F, 3.4F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.5128F, KeyframeAnimations.posVec(0.0F, -1.2F, 1.8F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("UpperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0641F, KeyframeAnimations.degreeVec(-5.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1282F, KeyframeAnimations.degreeVec(-9.4996F, 0.0436F, 3.499F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2564F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.3846F, KeyframeAnimations.degreeVec(-10.9354F, 0.0137F, -1.6082F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("UpperBody", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.0962F, KeyframeAnimations.posVec(0.0F, 1.3F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2564F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3526F, KeyframeAnimations.posVec(0.0F, 1.3F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5449F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
