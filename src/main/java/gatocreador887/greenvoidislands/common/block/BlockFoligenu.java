package gatocreador887.greenvoidislands.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFoligenu extends Block {
	public BlockFoligenu() {
		super(Material.ROCK);
		
		this.setSoundType(SoundType.GROUND);
		this.setHardness(2.2F);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(15.0F);
	}
}
