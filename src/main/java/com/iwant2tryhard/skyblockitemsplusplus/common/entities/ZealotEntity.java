package com.iwant2tryhard.skyblockitemsplusplus.common.entities;

import com.iwant2tryhard.skyblockitemsplusplus.core.init.EntityTypeInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.ai.goal.*;
import com.iwant2tryhard.skyblockitemsplusplus.common.entities.ZealotEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class ZealotEntity extends MonsterEntity implements IAngerable {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING;
    private static final DataParameter<Optional<BlockState>> DATA_CARRY_STATE;
    private static final DataParameter<Boolean> DATA_CREEPY;
    private static final DataParameter<Boolean> DATA_STARED_AT;
    private static final Predicate<LivingEntity> ENDERMITE_SELECTOR;
    private int lastStareSound = -2147483648;
    private int targetChangeTime;
    private static final RangedInteger PERSISTENT_ANGER_TIME;
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    public ZealotEntity(EntityType<ZealotEntity> entity, World world) {
        super(entity, world);
        this.maxUpStep = 1.0F;
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
    }

    public ZealotEntity(World world) {
        this(EntityTypeInit.ZEALOT.get(), world);
        this.maxUpStep = 1.0F;
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new ZealotEntity.StareGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new ZealotEntity.FindPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, EndermiteEntity.class, 10, true, false, ENDERMITE_SELECTOR));
        this.targetSelector.addGoal(4, new ResetAngerGoal(this, false));
    }

    /*@Override
    public AttributeModifierManager getAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60.0D);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.40000001192092896D);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(40.0D);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(64.0D);
        return super.getAttributes();
    }*/

    public static AttributeModifierMap.MutableAttribute setAttributes() {
        return MonsterEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35000001192092896D)
                .add(Attributes.ATTACK_DAMAGE, 30.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    public void setTarget(@Nullable LivingEntity p_70624_1_) {
        ModifiableAttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (p_70624_1_ == null) {
            this.targetChangeTime = 0;
            this.entityData.set(DATA_CREEPY, false);
            this.entityData.set(DATA_STARED_AT, false);
            modifiableattributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            this.targetChangeTime = this.tickCount;
            this.entityData.set(DATA_CREEPY, true);
            if (!modifiableattributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }

        super.setTarget(p_70624_1_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CARRY_STATE, Optional.empty());
        this.entityData.define(DATA_CREEPY, false);
        this.entityData.define(DATA_STARED_AT, false);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }

    public void setRemainingPersistentAngerTime(int p_230260_1_) {
        this.remainingPersistentAngerTime = p_230260_1_;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }

    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void playStareSound() {
        if (this.tickCount >= this.lastStareSound + 400) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
            }
        }

    }

    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (DATA_CREEPY.equals(p_184206_1_) && this.hasBeenStaredAt() && this.level.isClientSide) {
            this.playStareSound();
        }

        super.onSyncedDataUpdated(p_184206_1_);
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);

        this.addPersistentAngerSaveData(p_213281_1_);
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        if (!this.level.isClientSide) {
            this.readPersistentAngerSaveData((ServerWorld)this.level, p_70037_1_);
        }

    }

    private boolean isLookingAtMe(PlayerEntity p_70821_1_) {
        Vector3d vector3d = p_70821_1_.getViewVector(1.0F).normalize();
        Vector3d vector3d1 = new Vector3d(this.getX() - p_70821_1_.getX(), this.getEyeY() - p_70821_1_.getEyeY(), this.getZ() - p_70821_1_.getZ());
        double d0 = vector3d1.length();
        vector3d1 = vector3d1.normalize();
        double d1 = vector3d.dot(vector3d1);
        return d1 > 1.0D - 0.025D / d0 ? p_70821_1_.canSee(this) : false;
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 2.55F;
    }

    public void aiStep() {
        if (this.level.isClientSide) {
            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }

        this.jumping = false;
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerWorld)this.level, true);
        }

        super.aiStep();
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    protected void customServerAiStep() {
        if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
            float f = this.getBrightness();
            if (f > 0.5F && this.level.canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setTarget((LivingEntity)null);
                this.teleport();
            }
        }

        super.customServerAiStep();
    }

    protected boolean teleport() {
        if (!this.level.isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(64) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    private boolean teleportTowards(Entity p_70816_1_) {
        Vector3d vector3d = new Vector3d(this.getX() - p_70816_1_.getX(), this.getY(0.5D) - p_70816_1_.getEyeY(), this.getZ() - p_70816_1_.getZ());
        vector3d = vector3d.normalize();
        double d0 = 16.0D;
        double d1 = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.x * 16.0D;
        double d2 = this.getY() + (double)(this.random.nextInt(16) - 8) - vector3d.y * 16.0D;
        double d3 = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vector3d.z * 16.0D;
        return this.teleport(d1, d2, d3);
    }

    private boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(p_70825_1_, p_70825_3_, p_70825_5_);

        while(blockpos$mutable.getY() > 0 && !this.level.getBlockState(blockpos$mutable).getMaterial().blocksMotion()) {
            blockpos$mutable.move(Direction.DOWN);
        }

        BlockState blockstate = this.level.getBlockState(blockpos$mutable);
        boolean flag = blockstate.getMaterial().blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            EntityTeleportEvent.EnderEntity event = ForgeEventFactory.onEnderTeleport(this, p_70825_1_, p_70825_3_, p_70825_5_);
            if (event.isCanceled()) {
                return false;
            } else {
                boolean flag2 = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
                if (flag2 && !this.isSilent()) {
                    this.level.playSound((PlayerEntity)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }

                return flag2;
            }
        } else {
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENDERMAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
        super.dropCustomDeathLoot(p_213333_1_, p_213333_2_, p_213333_3_);
        BlockState blockstate = this.getCarriedBlock();
        if (blockstate != null) {
            this.spawnAtLocation(blockstate.getBlock());
        }

    }

    public void setCarriedBlock(@Nullable BlockState p_195406_1_) {
        this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable(p_195406_1_));
    }

    @Nullable
    public BlockState getCarriedBlock() {
        return (BlockState)((Optional)this.entityData.get(DATA_CARRY_STATE)).orElse((BlockState)null);
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else if (p_70097_1_ instanceof IndirectEntityDamageSource) {
            for(int i = 0; i < 64; ++i) {
                if (this.teleport()) {
                    return true;
                }
            }

            return false;
        } else {
            boolean flag = super.hurt(p_70097_1_, p_70097_2_);
            if (!this.level.isClientSide() && !(p_70097_1_.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
                this.teleport();
            }

            return flag;
        }
    }

    public boolean isCreepy() {
        return (Boolean)this.entityData.get(DATA_CREEPY);
    }

    public boolean hasBeenStaredAt() {
        return (Boolean)this.entityData.get(DATA_STARED_AT);
    }

    public void setBeingStaredAt() {
        this.entityData.set(DATA_STARED_AT, true);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.getCarriedBlock() != null;
    }

    static {
        SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15000000596046448D, AttributeModifier.Operation.ADDITION);
        DATA_CARRY_STATE = EntityDataManager.defineId(ZealotEntity.class, DataSerializers.BLOCK_STATE);
        DATA_CREEPY = EntityDataManager.defineId(ZealotEntity.class, DataSerializers.BOOLEAN);
        DATA_STARED_AT = EntityDataManager.defineId(ZealotEntity.class, DataSerializers.BOOLEAN);
        ENDERMITE_SELECTOR = (p_213626_0_) -> {
            return p_213626_0_ instanceof EndermiteEntity && ((EndermiteEntity)p_213626_0_).isPlayerSpawned();
        };
        PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    }

    static class StareGoal extends Goal {
        private final ZealotEntity zealot;
        private LivingEntity target;

        public StareGoal(ZealotEntity p_i50520_1_) {
            this.zealot = p_i50520_1_;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        public boolean canUse() {
            this.target = this.zealot.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            } else {
                double d0 = this.target.distanceToSqr(this.zealot);
                return d0 > 256.0D ? false : this.zealot.isLookingAtMe((PlayerEntity)this.target);
            }
        }

        public void start() {
            this.zealot.getNavigation().stop();
        }

        public void tick() {
            this.zealot.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    static class FindPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        private final ZealotEntity zealot;
        private PlayerEntity pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final EntityPredicate startAggroTargetConditions;
        private final EntityPredicate continueAggroTargetConditions = (new EntityPredicate()).allowUnseeable();

        public FindPlayerGoal(ZealotEntity p_i241912_1_, @Nullable Predicate<LivingEntity> p_i241912_2_) {
            super(p_i241912_1_, PlayerEntity.class, 10, false, false, p_i241912_2_);
            this.zealot = p_i241912_1_;
            this.startAggroTargetConditions = (new EntityPredicate()).range(this.getFollowDistance()).selector((p_220790_1_) -> {
                return p_i241912_1_.isLookingAtMe((PlayerEntity)p_220790_1_);
            });
        }

        public boolean canUse() {
            this.pendingTarget = this.zealot.level.getNearestPlayer(this.startAggroTargetConditions, this.zealot);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = 5;
            this.teleportTime = 0;
            this.zealot.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.zealot.isLookingAtMe(this.pendingTarget)) {
                    return false;
                } else {
                    this.zealot.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.target != null && this.continueAggroTargetConditions.test(this.zealot, this.target) ? true : super.canContinueToUse();
            }
        }

        public void tick() {
            if (this.zealot.getTarget() == null) {
                super.setTarget((LivingEntity)null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.target != null && !this.zealot.isPassenger()) {
                    if (this.zealot.isLookingAtMe((PlayerEntity)this.target)) {
                        if (this.target.distanceToSqr(this.zealot) < 16.0D) {
                            this.zealot.teleport();
                        }

                        this.teleportTime = 0;
                    } else if (this.target.distanceToSqr(this.zealot) > 256.0D && this.teleportTime++ >= 30 && this.zealot.teleportTowards(this.target)) {
                        this.teleportTime = 0;
                    }
                }

                super.tick();
            }

        }
    }
}
