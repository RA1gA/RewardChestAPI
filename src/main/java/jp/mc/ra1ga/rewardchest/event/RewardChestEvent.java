package jp.mc.ra1ga.rewardchest.event;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import jp.mc.ra1ga.rewardchest.Main;
import jp.mc.ra1ga.rewardchest.entity.ChestStand;
import jp.mc.ra1ga.rewardchest.entity.EntityRewardChest;

public class RewardChestEvent implements Listener {
	public RewardChestEvent(Main plugin) {
		this.plugin = plugin;
	}

	private Main plugin;

	@EventHandler
	public void onClickChestStand(PlayerArmorStandManipulateEvent e){
		ArmorStand as = e.getRightClicked();
		for(EntityRewardChest erc : EntityRewardChest.getEntityRewardChests()){
			ChestStand cs = erc.getChestStand();
			for(ArmorStand stand : cs.getStands()){
				if(as.equals(stand)){
					Player p = e.getPlayer();
					if(erc.getPlayer().equals(p)){
						p.openInventory(erc.getInventory());
					}
					e.setCancelled(true);
				}
			}
		}
	}

}
