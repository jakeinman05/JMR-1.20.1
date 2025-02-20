package net.notvergin.jmresurrected.entity.livingentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.notvergin.jmresurrected.items.JMItems;
import net.notvergin.jmresurrected.items.weapons.ImmortalBlade;
import net.notvergin.jmresurrected.entity.JMEntites;
import net.notvergin.jmresurrected.sound.JMSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class BabyJohnEntity extends Monster
{
    private static final EntityDataAccessor<Boolean> ALPHA = SynchedEntityData.defineId(BabyJohnEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(BabyJohnEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.defineId(BabyJohnEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SUMMONED_ALLIES = SynchedEntityData.defineId(BabyJohnEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private boolean hasAlphaAttributes = false;
    private boolean hasAlpha = false;
    private BabyJohnEntity alpha = null;
    private static double alphaSummonCount = 0;

    private boolean hasFleeingAttributes = false;
    private int fleeTicks = 0;
    private int fleeTimeoutTicks = 0;
    private boolean wantsToFlee = false;
    private LivingEntity avoidEntity;
    private boolean wantsToJump = false;

    private int attackTicks = 0;
    private final double defaultDamage = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

    public BabyJohnEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ALPHA, false);
        this.entityData.define(LEAPING, false);
        this.entityData.define(FLEEING, false);
        this.entityData.define(SUMMONED_ALLIES, false);
    }



    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setAlpha(pCompound.getBoolean("Alpha"));
        this.setLeaping(pCompound.getBoolean("Leaping"));
        this.setFleeing(pCompound.getBoolean("Fleeing"));
        this.setSummonedAllies(pCompound.getBoolean("SummonedAllies"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Alpha", this.isAlpha());
        pCompound.putBoolean("Leaping", this.isLeaping());
        pCompound.putBoolean("Fleeing", this.isFleeing());
        pCompound.putBoolean("SummonedAllies", this.hasSummonedAllies());
    }

    private void setupAnimationStates()
    {
        if(this.isLeaping())
            this.jumpAnimationState.start(this.tickCount);
        else
            this.jumpAnimationState.stop();

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
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6.0F, 1.0F);
        }
        else {
            f = 0.0f;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    public static AttributeSupplier createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BabyJohnMeleeGoal(this));
        this.goalSelector.addGoal(2, new BabyJohnFollowAlphaGoal(this, 2.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this, BabyJohnEntity.class).setAlertOthers());
    }

    @Override
    public void tick()
    {
        if(this.level().isClientSide)
            setupAnimationStates();

        super.tick();

        if(this.attackTicks > 0) {
            --attackTicks;
        }

        if(this.isAlpha() && !this.hasSummonedAllies()) {
            summonGroup(this, alphaSummonCount);
            setSummonedAllies(true);
        }

        if(this.tickCount % 60 == 0) {
            if(!this.hasAlpha && this.alpha == null) {
                List<BabyJohnEntity> near = this.level().getNearbyEntities(BabyJohnEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(10.0F));
                for(BabyJohnEntity nearby : near)
                {
                    if(nearby.isAlpha()) {
                        this.alpha = nearby;
                        this.hasAlpha = true;
                        break;
                    }
                }
            }
        }

        if(this.hasAlpha && !this.alpha.isAlive()) {
            this.alpha = null;
            this.hasAlpha = false;
        }

        if(this.isAlpha() && !this.hasAlphaAttributes) {
            this.hasAlphaAttributes = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.5D);
            this.heal(34.0F);
        }
        if(!this.isAlpha() && this.hasAlphaAttributes) {
            this.hasAlphaAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45D);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5D);
            this.heal(24.0F);
        }

        if(this.isFleeing() && !hasFleeingAttributes) {
            this.hasFleeingAttributes = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.55D);
        }

        if(!this.isFleeing() && hasFleeingAttributes) {
            this.hasFleeingAttributes = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        }


        if(this.isLeaping())
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(defaultDamage * 1.2D);
        else
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.isAlpha() ? 3.5D : this.defaultDamage);

        if(!this.level().isClientSide) {
            if(fleeTicks > 0) {
                --fleeTicks;
            }

        }

        if(!wantsToFlee && this.fleeTimeoutTicks == 0 && !this.isAlpha() && !this.hasAlpha && this.getHealth() < this.getMaxHealth() * 0.4) {
            this.wantsToFlee = true;
            setFleeing(true);

            int i = 100 + this.random.nextInt(20);
            this.fleeTicks = i;
            this.avoidEntity = this.getLastHurtByMob();
        }

        if(!this.level().isClientSide && isFleeing() && !(this.fleeTimeoutTicks > 0) && (this.fleeTicks > 0 || this.distanceToSqr(this.avoidEntity.position()) < 32.0) && (this.avoidEntity instanceof Player player && !player.isCreative())) {
            fleeFromTargetPosition(this.avoidEntity);
        }

        if(isFleeing() && fleeTicks == 0) {
            if(this.avoidEntity != null && (!this.avoidEntity.hasLineOfSight(this) || this.distanceToSqr(this.avoidEntity) > 50.0D))
                this.heal(10.0F);
            setFleeing(false);
            this.fleeTimeoutTicks = 300;
            this.fleeTicks = 0;
            this.wantsToFlee = false;
            this.avoidEntity = null;
        }

        if(this.fleeTimeoutTicks > 0) {
            --this.fleeTimeoutTicks;
        }
    }

    private void fleeFromTargetPosition(LivingEntity target) {
        if(target != null) {
            Vec3 targetVec = DefaultRandomPos.getPosAway(this, 13, 4, target.getDeltaMovement());
            if(targetVec != null) {
                this.getNavigation().moveTo(targetVec.x, targetVec.y, targetVec.z, 1.0D);
            }
        }
    }

    private void summonGroup(BabyJohnEntity john, double numSpawns)
    {
        EntityType<BabyJohnEntity> newJohnType = JMEntites.BABYJOHN.get();
        if(!this.level().isClientSide) {
            for(int i = 0; i < numSpawns; i++) {
                BabyJohnEntity newJohn = newJohnType.create(this.level());
                if(newJohn != null) {
                    Vec3 spawnPos = LandRandomPos.getPos(john, 2, 5);
                    if(spawnPos != null) {
                        newJohn.setPos(spawnPos);
                        this.level().addFreshEntity(newJohn);
                    }
                }
            }
        }
    }

    @Override
    public int getExperienceReward() {
        return this.isAlpha() ? 15 : super.getExperienceReward();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount)
    {
        if(pSource.getDirectEntity() instanceof Player player)
        {
            ItemStack mainHand = player.getMainHandItem();
            if(mainHand.getItem() instanceof ImmortalBlade)
                pAmount *= 1.3f;
        }

        return super.hurt(pSource, pAmount);
    }

    public boolean isAlpha() {
        return this.entityData.get(ALPHA);
    }

    public void setAlpha(boolean bool) {
        this.entityData.set(ALPHA, bool);
    }

    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    public void setLeaping(boolean bool) {
        this.entityData.set(LEAPING, bool);
    }

    public boolean isFleeing() {
        return this.entityData.get(FLEEING);
    }

    public void setFleeing(boolean bool) {
        this.entityData.set(FLEEING, bool);
    }

    public boolean hasSummonedAllies() {
        return entityData.get(SUMMONED_ALLIES);
    }

    public void setSummonedAllies(boolean bool) {
        this.entityData.set(SUMMONED_ALLIES, bool);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return JMSounds.BABYJOHN_AMBIENT.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(DamageSource pDamageSource) {
        return JMSounds.BABYJOHN_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return JMSounds.BABYJOHN_HURT.get();
    }

    @Override
    protected @NotNull ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(MODID, "entities/babyjohn");
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        if(pSource.getDirectEntity() instanceof Player) {
            if(this.isAlpha()) {
                // replace with custom item
                ItemStack dropShard = JMItems.IMMORTALITY_SHARD.get().getDefaultInstance();
                this.spawnAtLocation(dropShard);
            }
        }
    }

    public static boolean canSpawn(EntityType<BabyJohnEntity> pEntity, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos sPosition, RandomSource random) {
        if (pLevel instanceof ServerLevel sLevel) {
            long gameTime = sLevel.getGameTime();
            long daysPassed = gameTime / 24000L;
            if(daysPassed < 4) return false;

            BlockPos worldSpawn = new BlockPos(
                    sLevel.getLevelData().getXSpawn(),
                    sLevel.getLevelData().getYSpawn(),
                    sLevel.getLevelData().getZSpawn());
            double distFromSpawn = Math.sqrt(sPosition.distSqr(worldSpawn));
            if(distFromSpawn < 500.0D) return false;

            DifficultyInstance difficulty = sLevel.getCurrentDifficultyAt(sPosition);
            float localDifficulty = difficulty.getEffectiveDifficulty();

            alphaSummonCount = localDifficulty > 1 ? localDifficulty * random.nextInt(3) + 1 : 1;

            double k = 1.1d; // slope factor
            double x0 = 3.6d; // inflection point

            // sigmoid curve sigmoid curve sigmoid curve sigmoid curve sigmoid curve sigmoid curve
            double spawnChance = 1.0 / (1.0 + Math.exp(-k * (localDifficulty - x0)));
            spawnChance = Math.min(1.0, Math.max(0.0, spawnChance)); // clamps to 0-1 range

            if (sLevel.isRainingAt(sPosition) || sLevel.isThundering()) {
                spawnChance *= 1.2F;  // increase spawn chance during rain or thunder
            }

            if(random.nextDouble() < spawnChance) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if(Math.random() >= 0.9)
            this.setAlpha(true);

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor pLevel, @NotNull MobSpawnType pSpawnReason) {
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && canSpawnAnywhere(pLevel, this.blockPosition());
    }

    private boolean canSpawnAnywhere(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos.below()).isValidSpawn(level, pos.below(), this.getType());
    }

    static class BabyJohnMeleeGoal extends Goal
    {
        BabyJohnEntity john;

        public BabyJohnMeleeGoal(BabyJohnEntity john) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.john = john;
        }

        @Override
        public boolean canUse() {
            return john.getTarget() != null && john.getTarget().isAlive() && !john.isFleeing() && !(john.getTarget() instanceof Player player && player.isCreative());
        }

        @Override
        public boolean canContinueToUse() {
            return !this.john.isFleeing();
        }

        @Override
        public void stop() {
            john.setLeaping(false);
        }

        @Override
        public void tick() {
            LivingEntity target = john.getTarget();
            if(target != null && target.isAlive() && !this.john.level().isClientSide) {
                john.lookControl.setLookAt(target);
                int jumpChance = john.isAlpha() ? 15 : 25;
                john.wantsToJump = john.getRandom().nextInt(jumpChance) == 0;
                double dist = john.distanceTo(target);

                if(john.isLeaping()) {
                    if(john.attackTicks <= 0) {
                        if(checkDamage(target)) {
                            john.attackTicks = 30;
                        }
                    }

                    if(john.onGround() || john.isInWaterOrBubble()) {
                        john.setLeaping(false);
                    }
                }
                else if (dist > 2.0F && dist < 7.0F && john.wantsToJump && !john.isLeaping())
                {
                    john.setLeaping(true);
                    this.john.playSound(JMSounds.BABYJOHN_JUMP.get(), 0.5F, 1.0F + john.random.nextFloat() * 0.2F);
                    Vec3 johnVec = john.getDeltaMovement();
                    Vec3 targetVec = new Vec3(target.getX() - john.getX(), 0, target.getZ() - john.getZ());
                    if(targetVec.lengthSqr() > 1.0E-7D)
                        targetVec = targetVec.normalize().scale(0.9F).add(johnVec.scale(0.5));
                    john.setDeltaMovement(targetVec.x, 0.5D, targetVec.z);
                }
                // chance to jump
                else {
                    john.getNavigation().moveTo(target, 1.0D);
                    if(john.attackTicks <= 0) {
                        if(checkDamage(target))
                        {
                            john.attackTicks = 15;
                            Vec3 randVec = getRandomPosition(john);
                            if(randVec != null)
                                john.getNavigation().moveTo(randVec.x, randVec.y, randVec.z, 1.0D);
                        }
                    }
                }
            }

        }

        public Vec3 getRandomPosition(PathfinderMob entity)
        {
            return DefaultRandomPos.getPos(entity, 10, 5);
        }

        public boolean checkDamage(LivingEntity target) {
            if(john.hasLineOfSight(target) && john.distanceTo(target) < john.getBbWidth() + target.getBbWidth() + 0.5)
            {
                return target.hurt(target.damageSources().mobAttack(john), (float) john.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            }
            return false;
        }
    }

    static class BabyJohnFollowAlphaGoal extends Goal
    {
        BabyJohnEntity mob;
        private final float movementSpeed;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private float oldWaterCost;

        public BabyJohnFollowAlphaGoal(Mob pMob, float pStopDistance) {
            mob = (BabyJohnEntity) pMob;
            this.movementSpeed = 1.0F;
            this.navigation = pMob.getNavigation();
            this.stopDistance = pStopDistance;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
            this.mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        @Override
        public boolean canUse() {
            return !mob.isAlpha() && mob.hasAlpha && mob.alpha != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return super.canContinueToUse() && mob.alpha.isAlive();
        }

        @Override
        public void stop() {
            this.mob.hasAlpha = false;
            this.mob.alpha = null;
            this.navigation.stop();
            this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        @Override
        public void tick() {
            if (this.mob.alpha != null && !this.mob.isLeashed()) {
                this.mob.getLookControl().setLookAt(this.mob.alpha, 10.0F, (float)this.mob.getMaxHeadXRot());
                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = this.adjustedTickDelay(3);
                    double d0 = this.mob.getX() - this.mob.alpha.getX();
                    double d1 = this.mob.getY() - this.mob.alpha.getY();
                    double d2 = this.mob.getZ() - this.mob.alpha.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    if (!(d3 <= (double)(this.stopDistance * this.stopDistance))) {
                        this.navigation.moveTo(this.mob.alpha, movementSpeed);
                    } else {
                        this.navigation.stop();
                        LookControl lookcontrol = this.mob.alpha.getLookControl();
                        if (d3 <= (double)this.stopDistance || lookcontrol.getWantedX() == this.mob.getX() && lookcontrol.getWantedY() == this.mob.getY() && lookcontrol.getWantedZ() == this.mob.getZ()) {
                            double d4 = this.mob.alpha.getX() - this.mob.getX();
                            double d5 = this.mob.alpha.getZ() - this.mob.getZ();
                            this.navigation.moveTo(this.mob.getX() - d4, this.mob.getY(), this.mob.getZ() - d5, movementSpeed);
                        }

                    }
                }
            }
        }
    }
}
