package creepersgalore.greenvoidislands.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import creepersgalore.greenvoidislands.common.core.GVISoundManager;
import creepersgalore.greenvoidislands.common.world.GVIWorldProvider;

public class ClientEventHandler {
	
	/*@SubscribeEvent
	public static void onPlaySoundEvent(PlaySoundEvent event) {
		
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayerSP player = minecraft.player;
		
		if (player != null && player.world.provider instanceof GVIWorldProvider && (event.getSound().getSoundLocation().equals(new ResourceLocation("minecraft:music.creative")) || event.getSound().getSoundLocation().equals(new ResourceLocation("minecraft:music.game"))) && !minecraft.getSoundHandler().isSoundPlaying(PositionedSoundRecord.getMusicRecord(GVISoundManager.gvi_music))) {
			
			event.setResultSound(null);
			
			player.world.playSound(player.posX, player.posY, player.posZ, GVISoundManager.gvi_music, SoundCategory.MUSIC, 100000.0F, 1.0F, false);
			
		}
		
	}*/
	
}
