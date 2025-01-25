package com.notvergin.jmr.entity.mobs;

import com.notvergin.jmr.customitems.weapons.ImmortalBlade;
import com.notvergin.jmr.entity.goals.JohnChaseGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static com.notvergin.jmr.JohnModResurrected.MODID;

public class JohnEntity extends Monster
{
    public JohnEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    //public boolean hasFleed = false;

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

    // maybe implement brain (use brainLib from one of the entity videos)

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.46D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.5D)
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.54D);
    }

    protected void registerGoals()
    {
        // add runaway goal (regen health aswell)
        // maybe also rage goal (more damage speed and maybe sound)
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(2, new JohnFleeGoal(this, 16.0f, 20));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5f));
        this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f, true));
        this.targetSelector.addGoal(2, new JohnChaseGoal(this).setUnseenMemoryTicks(600));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Villager>(this, Villager.class, false, false));
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel)
    {
        return new GroundPathNavigation(this, pLevel)
        {
            @Override
            protected void followThePath()
            {
                this.mob.setMaxUpStep(1.0F);
                super.followThePath();
            }
        };
    }

    @Override
    public void tick()
    {
        if(this.level().isClientSide())
        {
            setupAnimationStates();
        }

        super.tick();

        this.setMaxUpStep(1.0f);

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount)
    {
        Entity entity = pSource.getEntity();
        if(entity instanceof Player player)
        {
            ItemStack handItem = player.getMainHandItem();
            if(handItem.getItem() instanceof ImmortalBlade)
                pAmount *= 1.6f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
        super.addAdditionalSaveData(pCompound);
        if(this.getTarget() != null)
            pCompound.putUUID("TargetUUID", this.getTarget().getUUID());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
        super.readAdditionalSaveData(pCompound);
        if(pCompound.hasUUID("TargetUUID"))
        {
            UUID targetUUID = pCompound.getUUID("TargetUUID");
            Entity targetEntity = ((ServerLevel)this.level()).getEntity(targetUUID);
            if(targetEntity instanceof LivingEntity entity)
                this.setTarget(entity);
        }
    }

    public static boolean canSpawn(EntityType<JohnEntity> pEntity, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos sPosistion, RandomSource random)
    {
        System.out.println("canSpawn Called");
        if(pLevel instanceof ServerLevel sLevel)
        {
            DifficultyInstance difficulty = sLevel.getCurrentDifficultyAt(sPosistion);
            float clampedDiff = difficulty.getEffectiveDifficulty();

            double k = 1.5;
            double x0 = 2.0;
            // sigmoid
            double spawnChance = 1.0f / (1.0f + Math.exp(-k * (clampedDiff - x0)));
            spawnChance = Math.min(1.0f, Math.max(0.0, spawnChance));


            if(sLevel.isRainingAt(sPosistion) || sLevel.isThundering())
                spawnChance *= 1.5f;

            long worldTime = sLevel.getGameTime() % 24000;
            spawnChance *= worldTime >= 13000 && worldTime <= 23000 ? 1.5f : 1.0f;

            //return random.nextDouble() < spawnChance;
            System.out.println("Spawn work");
            return random.nextDouble() < spawnChance;
        }
        System.out.println("canSpawn Failed Level not on Server");
        return true;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && canSpawnAnywhere(pLevel, this.blockPosition());
    }

    private boolean canSpawnAnywhere(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos.below()).isValidSpawn(level, pos.below(), this.getType());
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.WARDEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WARDEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HORSE_DEATH;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public void checkDespawn()
    {
        if(this.getTarget() == null)
        {
            super.checkDespawn();
        }
    }

    @Override
    protected ResourceLocation getDefaultLootTable()
    {
        return new ResourceLocation(MODID, "entities/john");
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 3.75f;
    }


//    static class JohnFleeGoal extends Goal
//    {
//        Mob mob;
//        LivingEntity currentTarget;
//        // has to be hidden for 2 seconds
//        public int hideCount;
//        // able to run for 15 seconds
//        public int runCount;
//        private BlockPos targetPos;
//        boolean hiding = false;
//        boolean running = false;
//
//        public JohnFleeGoal(PathfinderMob pMob)
//        {
//            this.mob = pMob;
//            this.currentTarget = JohnChaseGoal.currentTarget;
//        }
//
//        @Override
//        public boolean canUse() {
//            if(this.mob.getHealth() < this.mob.getMaxHealth()/3 && currentTarget != null)
//            {
//                if(!hiding || !running)
//                    targetPos = findHidableBlock();
//                return targetPos != null;
//            }
//            return false;
//        }
//
//        @Override
//        public boolean canContinueToUse()
//        {
//            return runCount > 0 && hideCount >=0 && targetPos != null;
//        }
//
//        @Override
//        public void start() {
//            if(targetPos != null)
//            {
//                hiding = false;
//                running = true;
//                runCount = 300;
//                hideCount = 20;
//                this.mob.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0F);
//                System.out.println("StartedHiding");
//            }
//        }
//
//        @Override
//        public void stop() {
//            hiding = false;
//            running = false;
//            mob.getNavigation().stop();
//            JohnChaseGoal.currentTarget = currentTarget;
//        }
//
//        @Override
//        public void tick()
//        {
//            if(!currentTarget.hasLineOfSight(this.mob))
//            {
//                if(!hiding && hideCount >= 0)
//                {
//                    hiding = true;
//                    running = false;
//                    System.out.println("Hiding");
//                    this.mob.getNavigation().stop();
//                    this.mob.heal(1);
//                    hideCount--;
//                }
//                else {
//                    if(hiding)
//                    {
//                        hiding = false;
//                        running = true;
//                        targetPos = findHidableBlock();
//                    }
//                }
//            }
//
//            runCount--;
//            super.tick();
//        }
//
//        private BlockPos findHidableBlock()
//        {
//            BlockPos mobPos = this.mob.blockPosition();
//            Level level = this.mob.level();
//            Vec3 mobVec = Vec3.atCenterOf(mobPos);
//            Vec3 playerVec = Vec3.atCenterOf(currentTarget.blockPosition());
//            // opposite direction of player
//            Vec3 oppositeDir = (playerVec.subtract(mobVec).normalize()).scale(-1);
//            // directional offsets from player
//            int xOffset = (int) (Math.signum(oppositeDir.x) * 16);
//            int zOffset = (int) (Math.signum(oppositeDir.z) * 16);
//
//            BlockPos bestPos = null;
//            double bestDistance = Double.MAX_VALUE;
//
//            for(BlockPos pos : BlockPos.betweenClosed(
//                    mobPos.offset(xOffset-8, -4, zOffset-8),
//                    mobPos.offset(xOffset+8, 4, zOffset+8)))
//            {
//                boolean validHidingSpot = true;
//
//                // check clearance for mob height
//                for(int i=0; i<4; i++)
//                {
//                    BlockState aboveState = level.getBlockState(pos.above(i));
//                    if(!aboveState.isSolidRender(level, pos.above(i)))
//                    {
//                        validHidingSpot = false;
//                        break;
//                    }
//                }
//
//                if(!validHidingSpot)
//                    continue;
//
//
//                Path path = this.mob.getNavigation().createPath(pos, 0);
//                if(path != null && path.canReach())
//                {
//                    Vec3 blockVec = Vec3.atCenterOf(pos);
//                    Vec3 directionToBlock = blockVec.subtract(mobVec).normalize();
//                    // check if in opposite direction
//                    double dotProd = directionToBlock.dot(oppositeDir);
//                    if(dotProd > 0.5)
//                    {
//                        double distance = pos.distSqr(mobPos);
//                        if(distance < bestDistance)
//                        {
//                            bestDistance = distance;
//                            bestPos = pos;
//                        }
//                    }
//                }
//            }
//            return bestPos;
//        }
//    }
}
