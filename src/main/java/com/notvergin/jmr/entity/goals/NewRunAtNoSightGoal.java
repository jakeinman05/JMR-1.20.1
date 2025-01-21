package com.notvergin.jmr.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class NewRunAtNoSightGoal extends NearestAttackableTargetGoal<Player>
{
    public static LivingEntity target;
    public double speedMultiplier;
    public double speed;

    public NewRunAtNoSightGoal(Mob pMob, double pMovementSpeed) {
        super(pMob, Player.class, 10, true, false, livingEntity -> (livingEntity instanceof Player));
        this.speedMultiplier = 1.5;
        this.speed = pMovementSpeed;
    }


    @Override
    public boolean canUse()
    {
        target = this.mob.level().getNearestPlayer(this.mob, 48.0D);
        if(target == null || !(target instanceof Player))
            return false;
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void tick()
    {
        if(target != null)
        {
            if(!target.hasLineOfSight(this.mob))
            {
                this.mob.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), speed * speedMultiplier * 2);
                System.out.println("active");
            }
            else {
                this.mob.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), speed * 2);
                System.out.println("not active");
            }
        }
        super.tick();
    }
}
