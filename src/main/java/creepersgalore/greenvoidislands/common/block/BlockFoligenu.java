package creepersgalore.greenvoidislands.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockFoligenu extends Block {
	
	public BlockFoligenu() {
		super(Material.ROCK);
		
		this.setSoundType(SoundType.GROUND);
		this.setHardness(2.2F);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(15.0F);
		this.setUnlocalizedName("foligenu_block");
		this.setRegistryName("foligenu_block");
		ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
	}
	
}
