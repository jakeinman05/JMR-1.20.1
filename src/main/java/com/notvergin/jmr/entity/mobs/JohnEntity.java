package com.notvergin.jmr.entity.mobs;

import com.notvergin.jmr.entity.goals.NewRunAtNoSightGoal;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class JohnEntity extends Monster
{
    public JohnEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick()
    {
        super.tick();

        if(this.level().isClientSide())
        {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates()
    {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING)
        {
            f = Math.min(pPartialTick * 6.0F, 1.0F);
        }
        else
        {
            f = 0.0f;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.46D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MAX_HEALTH, 20.0D);
    }

    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5f));
        this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f, true));
        this.targetSelector.addGoal(2, new NewRunAtNoSightGoal(this, 0.46f));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Villager>(this, Villager.class, true));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pDimensions) {
        return 3.75f;
    }
}

