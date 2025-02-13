package net.notvergin.jmresurrected.entity.mobs;

import net.notvergin.jmresurrected.customitems.weapons.ImmortalBlade;
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
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static net.notvergin.jmresurrected.JohnModResurrected.MODID;

public class JohnEntity extends Monster
{
    public JohnEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public int stuckTime = 0;
    public int reachCooldown = 0;
    public boolean isStuck = false;

    public boolean hasTarget = false;

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
                .add(Attributes.MOVEMENT_SPEED, 0.41D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.5D)
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.54D);
    }

    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new JohnReachTargetGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 32.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8f));
        this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f, true));
        this.targetSelector.addGoal(2, new JohnChaseGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, false, false));
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

        if(hasTarget)
        {
            if(navigation.isDone())
                stuckTime++;
            else stuckTime = 0;

            if(stuckTime > 60)
            {
                isStuck = true;
                //System.out.println("John stuck");
            }


            else isStuck = false;
        }
        //else System.out.println("nav done");

        reachCooldown--;

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
            {
                pAmount *= 1.3f;
            }

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

    public static boolean canSpawn(EntityType<JohnEntity> pEntity, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos sPosition, RandomSource random)
    {
        if (pLevel instanceof ServerLevel sLevel)
        {
            long gameTime = sLevel.getGameTime();
            long daysPassed = gameTime / 24000L;
            if(daysPassed < 5) return false;

            BlockPos worldSpawn = new BlockPos(
                    sLevel.getLevelData().getXSpawn(),
                    sLevel.getLevelData().getYSpawn(),
                    sLevel.getLevelData().getZSpawn());
            double distFromSpawn = Math.sqrt(sPosition.distSqr(worldSpawn));
            if(distFromSpawn < 1000.0D) return false;

            DifficultyInstance difficulty = sLevel.getCurrentDifficultyAt(sPosition);
            float localDifficulty = difficulty.getEffectiveDifficulty();

            double k = 3.0d;  // slope factor
            double x0 = 3.9d; // inflection point

            // sigmoid curve sigmoid curve sigmoid curve sigmoid curve sigmoid curve sigmoid curve
            double spawnChance = 1.0 / (1.0 + Math.exp(-k * (localDifficulty - x0)));
            spawnChance = Math.min(1.0, Math.max(0.0, spawnChance)); // clamps to 0-1 range

            if (sLevel.isRainingAt(sPosition) || sLevel.isThundering()) {
                spawnChance *= 1.2F;  // increase spawn chance during rain or thunder
            }

            return random.nextDouble() < spawnChance;
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
    protected @Nullable SoundEvent getAmbientSound() {
        return JMSounds.JOHN_AMBIENT.get();
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
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public void checkDespawn()
    {
        if(this.getTarget() != null)
            return;

        if(this.level().getNearestPlayer(this, 128) != null)
            return;

        super.checkDespawn();
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

    static class JohnChaseGoal extends NearestAttackableTargetGoal<LivingEntity>
    {
        private final JohnEntity john;
        private LivingEntity currentTarget;
        public boolean flag = false;

        public JohnChaseGoal(Mob pMob) {
            super(pMob, LivingEntity.class, 10, false, false, null);
            john = (JohnEntity) pMob;
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
                        john.hasTarget = true;
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
            john.hasTarget = false;
            currentTarget = null;
            flag = false;
        }
        // updates nav every tick to keep chasing
        @Override
        public void tick()
        {
            if(currentTarget != null)
            {
                Player chasedPlayer = this.mob.level().getNearestPlayer(mob, 48.0D);
                if(!currentTarget.hasLineOfSight(this.mob) && chasedPlayer != null) { // player still close
                    //System.out.println("Run fast");
                    this.mob.getNavigation().moveTo(
                            currentTarget.getX(),
                            currentTarget.getY(),
                            currentTarget.getZ(),
                            1.25D);
                }
                else if(!currentTarget.hasLineOfSight(this.mob) && chasedPlayer == null) { // player gets far away
                    //System.out.println("Player far");
                    this.mob.getNavigation().moveTo(
                            currentTarget.getX(),
                            currentTarget.getY(),
                            currentTarget.getZ(),
                            0.61111111111111111111111111111111111111111111111111111111111111111111111111111D);
                }
                else { // normal
                    this.mob.getNavigation().moveTo(
                            currentTarget.getX(),
                            currentTarget.getY(),
                            currentTarget.getZ(),
                            1.0D);
                }
            }
        }
    }

    static class JohnReachTargetGoal extends Goal
    {
        private final JohnEntity john;
        private static final int coolDownDuration = 40;

        public JohnReachTargetGoal(Mob pMob)
        {
            john = (JohnEntity)pMob;
        }

        @Override
        public boolean canUse()
        {
            return john.reachCooldown <= 0 && john.hasTarget && john.isStuck;
        }

        public boolean canContinueToUse() {
            return !john.onGround();
        }

        @Override
        public void start()
        {
            LivingEntity target = john.getTarget();
            if(target != null) // Player close, jump up to player
            {
                if((john.distanceToSqr(
                        target.getX(),
                        target.getY(),
                        target.getZ()) <= 50.59) && target.getY() > john.getY())
                {
                    // most stuff stripped from leap goal
                    double jumpHeight = Math.sqrt(0.189D * (target.getY() - (john.getY() - 1.0)));

                    Vec3 vec3 = john.getDeltaMovement();
                    Vec3 vec31 = new Vec3(target.getX() - john.getX(), 0.0D, target.getZ() - john.getZ());
                    if (vec31.lengthSqr() > 1.0E-7D) {
                        vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
                    }
                    john.setDeltaMovement(vec31.x, jumpHeight, vec31.z);
                }
                else if(john.distanceToSqr(
                        target.getX(),
                        target.getY(),
                        target.getZ()) >= 256.59)
                {
                    double xOff = (john.getRandom().nextDouble() - 0.5) * 100;
                    double zOff = (john.getRandom().nextDouble() - 0.5) * 100;

                    double xTeleport = john.getX() + xOff;
                    double zTeleport = john.getZ() + zOff;

                    BlockPos teleportPos = john.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos((int) xTeleport, (int) john.getY(), (int) zTeleport));

                    john.randomTeleport(
                            teleportPos.getX() + 0.5,
                            teleportPos.getY(),
                            teleportPos.getZ() + 0.5,
                            true);
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
