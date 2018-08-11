package gatocreador887.greenvoidislands.client.entity.model.bird.passive;

import gatocreador887.greenvoidislands.common.entity.bird.passive.EntityShronker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Shronker - GatoCreador887
 * Initially created using Tabula 7.0.0
 * Modified by author
 */
@SideOnly(Side.CLIENT)
public class ModelShronker extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer beak;
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer wingRight;
    public ModelRenderer wingLeft;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    private State state = State.FLYING;

    public ModelShronker() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.beak = new ModelRenderer(this, 0, 0);
        this.beak.setRotationPoint(0.0F, 0.25F, -10.0F);
        this.beak.addBox(-2.0F, -1.0F, -2.0F, 4, 2, 4, 0.0F);
        this.body = new ModelRenderer(this, 29, 3);
        this.body.setRotationPoint(0.0F, 10.0F, 5.0F);
        this.body.addBox(-5.0F, 0.0F, -7.0F, 10, 6, 14, 0.0F);
        this.legRight = new ModelRenderer(this, 100, 0);
        this.legRight.setRotationPoint(-2.5F, 16.0F, 5.5F);
        this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(legRight, 0.6108652381980153F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 9, 0);
        this.head.setRotationPoint(0.0F, 10.5F, -2.0F);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.head.addChild(this.beak);
        this.wingRight = new ModelRenderer(this, 0, 25);
        this.wingRight.setRotationPoint(-5.0F, 16.5F, 4.0F);
        this.wingRight.addBox(-12.0F, 0.0F, -5.0F, 12, 2, 10, 0.0F);
        this.setRotateAngle(wingRight, -0.017453292519943295F, 0.0F, 0.05235987755982988F);
        this.tail = new ModelRenderer(this, 65, 5);
        this.tail.setRotationPoint(0.0F, 10.5F, 12.0F);
        this.tail.addBox(-3.0F, -2.0F, 0.0F, 6, 2, 8, 0.0F);
        this.setRotateAngle(tail, 0.091106186954104F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 100, 15);
        this.legLeft.setRotationPoint(2.5F, 16.0F, 5.5F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(legLeft, 0.6108652381980153F, 0.0F, 0.0F);
        this.wingLeft = new ModelRenderer(this, 46, 25);
        this.wingLeft.setRotationPoint(5.0F, 16.5F, 4.0F);
        this.wingLeft.addBox(0.0F, 0.0F, -5.0F, 12, 2, 10, 0.0F);
        this.setRotateAngle(wingLeft, -0.017453292519943295F, 0.0F, -0.05235987755982988F);
    }
    
    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.body.render(scale);
        this.wingLeft.render(scale);
        this.wingRight.render(scale);
        this.tail.render(scale);
        this.head.render(scale);
        this.legLeft.render(scale);
        this.legRight.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        float f = ageInTicks * 0.3F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleZ = 0.0F;
        this.head.rotationPointX = 0.0F;
        this.body.rotationPointX = 0.0F;
        this.tail.rotationPointX = 0.0F;
        //this.wingRight.rotationPointX = -1.5F;
        //this.wingLeft.rotationPointX = 1.5F;

        /*if (this.state != ModelParrot.State.FLYING)
        {
            if (this.state == ModelParrot.State.SITTING)
            {
                return;
            }

            if (this.state == ModelParrot.State.PARTY)
            {
                float f1 = MathHelper.cos((float)entityIn.ticksExisted);
                float f2 = MathHelper.sin((float)entityIn.ticksExisted);
                this.head.rotationPointX = f1;
                this.head.rotationPointY = 15.69F + f2;
                this.head.rotateAngleX = 0.0F;
                this.head.rotateAngleY = 0.0F;
                this.head.rotateAngleZ = MathHelper.sin((float)entityIn.ticksExisted) * 0.4F;
                this.body.rotationPointX = f1;
                this.body.rotationPointY = 16.5F + f2;
                this.wingLeft.rotateAngleZ = -0.0873F - ageInTicks;
                this.wingLeft.rotationPointX = 1.5F + f1;
                this.wingLeft.rotationPointY = 16.94F + f2;
                this.wingRight.rotateAngleZ = 0.0873F + ageInTicks;
                this.wingRight.rotationPointX = -1.5F + f1;
                this.wingRight.rotationPointY = 16.94F + f2;
                this.tail.rotationPointX = f1;
                this.tail.rotationPointY = 21.07F + f2;
                return;
            }

            this.legLeft.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legRight.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        }*/
        
        if (this.state == State.SLEEPING) {
        	this.legRight.rotateAngleX = 0.0F;
        	this.legLeft.rotateAngleX = 0.0F;
        	this.wingRight.rotateAngleX = 0.0F;
        	this.wingRight.rotateAngleZ = -0.9105382707654417F;
        	this.wingLeft.rotateAngleX = 0.0F;
        	this.wingLeft.rotateAngleZ = 0.9105382707654417F;
        } else {
        	this.head.rotationPointY = 15.69F + f;
            this.tail.rotateAngleX = 1.015F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
            this.tail.rotationPointY = 21.07F + f;
            this.body.rotationPointY = 16.5F + f;
            this.wingLeft.rotateAngleZ = -0.0873F - ageInTicks;
            //this.wingLeft.rotationPointY = 16.94F + f;
            this.wingRight.rotateAngleZ = 0.0873F + ageInTicks;
            //this.wingRight.rotationPointY = 16.94F + f;
            this.legLeft.rotationPointY = 22.0F + f;
            this.legRight.rotationPointY = 22.0F + f;
            this.wingLeft.rotateAngleX = -0.017453292519943295F;
            this.wingRight.rotateAngleX = -0.017453292519943295F;
            this.legLeft.rotateAngleX = 1.015F + MathHelper.cos(limbSwing * 0.15F) * 0.1F * limbSwingAmount;
            this.legRight.rotateAngleX = 1.015F + MathHelper.sin(limbSwing * 0.15F) * 0.1F * limbSwingAmount;
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        //this.feather.rotateAngleX = -0.2214F;
        this.body.rotateAngleX = 0.0F;
        //this.wingLeft.rotateAngleX = -((float)Math.PI * 2F / 9F);
        //this.wingLeft.rotateAngleY = -(float)Math.PI;
        //this.wingRight.rotateAngleX = -((float)Math.PI * 2F / 9F);
        //this.wingRight.rotateAngleY = -(float)Math.PI;
        this.legLeft.rotateAngleX = -0.0299F;
        this.legRight.rotateAngleX = -0.0299F;
        this.legLeft.rotationPointY = 22.0F;
        this.legRight.rotationPointY = 22.0F;

        if (entitylivingbaseIn instanceof EntityShronker)
        {
            EntityShronker entityshronker = (EntityShronker)entitylivingbaseIn;

            /*if (entityparrot.isPartying())
            {
                this.legLeft.rotateAngleZ = -0.34906584F;
                this.legRight.rotateAngleZ = 0.34906584F;
                this.state = ModelParrot.State.PARTY;
                return;
            }

            if (entityparrot.isSitting())
            {
                float f = 1.9F;
                this.head.rotationPointY = 17.59F;
                this.tail.rotateAngleX = 1.5388988F;
                this.tail.rotationPointY = 22.97F;
                this.body.rotationPointY = 18.4F;
                this.wingLeft.rotateAngleZ = -0.0873F;
                this.wingLeft.rotationPointY = 18.84F;
                this.wingRight.rotateAngleZ = 0.0873F;
                this.wingRight.rotationPointY = 18.84F;
                ++this.legLeft.rotationPointY;
                ++this.legRight.rotationPointY;
                ++this.legLeft.rotateAngleX;
                ++this.legRight.rotateAngleX;
                this.state = ModelParrot.State.SITTING;
            }
            else*/ if (entityshronker.isSleeping())
            {
                this.legLeft.rotateAngleX += ((float)Math.PI * 2F / 9F);
                this.legRight.rotateAngleX += ((float)Math.PI * 2F / 9F);
                this.state = State.SLEEPING;
            }
            else
            {
                this.state = State.FLYING;
            }

            this.legLeft.rotateAngleZ = 0.0F;
            this.legRight.rotateAngleZ = 0.0F;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @SideOnly(Side.CLIENT)
    static enum State {
    	FLYING,
    	SLEEPING;
    }
}
