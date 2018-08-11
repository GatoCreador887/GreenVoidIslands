package gatocreador887.greenvoidislands.common.world;

import java.util.Random;

import javax.annotation.Nullable;

import gatocreador887.greenvoidislands.GreenVoidIslands;
import gatocreador887.greenvoidislands.common.core.GVIBlockManager;
import gatocreador887.greenvoidislands.common.world.biome.GVIBiomeProvider;
import gatocreador887.greenvoidislands.common.world.gen.GVIChunkProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GVIWorldProvider extends WorldProvider {
	public static DimensionType dtype = DimensionType.register("Green Void Islands", "green_void_islands", 7, GVIWorldProvider.class, false);
	
	protected float[] originalLightBrightnessTable = new float[16];
	
	@Override
	public void init() {
		this.biomeProvider = new GVIBiomeProvider(this.world.getSeed(), this.world.getWorldInfo().getTerrainType());
		this.setWeatherRenderer(new WeatherRendererDisabler());
		this.setSkyRenderer(new GVISkyRenderer());
		this.setCloudRenderer(new CloudRendererDisabler());
	}
	
	@Override
	public DimensionType getDimensionType() {
		return dtype;
	}
	
	@Override
	public boolean hasSkyLight() {
		return true;
	}
	
	@Override
	public boolean isDaytime() {
		return this.world.getSkylightSubtracted() < 7;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float par1, float par2) {
		return new Vec3d(0.0D, 0.8666666666666667D, 0.5098039215686274D);
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new GVIChunkProvider(this.world, this.world.getSeed() - 7433);
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getSkyColor(net.minecraft.entity.Entity cameraEntity, float partialTicks) {
		return this.getSkyColorBody(cameraEntity, partialTicks);
	}
	
	@SideOnly(Side.CLIENT)
	public Vec3d getSkyColorBody(Entity entityIn, float partialTicks) {
		float f = this.world.getCelestialAngle(partialTicks);
		float f1 = MathHelper.cos(f * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
		f1 = MathHelper.clamp(f1, 0.25F, 1.0F);
		int i = MathHelper.floor(entityIn.posX);
		int j = MathHelper.floor(entityIn.posY);
		int k = MathHelper.floor(entityIn.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		int l = net.minecraftforge.client.ForgeHooksClient.getSkyBlendColour(this.world, blockpos);
		float f3 = 0.0F;// (float)(l >> 16 & 255) / 255.0F;
		float f4 = 0.8666666666666667F;// (float)(l >> 8 & 255) / 255.0F;
		float f5 = 0.5098039215686274F;// (float)(l & 255) / 255.0F;
		f3 = f3 * f1;
		f4 = f4 * f1;
		f5 = f5 * f1;
		/*
		 * float f6 = world.getRainStrength(partialTicks); if (f6 > 0.0F) { float f7 =
		 * (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F; float f8 = 1.0F - f6 * 0.75F;
		 * f3 = f3 * f8 + f7 * (1.0F - f8); f4 = f4 * f8 + f7 * (1.0F - f8); f5 = f5 *
		 * f8 + f7 * (1.0F - f8); } float f10 = world.getThunderStrength(partialTicks);
		 * if (f10 > 0.0F) { float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
		 * float f9 = 1.0F - f10 * 0.75F; f3 = f3 * f9 + f11 * (1.0F - f9); f4 = f4 * f9
		 * + f11 * (1.0F - f9); f5 = f5 * f9 + f11 * (1.0F - f9); } if
		 * (world.getLastLightningBolt() > 0) { float f12 =
		 * (float)world.getLastLightningBolt() - partialTicks; if (f12 > 1.0F) { f12 =
		 * 1.0F; } f12 = f12 * 0.45F; f3 = f3 * (1.0F - f12) + 0.8F * f12; f4 = f4 *
		 * (1.0F - f12) + 0.8F * f12; f5 = f5 * (1.0F - f12) + 1.0F * f12; }
		 */
		
		return new Vec3d((double) f3, (double) f4, (double) f5);
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		BlockPos blockpos = new BlockPos(par1, 0, par2);
		return this.world.getBiome(blockpos).ignorePlayerSpawnSuitability() ? true : this.world.getBlockState(blockpos).getBlock() == GVIBlockManager.ISLAND_GRASS;
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesXZShowFog(int par1, int par2) {
		return true;
	}
	
	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public MusicTicker.MusicType getMusicType() {
		return GreenVoidIslands.MUSIC_TYPE;
	}
	
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		int i = (int) (worldTime % 32000L);
		float f = ((float) i + partialTicks) / 32000.0F - 0.25F;
		
		if (f < 0.0F) {
			++f;
		}
		
		if (f > 1.0F) {
			--f;
		}
		
		float f1 = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
		f = f + (f1 - f) / 3.0F;
		return f;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float partialTicks) {
		return super.getSunBrightness(partialTicks) * 0.45F;
	}
	
	@Override
	public float getSunBrightnessFactor(float par1) {
		return super.getSunBrightnessFactor(par1) * 0.45F;
	}
	
	@Override
	protected void generateLightBrightnessTable() {
		/*
		 * float minBrightness = (float) (1.0F / 10000000.0F * Math.pow(105, 3.25F) +
		 * 0.002F); for(int i = 0; i <= 15; i++) { float f1 = 1F - (i*i) / (15F*15F);
		 * this.lightBrightnessTable[i] = ((1F - f1) / (f1 * 6F + 1F) * (1F -
		 * minBrightness) + minBrightness); }
		 * System.arraycopy(this.lightBrightnessTable, 0,
		 * this.originalLightBrightnessTable, 0, 16);
		 */super.generateLightBrightnessTable();
	}
	
	public static class WeatherRendererDisabler extends IRenderHandler {
		@Override
		public void render(float partialTicks, WorldClient world, Minecraft mc) {}
	}
	
	public static class CloudRendererDisabler extends IRenderHandler {
		@Override
		public void render(float partialTicks, WorldClient world, Minecraft mc) {}
	}
	
	public static class GVISkyRenderer extends IRenderHandler {
		// private static final ResourceLocation MOON_PHASES_TEXTURES = new
		// ResourceLocation("textures/environment/moon_phases.png");
		private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("greenvoidislands:textures/environment/sun.png");
		
		/** The star GL Call list */
		private int starGLCallList = -1;
		/** OpenGL sky list */
		private int glSkyList = -1;
		/** OpenGL sky list 2 */
		private int glSkyList2 = -1;
		
		private VertexFormat vertexBufferFormat;
		
		private VertexBuffer starVBO;
		private VertexBuffer skyVBO;
		private VertexBuffer sky2VBO;
		private boolean init;
		
		private void init() {
			GlStateManager.glTexParameteri(3553, 10242, 10497);
			GlStateManager.glTexParameteri(3553, 10243, 10497);
			GlStateManager.bindTexture(0);
			
			this.vertexBufferFormat = new VertexFormat();
			this.vertexBufferFormat.addElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
			
			this.generateStars();
			this.generateSky();
			this.generateSky2();
			
			this.init = true;
		}
		
		@Override
		public void render(float partialTicks, WorldClient world, Minecraft mc) {
			if (!this.init) {
				this.init();
			}
			
			GlStateManager.disableTexture2D();
			Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
			float f = (float) vec3d.x;
			float f1 = (float) vec3d.y;
			float f2 = (float) vec3d.z;
			
			/*
			 * if (false) { float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F; float
			 * f4 = (f * 30.0F + f1 * 70.0F) / 100.0F; float f5 = (f * 30.0F + f2 * 70.0F) /
			 * 100.0F; f = f3; f1 = f4; f2 = f5; }
			 */
			
			GlStateManager.color(f, f1, f2);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			GlStateManager.depthMask(false);
			GlStateManager.enableFog();
			GlStateManager.color(f, f1, f2);
			
			if (OpenGlHelper.useVbo()) {
				this.skyVBO.bindBuffer();
				GlStateManager.glEnableClientState(32884);
				GlStateManager.glVertexPointer(3, 5126, 12, 0);
				this.skyVBO.drawArrays(7);
				this.skyVBO.unbindBuffer();
				GlStateManager.glDisableClientState(32884);
			} else {
				GlStateManager.callList(this.glSkyList);
			}
			
			GlStateManager.disableFog();
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderHelper.disableStandardItemLighting();
			float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
			
			if (afloat != null) {
				GlStateManager.disableTexture2D();
				GlStateManager.shadeModel(7425);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				float f6 = afloat[0];
				float f7 = afloat[1];
				float f8 = afloat[2];
				
				/*
				 * if (false) { float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
				 * float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F; float f11 = (f6 * 30.0F + f8
				 * * 70.0F) / 100.0F; f6 = f9; f7 = f10; f8 = f11; }
				 */
				
				bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
				int l1 = 16;
				
				for (int j2 = 0; j2 <= 16; ++j2) {
					float f21 = (float) j2 * ((float) Math.PI * 2F) / 16.0F;
					float f12 = MathHelper.sin(f21);
					float f13 = MathHelper.cos(f21);
					bufferbuilder.pos((double) (f12 * 120.0F), (double) (f13 * 120.0F), (double) (-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
				}
				
				tessellator.draw();
				GlStateManager.popMatrix();
				GlStateManager.shadeModel(7424);
			}
			
			GlStateManager.enableTexture2D();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.pushMatrix();
			float f16 = 1.0F - world.getRainStrength(partialTicks);
			GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			float f17 = 30.0F;
			mc.renderEngine.bindTexture(SUN_TEXTURES);
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((double) -f17, 100.0D, (double) -f17).tex(0.0D, 0.0D).endVertex();
			bufferbuilder.pos((double) f17, 100.0D, (double) -f17).tex(1.0D, 0.0D).endVertex();
			bufferbuilder.pos((double) f17, 100.0D, (double) f17).tex(1.0D, 1.0D).endVertex();
			bufferbuilder.pos((double) -f17, 100.0D, (double) f17).tex(0.0D, 1.0D).endVertex();
			tessellator.draw();
			f17 = 20.0F;
			/*
			 * mc.renderEngine.bindTexture(MOON_PHASES_TEXTURES); int k1 =
			 * world.getMoonPhase(); int i2 = k1 % 4; int k2 = k1 / 4 % 2; float f22 =
			 * (float)(i2 + 0) / 4.0F; float f23 = (float)(k2 + 0) / 2.0F; float f24 =
			 * (float)(i2 + 1) / 4.0F; float f14 = (float)(k2 + 1) / 2.0F;
			 * bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			 * bufferbuilder.pos((double)(-f17), -100.0D, (double)f17).tex((double)f24,
			 * (double)f14).endVertex(); bufferbuilder.pos((double)f17, -100.0D,
			 * (double)f17).tex((double)f22, (double)f14).endVertex();
			 * bufferbuilder.pos((double)f17, -100.0D, (double)(-f17)).tex((double)f22,
			 * (double)f23).endVertex(); bufferbuilder.pos((double)(-f17), -100.0D,
			 * (double)(-f17)).tex((double)f24, (double)f23).endVertex();
			 * tessellator.draw(); Someone stole the moon! Gru! Get back here!
			 */
			GlStateManager.disableTexture2D();
			float f15 = world.getStarBrightness(partialTicks) * f16;
			
			if (f15 > 0.0F) {
				GlStateManager.color(f15, f15 * 0.25F, f15, f15 * 0.5F);
				
				if (OpenGlHelper.useVbo()) {
					this.starVBO.bindBuffer();
					GlStateManager.glEnableClientState(32884);
					GlStateManager.glVertexPointer(3, 5126, 12, 0);
					this.starVBO.drawArrays(7);
					this.starVBO.unbindBuffer();
					GlStateManager.glDisableClientState(32884);
				} else {
					GlStateManager.callList(this.starGLCallList);
				}
			}
			
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableFog();
			GlStateManager.popMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.color(0.0F, 0.0F, 0.0F);
			double d3 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();
			
			/*
			 * if (d3 < 0.0D) { GlStateManager.pushMatrix(); GlStateManager.translate(0.0F,
			 * 12.0F, 0.0F); if (OpenGlHelper.useVbo()) { this.sky2VBO.bindBuffer();
			 * GlStateManager.glEnableClientState(32884); GlStateManager.glVertexPointer(3,
			 * 5126, 12, 0); this.sky2VBO.drawArrays(7); this.sky2VBO.unbindBuffer();
			 * GlStateManager.glDisableClientState(32884); } else {
			 * GlStateManager.callList(this.glSkyList2); } GlStateManager.popMatrix(); float
			 * f18 = 1.0F; float f19 = -((float) (d3 + 65.0D)); float f20 = -1.0F;
			 * bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			 * bufferbuilder.pos(-1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
			 * bufferbuilder.pos(-1.0D, (double) f19, -1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(1.0D, (double) f19, 1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(1.0D, (double) f19, -1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(-1.0D, (double) f19, -1.0D).color(0, 0,
			 * 0, 255).endVertex(); bufferbuilder.pos(-1.0D, (double) f19, 1.0D).color(0, 0,
			 * 0, 255).endVertex(); bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0,
			 * 255).endVertex(); bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0,
			 * 255).endVertex(); tessellator.draw(); }
			 */
			
			if (world.provider.isSkyColored()) {
				GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
			} else {
				GlStateManager.color(f, f1, f2);
			}
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -((float) (d3 - 16.0D)), 0.0F);
			GlStateManager.callList(this.glSkyList2);
			GlStateManager.popMatrix();
			GlStateManager.enableTexture2D();
			GlStateManager.depthMask(true);
			
		}
		
		private void generateStars() {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			
			if (this.starVBO != null) {
				this.starVBO.deleteGlBuffers();
			}
			
			if (this.starGLCallList >= 0) {
				GLAllocation.deleteDisplayLists(this.starGLCallList);
				this.starGLCallList = -1;
			}
			
			if (OpenGlHelper.useVbo()) {
				this.starVBO = new VertexBuffer(this.vertexBufferFormat);
				this.renderStars(bufferbuilder);
				bufferbuilder.finishDrawing();
				bufferbuilder.reset();
				this.starVBO.bufferData(bufferbuilder.getByteBuffer());
			} else {
				this.starGLCallList = GLAllocation.generateDisplayLists(1);
				GlStateManager.pushMatrix();
				GlStateManager.glNewList(this.starGLCallList, 4864);
				this.renderStars(bufferbuilder);
				tessellator.draw();
				GlStateManager.glEndList();
				GlStateManager.popMatrix();
			}
		}
		
		private void renderStars(BufferBuilder bufferBuilderIn) {
			Random random = new Random(10842L);
			bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);
			
			for (int i = 0; i < 1500; ++i) {
				double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
				double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
				double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
				double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
				double d4 = d0 * d0 + d1 * d1 + d2 * d2;
				
				if (d4 < 1.0D && d4 > 0.01D) {
					d4 = 1.0D / Math.sqrt(d4);
					d0 = d0 * d4;
					d1 = d1 * d4;
					d2 = d2 * d4;
					double d5 = d0 * 100.0D;
					double d6 = d1 * 100.0D;
					double d7 = d2 * 100.0D;
					double d8 = Math.atan2(d0, d2);
					double d9 = Math.sin(d8);
					double d10 = Math.cos(d8);
					double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
					double d12 = Math.sin(d11);
					double d13 = Math.cos(d11);
					double d14 = random.nextDouble() * Math.PI * 2.0D;
					double d15 = Math.sin(d14);
					double d16 = Math.cos(d14);
					
					for (int j = 0; j < 4; ++j) {
						double d17 = 0.0D;
						double d18 = (double) ((j & 2) - 1) * d3;
						double d19 = (double) ((j + 1 & 2) - 1) * d3;
						double d20 = 0.0D;
						double d21 = d18 * d16 - d19 * d15;
						double d22 = d19 * d16 + d18 * d15;
						double d23 = d21 * d12 + 0.0D * d13;
						double d24 = 0.0D * d12 - d21 * d13;
						double d25 = d24 * d9 - d22 * d10;
						double d26 = d22 * d9 + d24 * d10;
						bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
					}
				}
			}
		}
		
		private void generateSky() {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			
			if (this.skyVBO != null) {
				this.skyVBO.deleteGlBuffers();
			}
			
			if (this.glSkyList >= 0) {
				GLAllocation.deleteDisplayLists(this.glSkyList);
				this.glSkyList = -1;
			}
			
			if (OpenGlHelper.useVbo()) {
				this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
				this.renderSky(bufferbuilder, 16.0F, false);
				bufferbuilder.finishDrawing();
				bufferbuilder.reset();
				this.skyVBO.bufferData(bufferbuilder.getByteBuffer());
			} else {
				this.glSkyList = GLAllocation.generateDisplayLists(1);
				GlStateManager.glNewList(this.glSkyList, 4864);
				this.renderSky(bufferbuilder, 16.0F, false);
				tessellator.draw();
				GlStateManager.glEndList();
			}
		}
		
		private void renderSky(BufferBuilder bufferBuilderIn, float posY, boolean reverseX) {
			int i = 64;
			int j = 6;
			bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);
			
			for (int k = -384; k <= 384; k += 64) {
				for (int l = -384; l <= 384; l += 64) {
					float f = (float) k;
					float f1 = (float) (k + 64);
					
					if (reverseX) {
						f1 = (float) k;
						f = (float) (k + 64);
					}
					
					bufferBuilderIn.pos((double) f, (double) posY, (double) l).endVertex();
					bufferBuilderIn.pos((double) f1, (double) posY, (double) l).endVertex();
					bufferBuilderIn.pos((double) f1, (double) posY, (double) (l + 64)).endVertex();
					bufferBuilderIn.pos((double) f, (double) posY, (double) (l + 64)).endVertex();
				}
			}
		}
		
		private void generateSky2() {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			
			if (this.sky2VBO != null) {
				this.sky2VBO.deleteGlBuffers();
			}
			
			if (this.glSkyList2 >= 0) {
				GLAllocation.deleteDisplayLists(this.glSkyList2);
				this.glSkyList2 = -1;
			}
			
			if (OpenGlHelper.useVbo()) {
				this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
				this.renderSky(bufferbuilder, -16.0F, true);
				bufferbuilder.finishDrawing();
				bufferbuilder.reset();
				this.sky2VBO.bufferData(bufferbuilder.getByteBuffer());
			} else {
				this.glSkyList2 = GLAllocation.generateDisplayLists(1);
				GlStateManager.glNewList(this.glSkyList2, 4864);
				this.renderSky(bufferbuilder, -16.0F, true);
				tessellator.draw();
				GlStateManager.glEndList();
			}
		}
		
	}
}