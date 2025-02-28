package net.notvergin.jmresurrected.entity.livingentity;

import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.notvergin.jmresurrected.JohnModResurrected;
import net.notvergin.jmresurrected.items.weapons.ImmortalBlade;
import net.notvergin.jmresurrected.sound.JMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JohnEntity extends Monster {
    private static final EntityDataAccessor<Boolean> CHASING = SynchedEntityData.defineId(JohnEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STALKING = SynchedEntityData.defineId(JohnEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_STALKED = SynchedEntityData.defineId(JohnEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STUCK = SynchedEntityData.defineId(JohnEntity.class, EntityDataSerializers.BOOLEAN);

    private final Logger LOG = JohnModResurrected.LOGGER;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public int stuckTime = 0;
    public int reachCooldown = 0;

    private Player target = null;

    private void setupAnimationStates() {
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
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6.0F, 1.0F);
        } else {
            f = 0.0f;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    public JohnEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.5D)
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.54D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new JohnStalkTargetGoal(this));
        this.goalSelector.addGoal(1, new JohnMeleeGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.96D, false));
        this.goalSelector.addGoal(3, new JohnReachTargetGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 32.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8f));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHASING, false);
        this.entityData.define(STALKING, false);
        this.entityData.define(HAS_STALKED, false);
        this.entityData.define(STUCK, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);


        compound.putBoolean("Chasing", this.isChasing());
        compound.putBoolean("Stuck", this.isStuck());
        compound.putBoolean("isStalking", this.isStalking());
        compound.putBoolean("hasStalked", this.hasStalked());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.setChasing(compound.getBoolean("Chasing"));
        LOG.info("READ Chasing: {}", this.isChasing());
        this.setStuck(compound.getBoolean("Stuck"));
        this.setStalking(compound.getBoolean("isStalking"));
        LOG.info("READ IsStalking: {}", this.isStalking());
        this.setStalked(compound.getBoolean("hasStalked"));
        LOG.info("READ HasStalked: {}", this.hasStalked());
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new GroundPathNavigation(this, pLevel) {
            @Override
            protected void followThePath() {
                this.mob.setMaxUpStep(1.0F);
                super.followThePath();
            }
        };
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }

        super.tick();

        if (this.getTarget() != null && this.getTarget() instanceof Player && this.target == null) {
            LOG.info("John Target set to: {}", this.getTarget());
            this.target = (Player) this.getTarget();
        }

        if (this.target != null && (!this.target.isAlive() || target.isCreative())) {
            LOG.info("John Target has been set to null");
            this.target = null;
            this.setStalked(false);
            this.setChasing(false);
            this.setStalking(false);
        }

        if (this.target != null && this.target.isAlive()) {
            if (!this.level().isClientSide()) {
                if (navigation.isDone()) {
                    stuckTime++;
                } else {
                    stuckTime = 0;
                }

                if (stuckTime > 60 && !this.isStuck()) {
                    setStuck(true);
                    LOG.info("John stuck");
                } else {
                    setStuck(false);
                }
            }

        }
        reachCooldown--;

        if (this.isChasing() && !isStalking()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.41D);
        }

        if (!this.isChasing() || isStalking()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.21D);
        }
    }

    private boolean isChasing() {
        return this.entityData.get(CHASING);
    }

    private void setChasing(boolean chase) {
        this.entityData.set(CHASING, chase);
    }

    private boolean isStuck() {
        return this.entityData.get(STUCK);
    }

    private void setStuck(boolean stuck) {
        this.entityData.set(STUCK, stuck);
    }

    private boolean isStalking() {
        return this.entityData.get(STALKING);
    }

    private void setStalking(boolean stalking) {
        this.entityData.set(STALKING, stalking);
    }

    private boolean hasStalked() {
        return this.entityData.get(HAS_STALKED);
    }

    private void setStalked(boolean stalked) {
        this.entityData.set(HAS_STALKED, stalked);
    }

    private boolean isSeenBy(Player player) {
        Vec3 lookAngle = player.getLookAngle();
        Vec3 johnDirection = this.position().subtract(player.getEyePosition()).normalize();

        double dot = lookAngle.dot(johnDirection);
        double viewAngle = Math.toDegrees(Math.acos(dot));
        double fov = Minecraft.getInstance().options.fov().get();

        if (viewAngle > (fov)) {
            return false;
        }

        return player.hasLineOfSight(this);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity entity = pSource.getEntity();
        if (entity instanceof Player player) {
            ItemStack handItem = player.getMainHandItem();
            if (handItem.getItem() instanceof ImmortalBlade) {
                pAmount *= 1.3f;
            }

        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public int getExperienceReward() {
        return 50;
    }

    public static boolean canSpawn(EntityType<JohnEntity> pEntity, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos sPosition, RandomSource random) {
        if (pLevel instanceof ServerLevel sLevel) {
            long gameTime = sLevel.getGameTime();
            long daysPassed = gameTime / 24000L;
            if (daysPassed < 5) return false;

            BlockPos worldSpawn = new BlockPos(
                    sLevel.getLevelData().getXSpawn(),
                    sLevel.getLevelData().getYSpawn(),
                    sLevel.getLevelData().getZSpawn());
            double distFromSpawn = Math.sqrt(sPosition.distSqr(worldSpawn));
            if (distFromSpawn < 1000.0D) return false;

            DifficultyInstance difficulty = sLevel.getCurrentDifficultyAt(sPosition);
            float localDifficulty = difficulty.getEffectiveDifficulty();

            double k = 1.4d;  // slope factor
            double x0 = 3.7d; // inflection point

            // sigmoid curve sigmoid curve sigmoid curve sigmoid curve sigmoid curve sigmoid curve
            double spawnChance = 1.0 / (1.0 + Math.exp(-k * (localDifficulty - x0))) + 0.01;
            spawnChance = Math.min(0.45, Math.max(0.0, spawnChance)); // clamps to 0-0.45 range

            if (sLevel.isRainingAt(sPosition) || sLevel.isThundering()) {
                spawnChance *= 1.2F;  // increase spawn chance during rain or thunder
            }

            if (random.nextDouble() < spawnChance) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && canSpawnAnywhere(pLevel, this.blockPosition());
    }

    private boolean canSpawnAnywhere(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos.below()).isValidSpawn(level, pos.below(), this.getType());
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if (!this.isStalking())
            return JMSounds.JOHN_AMBIENT.get();
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return JMSounds.JOHN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return JMSounds.JOHN_DEATH.get();
    }

    @Override
    public void checkDespawn() {
        if (this.target != null)
            return;

        super.checkDespawn();
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(MODID, "entities/john");
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 3.75f;
    }

    static class JohnMeleeGoal extends Goal {
        JohnEntity john;

        private int attackCooldown = 0;
        private final int DEF_AttackCooldown = 15;

        public JohnMeleeGoal(JohnEntity john) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.TARGET));
            this.john = john;
        }

        @Override
        public boolean canUse() {
            return john.target != null && john.target.isAlive() && (!john.isStalking() || john.isSeenBy(john.target));
        }

        @Override
        public boolean canContinueToUse() {
            return john.target != null && john.target.isAlive();
        }

        @Override
        public void start() {
            john.setChasing(true);
            john.setStalking(false);
            super.start();
        }

        @Override
        public void stop() {
            john.setChasing(false);
            john.target = null;
            super.stop();
        }

        @Override
        public void tick() {
            if (john.target != null && john.target.isAlive() && !john.level().isClientSide) {
                john.lookControl.setLookAt(john.target);
                john.getNavigation().moveTo(john.target, 1.0D);

                if (attackCooldown <= 0) {
                    if (checkDamage(john.target)) {
                        attackCooldown = DEF_AttackCooldown;
                    }
                }

            }
            --attackCooldown;

            super.tick();
        }

        public boolean checkDamage(LivingEntity target) {
            if (john.distanceTo(target) < john.getBbWidth() + target.getBbWidth() + 0.5) {
                return target.hurt(target.damageSources().mobAttack(john), (float) john.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            }
            return false;
        }
    }

    static class JohnStalkTargetGoal extends Goal {
        JohnEntity john;

        public JohnStalkTargetGoal(JohnEntity john) {
            this.john = john;
        }

        @Override
        public boolean canUse() {
            return this.john.target != null && this.john.target instanceof Player && !this.john.hasStalked() && !this.john.isSeenBy((Player) this.john.target) && this.john.target.isAlive() && !this.john.isChasing();
        }

        @Override
        public boolean canContinueToUse() {
            return this.john.target != null && this.john.target.isAlive() && !this.john.isSeenBy(this.john.target);
        }

        @Override
        public void start() {
            this.john.setStalking(true);
            super.start();
        }

        @Override
        public void stop() {
            this.john.setStalking(false);
            this.john.setStalked(true);
            super.stop();
        }

        @Override
        public void tick() {
            super.tick();

            if (john.target != null && !john.level().isClientSide) {

                if (john.position().distanceTo(john.target.position()) < 8.0) {
                    Vec3 targetLookVec = john.target.getLookAngle().normalize();
                    Vec3 oppositeLookVec = john.target.position().subtract(targetLookVec.scale(john.position().distanceTo(john.target.position()) - 0.1));

                    john.getNavigation().moveTo(oppositeLookVec.x(), oppositeLookVec.y(), oppositeLookVec.z(), 1.5D);
                } else
                    john.getNavigation().moveTo(john.target, 1.0D);
            }
        }
    }

    static class JohnReachTargetGoal extends Goal {
        private final JohnEntity john;
        private static final int coolDownDuration = 40;

        public JohnReachTargetGoal(Mob pMob) {
            john = (JohnEntity) pMob;
        }

        @Override
        public boolean canUse() {
            return john.reachCooldown <= 0 && john.getTarget() != null && john.isStuck();
        }

        @Override
        public void start() {
            LivingEntity target = john.getTarget();
            if (target != null) {
                if ((john.distanceToSqr(target.getX(), target.getY(), target.getZ()) <= 50.59) && target.getY() > john.getY()) {
                    double jumpHeight = Math.sqrt(0.189D * (target.getY() - (john.getY() - 1.0)));

                    Vec3 vec3 = john.getDeltaMovement();
                    Vec3 vec31 = new Vec3(target.getX() - john.getX(), 0.0D, target.getZ() - john.getZ());
                    if (vec31.lengthSqr() > 1.0E-7D) {
                        vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
                    }
                    john.setDeltaMovement(vec31.x, jumpHeight, vec31.z);
                } else if (john.distanceToSqr(target.getX(), target.getY(), target.getZ()) >= 256.59) {
                    double xOff = (john.getRandom().nextDouble() - 0.5) * 100;
                    double zOff = (john.getRandom().nextDouble() - 0.5) * 100;

                    double xTeleport = john.getX() + xOff;
                    double zTeleport = john.getZ() + zOff;

                    BlockPos teleportPos = john.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos((int) xTeleport, (int) john.getY(), (int) zTeleport));

                    john.randomTeleport(teleportPos.getX() + 0.5, teleportPos.getY(), teleportPos.getZ() + 0.5, true);
                }
            }
        }


        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void stop() {
            john.reachCooldown = coolDownDuration;
            super.stop();
        }
    }
}
