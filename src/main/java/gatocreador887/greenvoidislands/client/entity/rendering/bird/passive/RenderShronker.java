package gatocreador887.greenvoidislands.client.entity.rendering.bird.passive;

import gatocreador887.greenvoidislands.client.entity.model.bird.passive.ModelShronker;
import gatocreador887.greenvoidislands.common.entity.bird.passive.EntityShronker;
import gatocreador887.greenvoidislands.util.GVIReference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShronker extends RenderLiving<EntityShronker> {
	private static final ResourceLocation SHRONKER_TEXTURES = new ResourceLocation(GVIReference.ID, "textures/entity/shronker/shronker.png");
	private static final ResourceLocation SLEEPING_SHRONKER_TEXTURES = new ResourceLocation(GVIReference.ID, "textures/entity/shronker/sleeping.png");
	
	public static final Factory FACTORY = new Factory();
	
	public RenderShronker(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelShronker(), 1.0F);
	}
	
	protected float getDeathMaxRotation(EntityShronker entityLivingBaseIn) {
		return 180.0F;
	}
	
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityShronker entity) {
		return entity.isSleeping() ? SLEEPING_SHRONKER_TEXTURES : SHRONKER_TEXTURES;
	}
	
	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(EntityShronker entityLivingBaseIn, double x, double y, double z) {
		float f = (float) y;
		
		if (entityLivingBaseIn.isSleeping()) {
			f += 0.3F;
		}
		
		GlStateManager.translate((float) x, f, (float) z);
	}
	
	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	public float handleRotationFloat(EntityShronker livingBase, float partialTicks) {
		return this.getCustomBob(livingBase, partialTicks);
	}
	
	private float getCustomBob(EntityShronker shronker, float partialTicks) {
		float f = shronker.oFlap + (shronker.flap - shronker.oFlap) * partialTicks;
		float f1 = shronker.oFlapSpeed + (shronker.flapSpeed - shronker.oFlapSpeed) * partialTicks;
		return (MathHelper.sin(f) + 1.0F) * f1 * 1.5F;
	}
	
	public static class Factory implements IRenderFactory<EntityShronker> {
		@Override
		public Render<? super EntityShronker> createRenderFor(RenderManager manager) {
			return new RenderShronker(manager);
		}
	}
}