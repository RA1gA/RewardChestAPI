package jp.mc.ra1ga.rewardchest.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import jp.mc.ra1ga.rewardchest.Main;

public class RewardChest {
	public RewardChest(Main plugin, String rewardName) {
		this.config = plugin.getConfig();
		this.rewardName = rewardName;
	}

	private FileConfiguration config;
	private String rewardName;

	public String getRewardName(){
		return rewardName;
	}

	public int getLimitedDuration(){
		return this.config.getInt("Rewards." + rewardName + ".LimitedDuration");
	}

	public int getLimitedDistance(){
		return this.config.getInt("Rewards." + rewardName + ".LimitedDistance");
	}

	public int getMinAmount(){
		return this.config.getInt("Rewards." + rewardName + ".MinAmount");
	}

	public int getMaxAmount(){
		return this.config.getInt("Rewards." + rewardName + ".MaxAmount");
	}

	public List<ItemStack> getItems(){
		List<ItemStack> items = new ArrayList<>();
		List<String> list = config.getStringList("Rewards." + rewardName + ".Items");
		for(String name : list){
			items.add(config.getItemStack("Items." + name));
		}
		return items;
	}

}
