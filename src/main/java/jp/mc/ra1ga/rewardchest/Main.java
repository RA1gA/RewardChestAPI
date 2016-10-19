package jp.mc.ra1ga.rewardchest;

import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import jp.mc.ra1ga.rewardchest.command.RewardChestCommand;
import jp.mc.ra1ga.rewardchest.command.RewardItemCommand;
import jp.mc.ra1ga.rewardchest.entity.ChestStand;
import jp.mc.ra1ga.rewardchest.entity.EntityRewardChest;
import jp.mc.ra1ga.rewardchest.event.RewardChestEvent;

public class Main extends JavaPlugin {

	@Override
	public void onEnable(){

		getServer().getPluginManager().registerEvents(new RewardChestEvent(this), this);

		getCommand("reward").setExecutor(new RewardChestCommand(this));
		getCommand("rewarditem").setExecutor(new RewardItemCommand(this));

		saveDefaultConfig();

	}

	@Override
	public void onDisable(){

		for(EntityRewardChest erc : EntityRewardChest.getEntityRewardChests()){
			ChestStand cs = erc.getChestStand();
			for(ArmorStand as : cs.getStands()){
				if(as!=null && !as.isDead()){
					as.remove();
				}
			}
		}

	}

}
