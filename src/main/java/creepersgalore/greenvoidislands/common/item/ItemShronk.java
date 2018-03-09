package creepersgalore.greenvoidislands.common.item;

import java.util.Map;

import com.google.common.collect.Maps;

import creepersgalore.greenvoidislands.common.core.GVIBlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemShronk extends ItemFood
{
    public ItemShronk()
    {
        super(0, 0.0F, false);
        this.setHasSubtypes(true);
    }

    public int getHealAmount(ItemStack stack)
    {
        ItemShronk.RipenessType itemshronk$ripenesstype = ItemShronk.RipenessType.byItemStack(stack);
        return itemshronk$ripenesstype.getHealAmount();
    }

    public float getSaturationModifier(ItemStack stack)
    {
        ItemShronk.RipenessType itemshronk$ripenesstype = ItemShronk.RipenessType.byItemStack(stack);
        return itemshronk$ripenesstype.getSaturationModifier();
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        ItemShronk.RipenessType itemshronk$ripenesstype = ItemShronk.RipenessType.byItemStack(stack);

        if (itemshronk$ripenesstype == ItemShronk.RipenessType.STAGE1)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0));
        }
        else if (itemshronk$ripenesstype == ItemShronk.RipenessType.STAGE2)
        {
        	player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 50, 0));
        }
        else if (itemshronk$ripenesstype == ItemShronk.RipenessType.STAGE3)
        {
        	player.heal(1.0f);
        }

        super.onFoodEaten(stack, worldIn, player);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        ItemShronk.RipenessType itemshronk$ripenesstype = ItemShronk.RipenessType.byItemStack(stack);

        if (entityLiving instanceof EntityPlayer && itemshronk$ripenesstype == ItemShronk.RipenessType.STAGE3)
        {
        	((EntityPlayer) entityLiving).inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(GVIBlockManager.shronk_sapling)));
        }

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (ItemShronk.RipenessType itemshronk$ripenesstype : ItemShronk.RipenessType.values())
            {
            	items.add(new ItemStack(this, 1, itemshronk$ripenesstype.getMetadata()));
            }
        }
    }
    
    public static String getVariantNameFromMeta(int meta)
    {
    	if (meta == 0)
    	{
    		return "unripe1";
    	}
    	else if (meta == 1)
    	{
    		return "unripe2";
    	}
    	else if (meta == 2)
    	{
    		return "ripe";
    	}
    	else
    	{
    		return null;
    	}
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        ItemShronk.RipenessType itemshronk$ripenesstype = ItemShronk.RipenessType.byItemStack(stack);
        return this.getUnlocalizedName() + "." + itemshronk$ripenesstype.getUnlocalizedName();
    }

    public static enum RipenessType
    {
        STAGE1(0, "unripe1", 2, 0.1F),
        STAGE2(1, "unripe2", 3, 0.25F),
        STAGE3(2, "ripe", 5, 0.4F);

        /** Maps an item damage value for an ItemStack to the corresponding FishType value. */
        private static final Map<Integer, ItemShronk.RipenessType> META_LOOKUP = Maps.<Integer, ItemShronk.RipenessType>newHashMap();
        /** The item damage value on an ItemStack that represents this fish type */
        private final int meta;
        /**
         * The value that this fish type uses to replace "XYZ" in: "fish.XYZ.raw" / "fish.XYZ.cooked" for the
         * unlocalized name and "fish_XYZ_raw" / "fish_XYZ_cooked" for the icon string.
         */
        private final String unlocalizedName;
        /** The amount that eating the uncooked version of this fish should heal the player. */
        private final int healAmount;
        /** The saturation modifier to apply to the heal amount when the player eats the uncooked version of this fish. */
        private final float saturationModifier;

        private RipenessType(int meta, String unlocalizedName, int heal, float saturation)
        {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.healAmount = heal;
            this.saturationModifier = saturation;
        }

        /**
         * Gets the item damage value on an ItemStack that represents this fish type
         */
        public int getMetadata()
        {
            return this.meta;
        }

        /**
         * Gets the value that this fish type uses to replace "XYZ" in: "fish.XYZ.raw" / "fish.XYZ.cooked" for the
         * unlocalized name and "fish_XYZ_raw" / "fish_XYZ_cooked" for the icon string.
         */
        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        /**
         * Gets the amount that eating the uncooked version of this fish should heal the player.
         */
        public int getHealAmount()
        {
            return this.healAmount;
        }

        /**
         * Gets the saturation modifier to apply to the heal amount when the player eats the uncooked version of this
         * fish.
         */
        public float getSaturationModifier()
        {
            return this.saturationModifier;
        }

        /**
         * Gets the corresponding FishType value for the given item damage value of an ItemStack, defaulting to COD for
         * unrecognized damage values.
         */
        public static ItemShronk.RipenessType byMetadata(int meta)
        {
            ItemShronk.RipenessType itemshronk$ripenesstype = META_LOOKUP.get(Integer.valueOf(meta));
            return itemshronk$ripenesstype == null ? STAGE1 : itemshronk$ripenesstype;
        }

        /**
         * Gets the FishType that corresponds to the given ItemStack, defaulting to COD if the given ItemStack does not
         * actually contain a fish.
         */
        public static ItemShronk.RipenessType byItemStack(ItemStack stack)
        {
            return stack.getItem() instanceof ItemShronk ? byMetadata(stack.getMetadata()) : STAGE1;
        }

        static
        {
            for (ItemShronk.RipenessType itemshronk$ripenesstype : values())
            {
                META_LOOKUP.put(Integer.valueOf(itemshronk$ripenesstype.getMetadata()), itemshronk$ripenesstype);
            }
        }
    }
}