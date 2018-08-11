package gatocreador887.greenvoidislands.common;

import java.util.Random;

import gatocreador887.greenvoidislands.common.core.GVISoundManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerEventHandler {
	
	@SubscribeEvent
	public static void onTravelToDimension(EntityTravelToDimensionEvent event) {
		
		Entity entity = event.getEntity();
		
		if (entity instanceof EntityPlayer && event.getDimension() == 7) {
			
			entity.world.playSound(entity.posX, entity.posY, entity.posZ, GVISoundManager.GVI_TRAVEL, SoundCategory.BLOCKS, 100000.0F, 0.7F + new Random().nextFloat(), false);
			
		}
		
	}
	
}
