package gatocreador887.greenvoidislands.common.entity.bird.passive;

import gatocreador887.greenvoidislands.common.block.BlockShronkLeaves;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.core.GVILootTableManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityShronker extends EntityCreature {
	protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.<Boolean>createKey(EntityShronker.class, DataSerializers.BOOLEAN);
	/** What direction this Shronker is flying in */
	public float direction = this.rand.nextFloat();
	/** How hungry this Shronker is */
	protected float hunger = this.rand.nextFloat();
	/** How long this Shronker is scared */
	protected int scaredTimer;
	public float flap;
	public float flapSpeed;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;
	
	public EntityShronker(World worldIn) {
		super(worldIn);
		this.setSize(0.9F, 0.8F);
		this.moveHelper = new FlyingMoveHelper(this);
		this.experienceValue = 5;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SLEEPING, Boolean.valueOf(false));
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityShronker.AISleep());
		this.tasks.addTask(1, new EntityShronker.AIEatShronk());
		this.tasks.addTask(2, new EntityShronker.AIFlyingRandom());
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		// this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
		// this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4000000059604645D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4000000059604645D);
	}
	
	@Override
	public void onUpdate() {
		this.setNoGravity(!this.isSleeping());
		super.onUpdate();
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (this.hunger > 0.0F) {
			this.hunger -= 0.0005F;
		}
		
		if (this.scaredTimer > 0) {
			--this.scaredTimer;
		}
		
		if (!this.world.isRemote && this.world.isDaytime() && this.isSleeping()) {
			this.setSleeping(false);
		}
		
		this.calculateFlapping();
	}
	
	private void calculateFlapping() {
		this.oFlap = this.flap;
		this.oFlapSpeed = this.flapSpeed;
		this.flapSpeed = 0.2F;
		this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
		
		if (this.flapping < 1.0F) {
			this.flapping = 1.0F;
		}
		
		this.flapping = (float) ((double) this.flapping * 0.9D);
		
		this.flap += this.flapping * 2.0F;
	}
	
	@Override
	public float getEyeHeight() {
		return 0.6F;
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {}
	
	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
	
	@Override
	public ResourceLocation getLootTable() {
		return GVILootTableManager.SHRONKER;
	}
	
	@Override
	protected float playFlySound(float dist) {
		this.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 0.15F, 1.75F);
		return dist + this.flapSpeed * 2.0F;
	}
	
	@Override
	protected boolean makeFlySound() {
		return !this.isSleeping();
	}
	
	public boolean isSleeping() {
		return this.dataManager.get(SLEEPING).booleanValue();
	}
	
	public void setSleeping(boolean sleeping) {
		this.dataManager.set(SLEEPING, Boolean.valueOf(sleeping));
	}
	
	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() != null) {
			float angle = source.getImmediateSource().rotationYaw + 90.0F;
			
			if (angle < 0) {
				angle += 360.0F;
			}
			
			this.direction = (angle) / 360.0F;// 1/4 is south
			this.scaredTimer = 200;
			this.getMoveHelper().action = EntityMoveHelper.Action.WAIT;
		} else {
			this.direction = this.rand.nextFloat();
		}
		
		this.hunger -= source.getHungerDamage() / 6.0F;
		this.setSleeping(false);
		
		return super.attackEntityFrom(source, amount);
	}
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		
		compound.setFloat("Direction", this.direction);
		compound.setFloat("Hunger", this.hunger);
		compound.setFloat("ScaredTimer", this.scaredTimer);
		compound.setBoolean("Sleeping", this.isSleeping());
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		if (compound.hasKey("Direction", 99)) {
			this.direction = MathHelper.clamp(compound.getFloat("Direction"), 0.0F, 1.0F);
		}
		
		if (compound.hasKey("Hunger", 99)) {
			this.hunger = MathHelper.clamp(compound.getFloat("Hunger"), 0.0F, 1.0F);
		}
		
		if (compound.hasKey("ScaredTimer", 99)) {
			this.scaredTimer = Math.max(compound.getInteger("ScaredTimer"), 0);
		}
		
		this.setSleeping(compound.getBoolean("Sleeping"));
	}
	
	class FlyingMoveHelper extends EntityMoveHelper {
		public FlyingMoveHelper(EntityShronker shronker) {
			super(shronker);
		}
		
		@Override
		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO) {
				double x = this.posX - EntityShronker.this.posX;
				double y = this.posY - EntityShronker.this.posY;
				double z = this.posZ - EntityShronker.this.posZ;
				double dist = MathHelper.sqrt(x * x + y * y + z * z);
				
				if (dist < EntityShronker.this.getEntityBoundingBox().getAverageEdgeLength()) {
					this.action = EntityMoveHelper.Action.WAIT;
				} else {
					EntityShronker.this.motionX += x / dist * 0.05D * this.speed;
					EntityShronker.this.motionY += y / dist * 0.05D * this.speed;
					EntityShronker.this.motionZ += z / dist * 0.05D * this.speed;
					EntityShronker.this.rotationYaw = -((float) MathHelper.atan2(EntityShronker.this.motionX, EntityShronker.this.motionZ)) * (180.0F / (float) Math.PI);
					EntityShronker.this.renderYawOffset = EntityShronker.this.rotationYaw;
				}
			}
		}
	}
	
	class AIEatShronk extends EntityAIBase {
		public BlockPos shronkPos;
		public int fruitAge;
		
		public AIEatShronk() {
			this.setMutexBits(3);
		}
		
		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			if (!EntityShronker.this.isSleeping() && EntityShronker.this.scaredTimer <= 0 && EntityShronker.this.hunger < 0.9F) {
				for (int xOffset = 0; xOffset < 20; xOffset++) {
					for (int yOffset = 0; yOffset < 30; yOffset++) {
						for (int zOffset = 0; zOffset < 20; zOffset++) {
							double x = EntityShronker.this.posX + xOffset;
							double y = EntityShronker.this.posY + yOffset;
							double z = EntityShronker.this.posZ + zOffset;
							BlockPos pos = new BlockPos(x, y, z);
							
							if (EntityShronker.this.world.getBlockState(pos).getBlock() == GVIBlockManager.SHRONK_LEAVES && EntityShronker.this.world.isAirBlock(pos.up())) {
								this.fruitAge = EntityShronker.this.world.getBlockState(pos).getValue(BlockShronkLeaves.FRUIT_AGE);
								
								if (this.fruitAge > 1) {
									this.shronkPos = pos;
									return true;
								}
							}
						}
					}
				}
			}
			
			return false;
		}
		
		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return this.shronkPos != null && !EntityShronker.this.isSleeping() && EntityShronker.this.scaredTimer <= 0 && EntityShronker.this.hunger < 0.9F;
		}
		
		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		@Override
		public void resetTask() {
			super.resetTask();
			this.shronkPos = null;
			this.fruitAge = 0;
		}
		
		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void updateTask() {
			if (EntityShronker.this.world.getBlockState(this.shronkPos).getBlock() == GVIBlockManager.SHRONK_LEAVES && EntityShronker.this.world.isAirBlock(this.shronkPos.up())) {
				this.fruitAge = EntityShronker.this.world.getBlockState(this.shronkPos).getValue(BlockShronkLeaves.FRUIT_AGE);
				
				if (this.fruitAge > 1) {
					EntityShronker.this.moveHelper.setMoveTo(this.shronkPos.getX() + 0.5D, this.shronkPos.up().getY() + 0.25D, this.shronkPos.getZ() + 0.5D, 0.5D);
					EntityShronker.this.getLookHelper().setLookPosition(this.shronkPos.getX() + 0.5D, this.shronkPos.up().getY() + 0.25D, this.shronkPos.getZ() + 0.5D, 180.0F, 20.0F);
					double x = EntityShronker.this.posX - (this.shronkPos.getX() + 0.5D);
					double y = EntityShronker.this.posY - (this.shronkPos.up().getY() + 0.25D);
					double z = EntityShronker.this.posZ - (this.shronkPos.getZ() + 0.5D);
					double dist = Math.sqrt(x * x + y * y + z * z);
					
					if (dist < EntityShronker.this.getEntityBoundingBox().getAverageEdgeLength()) {
						EntityShronker.this.world.setBlockState(this.shronkPos, EntityShronker.this.world.getBlockState(this.shronkPos).withProperty(BlockShronkLeaves.FRUIT_AGE, Integer.valueOf(0)));
						EntityShronker.this.hunger += 0.1F * (this.fruitAge - 1);
						EntityShronker.this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 0.9F + EntityShronker.this.rand.nextFloat() * 0.3F);
						this.resetTask();
					}
					
					return;
				}
			}
			
			this.resetTask();
		}
	}
	
	class AIFlyingRandom extends EntityAIBase {
		public AIFlyingRandom() {
			this.setMutexBits(3);
		}
		
		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			return !EntityShronker.this.isSleeping() && !EntityShronker.this.getMoveHelper().isUpdating();
		}
		
		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return false;
		}
		
		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void updateTask() {
			EntityShronker.this.direction += (EntityShronker.this.rand.nextFloat() - EntityShronker.this.rand.nextFloat()) * 0.1F;
			
			if (EntityShronker.this.direction < 0.0F) {
				EntityShronker.this.direction += 1.0F;
			} else if (EntityShronker.this.direction > 1.0F) {
				EntityShronker.this.direction -= 1.0F;
			}
			
			double phi = 2.0D * Math.PI * EntityShronker.this.direction;
			
			double xOffset = Math.cos(phi) * 10.0D;
			double yOffset = (EntityShronker.this.rand.nextFloat() - EntityShronker.this.rand.nextFloat()) * 0.5F;
			double zOffset = Math.sin(phi) * 10.0D;
			double x = EntityShronker.this.posX + xOffset;
			double y = EntityShronker.this.posY + yOffset;
			double z = EntityShronker.this.posZ + zOffset;
			
			for (int i = 0; i < 3; i++) {
				if (EntityShronker.this.world.isAirBlock(new BlockPos(x, y, z)) && this.canSee(x, y, z)) {
					EntityShronker.this.moveHelper.setMoveTo(x, y, z, 0.25D + (EntityShronker.this.scaredTimer > 0 ? 0.15D : 0.0D));
					EntityShronker.this.getLookHelper().setLookPosition(x, y, z, 180.0F, 20.0F);
					break;
				} else {
					EntityShronker.this.direction = EntityShronker.this.rand.nextFloat();
					phi = 2.0D * Math.PI * EntityShronker.this.direction;
					xOffset = Math.cos(phi) * 10.0D;
					zOffset = Math.sin(phi) * 10.0D;
					x = EntityShronker.this.posX + xOffset;
					z = EntityShronker.this.posZ + zOffset;
				}
			}
		}
		
		protected boolean canSee(double x, double y, double z) {
			return EntityShronker.this.world.rayTraceBlocks(new Vec3d(EntityShronker.this.posX, EntityShronker.this.posY + (double) EntityShronker.this.getEyeHeight(), EntityShronker.this.posZ), new Vec3d(x, y, z), false, true, false) == null;
		}
	}
	
	class AISleep extends EntityAIBase {
		public BlockPos sleepPos;
		
		public AISleep() {
			this.setMutexBits(3);
		}
		
		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			if (!EntityShronker.this.isSleeping() && !EntityShronker.this.world.isDaytime() && EntityShronker.this.scaredTimer <= 0) {
				for (int xOffset = 0; xOffset < 20; xOffset++) {
					for (int yOffset = 0; yOffset < 30; yOffset++) {
						for (int zOffset = 0; zOffset < 20; zOffset++) {
							double x = EntityShronker.this.posX + xOffset;
							double y = EntityShronker.this.posY + yOffset;
							double z = EntityShronker.this.posZ + zOffset;
							BlockPos pos = new BlockPos(x, y, z);
							
							if (EntityShronker.this.world.isAirBlock(pos) && EntityShronker.this.world.getBlockState(pos.down()).getMaterial().isSolid()) {
								this.sleepPos = pos;
								
								return true;
							}
						}
					}
				}
			}
			
			return false;
		}
		
		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return !EntityShronker.this.isSleeping() && !EntityShronker.this.world.isDaytime() && EntityShronker.this.scaredTimer <= 0 && this.sleepPos != null;
		}
		
		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		@Override
		public void resetTask() {
			super.resetTask();
			this.sleepPos = null;
		}
		
		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void updateTask() {
			EntityShronker.this.moveHelper.setMoveTo(this.sleepPos.getX() + 0.5D, this.sleepPos.getY() + 0.25D, this.sleepPos.getZ() + 0.5D, 0.5D);
			//EntityShronker.this.getLookHelper().setLookPosition(this.sleepPos.getX() + 0.5D, this.sleepPos.up().getY() + 0.25D, this.sleepPos.getZ() + 0.5D, 180.0F, 20.0F);
			double x = EntityShronker.this.posX - (this.sleepPos.getX() + 0.5D);
			double y = EntityShronker.this.posY - (this.sleepPos.getY() + 0.25D);
			double z = EntityShronker.this.posZ - (this.sleepPos.getZ() + 0.5D);
			double dist = Math.sqrt(x * x + y * y + z * z);
			
			if (dist < EntityShronker.this.getEntityBoundingBox().getAverageEdgeLength()) {
				EntityShronker.this.setSleeping(true);
				this.resetTask();
			}
		}
	}
}
