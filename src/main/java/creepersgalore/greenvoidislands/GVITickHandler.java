package creepersgalore.greenvoidislands;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import creepersgalore.greenvoidislands.common.core.GVISoundManager;
import creepersgalore.greenvoidislands.common.world.GVIWorldProvider;

public class GVITickHandler {
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if (player != null && player.world.provider instanceof GVIWorldProvider && player.getRNG().nextInt(601) == 0) {
			
			player.world.playSound(player.posX, player.posY, player.posZ, GVISoundManager.ambient_reverb, SoundCategory.AMBIENT, 100000.0F, 1.0F + new Random().nextFloat(), false);
			
		}
		
	}
	
}
