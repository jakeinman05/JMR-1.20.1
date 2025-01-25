package com.notvergin.jmr.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class JohnChaseGoal extends NearestAttackableTargetGoal<LivingEntity>
{
    private LivingEntity currentTarget;
    public boolean flag = false;

    public JohnChaseGoal(Mob pMob) {
        super(pMob, LivingEntity.class, 10, false, false, null);
    }

    @Override
    public boolean canUse()
    {
        // checks entities in range
        if(currentTarget == null && !flag)
        {
            LivingEntity possibleTarget = this.mob.level().getNearestPlayer(this.mob, 48.0D);
            if(possibleTarget instanceof Player player)
            {
                if(!player.isCreative() && !player.isSpectator() && player.isAlive())
                {
                    currentTarget = player;
                    flag = true;
                    return true;
                }
            }
        }

        return false;
    }
    // keeps chasing
    @Override
    public boolean canContinueToUse()
    {
        if(currentTarget instanceof Player player && flag)
        {
            if(!player.isCreative() && !player.isSpectator() && player.isAlive())
                return currentTarget != null && currentTarget.isAlive();
        }
        return false;
    }
    // sets current target on goal use
    @Override
    public void start()
    {
        super.start();
        if(currentTarget != null)
            this.mob.setTarget(currentTarget);
    }
    // clears current target
    @Override
    public void stop()
    {
        super.stop();
        this.mob.setTarget(null);
        currentTarget = null;
        flag = false;
    }
    // updates nav every tick to keep chasing
    @Override
    public void tick()
    {
        if(currentTarget != null)
        {
            if(!currentTarget.hasLineOfSight(this.mob))
            {
                this.mob.getNavigation().moveTo(currentTarget.getX(), currentTarget.getY(), currentTarget.getZ(), 2.0D);

            }
            else {
                this.mob.getNavigation().moveTo(currentTarget.getX(), currentTarget.getY(), currentTarget.getZ(), 1.0D);
            }
        }
    }
}
